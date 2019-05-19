package ai.tablut.adversarial.heuristic

import ai.tablut.adversarial.heuristic.Phase.Phase
import ai.tablut.state.Player.Player
import ai.tablut.state.{GameContext, Player}

class HeuristicFactory(computeTimeSeconds: Int){
	val intensiveHeuristicDepthStart: Int = 5
	private var intensive = false
	def isIntensiveUsed: Boolean = intensive

	private def whiteStrategies(gameContext: GameContext, phase: Phase) = phase match{
		case Phase.START => Seq(
			(new WhiteStarterStrategy(gameContext), 10),
		)
		case Phase.MID => Seq(
			(new KingPathStrategy(gameContext), 5),
			(new PawsMajorityStrategy(), 4),
			(new CellPriorityStrategy(gameContext), 1)
		)
		case Phase.END => Seq(
			(new KingPathStrategy(gameContext), 5),
			(new CellPriorityStrategy(gameContext), 4),
			(new PawsMajorityStrategy(), 1),
		)
	}

	private def blackStrategies(gameContext: GameContext, phase: Phase) = phase match{
		case Phase.START => Seq(
			(new CellPriorityStrategy(gameContext), 10)
		)
		case Phase.MID => Seq(
			(new CellPriorityStrategy(gameContext), 5),
			(new PawsMajorityStrategy(), 3),
			(new KingPathStrategy(gameContext), 2)
		)
		case Phase.END => Seq(
			(new KingKillingStrategy, 6),
			(new KingPathStrategy(gameContext), 3),
			(new CellPriorityStrategy(gameContext), 1),
		)
	}

	def createHeuristicFunction(gameContext: GameContext, player: Player, phase: Phase, depth: Int): HeuristicFunction = {
		intensive = false

		val strategies = player match{
			case Player.WHITE =>
				whiteStrategies(gameContext, phase)
			case Player.BLACK =>
				blackStrategies(gameContext, phase)
			case _ => Seq()
		}

		if (phase == Phase.START)
			return new HeuristicFunctionImpl(strategies)

		val basedOnDepth =
			if (depth >= intensiveHeuristicDepthStart) {
				intensive = true
				strategies
			} else
				strategies.filterNot(s => s._1.isInstanceOf[KingPathStrategy])

		new HeuristicFunctionImpl(basedOnDepth)
	}

}
