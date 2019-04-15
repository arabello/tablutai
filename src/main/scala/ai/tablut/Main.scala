package ai.tablut

import ai.tablut.connectivity.ConnFactory
import ai.tablut.serialization.TablutSerializer
import ai.tablut.state.{Action, StateFacade}

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

		val worstStateValue = -1.0
		val bestStateValue = 1.0
		val maxComputationSeconds = 50

		val stateFactory = StateFacade.normalStateFactory()

		client.writeTeamName()
		val jsonInitState = client.readState()
		var currState = TablutSerializer.fromJson(jsonInitState, stateFactory)

		//val game = new TablutGame(stateFactory)
		//val search = new IterativeDeepeningAlphaBetaSearch(game, worstStateValue, bestStateValue, maxComputationSeconds)

		while(true) {
			val nextAction = Action(currState.turn, currState.board(3)(4), currState.board(3)(5)) // search.makeDecision(currState)
			client.writeAction(nextAction)

			val jsonState = client.readState()
			currState = TablutSerializer.fromJson(jsonState, stateFactory)
		}
	}
}
