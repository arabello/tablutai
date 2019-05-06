package ai.tablut.adversarial.heuristic

import ai.tablut.adversarial.heuristic.Phase.Phase
import ai.tablut.state.Player.Player
import ai.tablut.state.{GameContext, Player}

object HeuristicFactory{
	private def whiteStrategies(gameContext: GameContext, phase: Phase) = phase match{
		case Phase.START => Seq(
			(new WhiteStarterStrategy(gameContext), 8),
			(new CellPriorityStrategy(gameContext), 2)
		)
		case Phase.MID => Seq(
			(new CellPriorityStrategy(gameContext), 6),
			(new HotspotStrategy(gameContext), 3),
			(new PawsMajorityStrategy(), 1),
		)
		case Phase.END => Seq(
			(new HotspotStrategy(gameContext), 8),
			(new PawsMajorityStrategy(), 2),
		)
	}

	private def blackStrategies(gameContext: GameContext, phase: Phase) = phase match{
		case Phase.START => Seq(
			(new CellPriorityStrategy(gameContext), 8),
			(new PawsMajorityStrategy(), 2)
		)
		case Phase.MID => Seq(
			(new KingKillingStrategy, 5),
			(new CellPriorityStrategy(gameContext), 3),
			(new PawsMajorityStrategy(), 1)
		)
		case Phase.END => Seq(
			(new KingKillingStrategy, 8),
			(new PawsMajorityStrategy(), 2)
		)
	}

	def createHeuristicFunction(gameContext: GameContext, player: Player, phase: Phase): HeuristicFunction =
		new HeuristicFunctionImpl(player match{
			case Player.WHITE => whiteStrategies(gameContext, phase)
			case Player.BLACK => blackStrategies(gameContext, phase)
			case _ => Seq()
		})
}
