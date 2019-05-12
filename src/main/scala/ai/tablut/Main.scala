package ai.tablut

import ai.tablut.adversarial.{PhaseFactory, TablutGame, TablutSearch}
import ai.tablut.connectivity.ConnFactory
import ai.tablut.serialization.TablutSerializer
import ai.tablut.state.StateFacade

object Main {
	def main(args: Array[String]): Unit = {
		if (args.length == 0){
			System.err.println("Usage 'main w|b [compute time in seconds] [server ip] [server port]'")
			System.exit(1)
		}

		val clientType = args(0) match {
			case "w" => "w"
			case "b" => "b"
			case _ =>
				System.err.println("Client type not specified. Usage 'main [w|b]'")
				System.exit(1)
		}

		val maxComputationTime = try {
				args(1).toInt
			}catch {
				case _: Throwable => Config.MAX_COMPUTATION_TIME
			}

		val serverIp = try {
			args(2)
		}catch {
			case _: Throwable => Config.SERVER_IP
		}

		val connFactory = new ConnFactory(Config.TEAM_NAME, serverIp)

		val port = try{
			args(3).toInt
		}catch {
			case _: Throwable => if (clientType == "w") Config.WHITE_PORT else Config.BLACK_PORT
		}

		val client = if (clientType == "w") connFactory.createWhiteClient(port) else connFactory.createBlackClient(port)
		val stateFactory = StateFacade.normalStateFactory()

		client.writeTeamName()
		if (clientType == "b") // bad protocol: black player have to read twice, because the first time the init state is given before the white move
			client.readState()
		val jsonInitState = client.readState()
		val initState = TablutSerializer.fromJson(jsonInitState, stateFactory)

		val game = new TablutGame(stateFactory, initState)
		val search = new TablutSearch(stateFactory.context, game, maxComputationTime)
		val phaseFactor = new PhaseFactory()

		var currState = initState
		var nTurn = 1
		while(true) {
			val nextAction = search.makeDecision(currState)
			client.writeAction(nextAction)

			// Wait for enemy turn

			val metrics = search.getMetrics
			LogInterceptor{
				println(s"$metrics")
			}

			// my state from server
			val afterActionJson = client.readState()
			val afterActionState = TablutSerializer.fromJson(afterActionJson, stateFactory)

			val newPhase = phaseFactor.createPhase(afterActionState, client.player, nTurn)
			nTurn += 1
			search.setPhase(newPhase)

			System.gc()

			// Read state after enemy turn

			val jsonState = client.readState()
			currState = TablutSerializer.fromJson(jsonState, stateFactory)


		}
	}
}
