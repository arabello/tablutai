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
				.text("Type of player. Use 'w' or 'white' or 'WHITE' for the WHITE player. Use 'b' or 'black' or 'BLACK' for the BLACK player")

			opt[Int]('t', "time")
                                .validate(x => if (x <= 0) failure("time must be > 0") else success)
				.action((x, c) => c.copy(maxTurnTime = x))
				.text("The max player turn time in seconds. Default is 60")

			opt[String]('s', "serverIp")
				.action((x, c) => c.copy(serverIP = x))
				.text("The server IP. Default is localhost")

			opt[Int]('w', "whitePort")
				.action((x, c) => c.copy(whitePort = x))
				.text("The server port for the WHITE player. Default is 5800")

			opt[Int]('b', "blackPort")
				.action((x, c) => c.copy(blackPort = x))
				.text("The server port for the BLACK player. Default is 5801")

			opt[Unit]('d', "debug")
				.action((x, c) => c.copy(debug = true))
				.text("Flag to enable debug logging")

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
				var turnMillis = System.currentTimeMillis()
				val initState = TablutSerializer.fromJson(jsonInitState, stateFactory)

				val game = new TablutGame(stateFactory, initState)
				val compTime = if (config.maxTurnTime <= config.paddingTime) config.maxTurnTime else config.maxTurnTime - config.paddingTime
				val search = new TablutSearch(stateFactory.context, game, compTime)
				val phaseFactory = new PhaseFactory(config.midPhaseTurn, config.endPhaseTurn)


				var currState = initState
				var nTurn = if (player == Player.BLACK) 2 else 1
				while(true) {
					val nextAction = search.makeDecision(currState)
					client.writeAction(nextAction)

                    LogInterceptor{
                        print(s"turn time: ${(System.currentTimeMillis() - turnMillis) / 1000.toFloat} s ")
                    }

					// My state from server
					client.readState()

					// Wait for enemy turn
					System.gc()

					val metrics = search.getMetrics
					LogInterceptor{
						println(s"$metrics")
					}

					// Read state after enemy turn
					val jsonState = client.readState()
					turnMillis = System.currentTimeMillis()
					currState = TablutSerializer.fromJson(jsonState, stateFactory)

					val newPhase = phaseFactory.createPhase(currState, client.player, nTurn)
					nTurn += 1
					search.setPhase(newPhase)
				}

			case None =>
				optionParser.help("help")
				System.exit(1)
		}
	}
}
