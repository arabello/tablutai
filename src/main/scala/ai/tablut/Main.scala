package ai.tablut

import ai.tablut.adversarial.{IDABSimpleSearch, TablutGame}
import ai.tablut.connectivity.ConnFactory
import ai.tablut.serialization.TablutSerializer
import ai.tablut.state.StateFacade

object Main {
	def main(args: Array[String]): Unit = {
		if (args.length == 0){
			System.err.println("Usage 'main [w|b]'")
			System.exit(1)
		}

		val clientType = args(0) match {
			case "w" => "w"
			case "b" => "b"
			case _ =>
				System.err.println("Client type not specified. Usage 'main [w|b]'")
				System.exit(1)
		}

		val client = if (clientType == "w") ConnFactory.createWhiteClient() else ConnFactory.createBlackClient()

		val worstStateValue = 0.0
		val bestStateValue = 1.0
		val maxComputationSeconds = 3

		val stateFactory = StateFacade.normalStateFactory()

		client.writeTeamName()
		if (clientType == "b") // bad protocol: black player have to read twice, because the first time the init state is given before the white move
			client.readState()
		val jsonInitState = client.readState()
		val initState = TablutSerializer.fromJson(jsonInitState, stateFactory)

		val game = new TablutGame(stateFactory, initState)
		val search = new IDABSimpleSearch(stateFactory.context, game, worstStateValue, bestStateValue, maxComputationSeconds)
		//val search = new IterativeDeepeningAlphaBetaSearch(game, worstStateValue, bestStateValue, maxComputationSeconds)

		var currState = initState
		while(true) {
			val nextAction = search.makeDecision(currState)
			val metrics = search.getMetrics
			println(metrics)
			client.writeAction(nextAction)

			client.readState() // my state from server

			val jsonState = client.readState()
			currState = TablutSerializer.fromJson(jsonState, stateFactory)
		}
	}
}
