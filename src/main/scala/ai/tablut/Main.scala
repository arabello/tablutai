package ai.tablut

import java.io.FileInputStream
import java.util.Properties

import ai.tablut.adversarial.{IterativeDeepeningAlphaBetaSearch, TablutGame, TablutSearch}
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

		val conf = new Properties()
		conf.load(new FileInputStream("config.properties"))

		LogInterceptor.init(conf)

		val connFactory = new ConnFactory(conf)
		val client = if (clientType == "w") connFactory.createWhiteClient() else connFactory.createBlackClient()
		val stateFactory = StateFacade.normalStateFactory()
		val maxComputationTime = conf.getProperty("MAX_COMPUTATION_TIME", "57").toInt

		client.writeTeamName()
		if (clientType == "b") // bad protocol: black player have to read twice, because the first time the init state is given before the white move
			client.readState()
		val jsonInitState = client.readState()
		val initState = TablutSerializer.fromJson(jsonInitState, stateFactory)

		val game = new TablutGame(stateFactory, initState)
		val search = new TablutSearch(stateFactory.context, game, maxComputationTime)
		///val search = new IterativeDeepeningAlphaBetaSearch(game, 0, 1, 40)

		var currState = initState
		while(true) {
			val nextAction = search.makeDecision(currState)
			val metrics = search.getMetrics
			LogInterceptor{
				println(s"$metrics")
			}
			client.writeAction(nextAction)

			client.readState() // my state from server

			val jsonState = client.readState()
			currState = TablutSerializer.fromJson(jsonState, stateFactory)
		}
	}
}
