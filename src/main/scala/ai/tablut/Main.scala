package ai.tablut

import java.io.FileInputStream
import java.util.Properties

import ai.tablut.adversarial.heuristic.Phase
import ai.tablut.adversarial.{IterativeDeepeningAlphaBetaSearch, PhaseFactory, TablutGame, TablutSearch}
import ai.tablut.connectivity.ConnFactory
import ai.tablut.serialization.TablutSerializer
import ai.tablut.state.{Player, StateFacade}

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
		val defaultMaxCompTime = conf.getProperty("MAX_COMPUTATION_TIME", "55").toInt
		val maxComputationTime =
			if (args.length >= 2) try {
				args(1).toInt
			}catch {
				case _: Throwable => defaultMaxCompTime
			}else
				defaultMaxCompTime

		client.writeTeamName()
		if (clientType == "b") // bad protocol: black player have to read twice, because the first time the init state is given before the white move
			client.readState()
		val jsonInitState = client.readState()
		val initState = TablutSerializer.fromJson(jsonInitState, stateFactory)

		val game = new TablutGame(stateFactory, initState)
		val search = new TablutSearch(stateFactory.context, game, maxComputationTime)
		val phaseFactor = new PhaseFactory(conf)

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
