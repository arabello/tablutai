package ai.tablut

import ai.tablut.adversarial.{PhaseFactory, TablutGame, TablutSearch}
import ai.tablut.connectivity.ConnFactory
import ai.tablut.serialization.TablutSerializer
import ai.tablut.state.{Player, StateFacade}

object Main {
	def main(args: Array[String]): Unit = {
		val optionParser = new scopt.OptionParser[Config]("java -jar bin/pelle") {
			head("pelle", "1.x")

			opt[String]('p', "player")
    			.required()
				.action((x, c) => x match{
					case "w" | "white" | "WHITE" => c.copy(player = Some(Player.WHITE))
					case "b" | "black" | "BLACK" => c.copy(player = Some(Player.BLACK))
					case _ => c.copy(player = None)
				})
				.text("Type of player.\n'w' | 'white' | 'WHITE' for the WHITE player.\n'b' | 'black' 'BLACK' for the BLACK player")

			opt[Int]('c', "computeTime")
				.action((x, c) => c.copy(maxCompTime = x))
				.text("The max computing time in seconds (only computing NOT the entire player turn). Default is 55")

			opt[String]('s', "serverIp")
				.action((x, c) => c.copy(serverIP = x))
				.text("The server IP. Default is localhost")

			opt[Int]('w', "whitePort")
				.action((x, c) => c.copy(whitePort = x))
				.text("The server port for the WHITE player. Default is 5800")

			opt[Int]('b', "blackPort")
				.action((x, c) => c.copy(blackPort = x))
				.text("The server port for the BLACK player. Default is 5801")

			opt[Boolean]('d', "debug")
				.action((x, c) => c.copy(debug = x))
				.text("Enable debug log texts. Default is false")

			help("help").abbr("h")
		}

		optionParser.parse(args, Config()) match {
			case Some(config) =>
				if (config.player.isEmpty){
					optionParser.help("help")
					System.exit(1)
				}

				val player = config.player.get
				LogInterceptor.debug = config.debug
				val connFactory = new ConnFactory(config.teamName, config.serverIP)
				val client = if (player == Player.WHITE) connFactory.createWhiteClient(config.whitePort) else connFactory.createBlackClient(config.blackPort)
				val stateFactory = StateFacade.normalStateFactory()

				client.writeTeamName()
				if (player == Player.BLACK) // bad protocol: black player have to read twice, because the first time the init state is given before the white move
					client.readState()
				val jsonInitState = client.readState()
				val initState = TablutSerializer.fromJson(jsonInitState, stateFactory)

				val game = new TablutGame(stateFactory, initState)
				val search = new TablutSearch(stateFactory.context, game, config.maxCompTime)
				val phaseFactor = new PhaseFactory(config.midPhaseTurn, config.endPhaseTurn)

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

			case None =>
				optionParser.help("help")
				System.exit(1)
		}
	}
}
