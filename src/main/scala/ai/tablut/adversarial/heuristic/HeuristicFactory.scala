package ai.tablut.adversarial.heuristic

import ai.tablut.adversarial.heuristic.Phase.Phase
import ai.tablut.state.{GameContext, Player}
import ai.tablut.state.Player.Player

object HeuristicFactory{
	private def whiteStrategies(gameContext: GameContext, phase: Phase) = phase match{
		case Phase.START => Seq(
			(new StarterStrategy(gameContext), 8),
			(new PawsMajorityStrategy(), 2)
		)
		case Phase.MID => Seq(
			(new CellPriorityStrategy(gameContext), 6),
			(new KingEscapeStrategy(gameContext), 3),
			(new PawsMajorityStrategy(), 1),
		)
		case Phase.END => Seq(
			(new KingEscapeStrategy(gameContext), 8),
			(new PawsMajorityStrategy(), 2),
		)
	}

	private def blackStrategies(gameContext: GameContext, phase: Phase) = phase match{
		case Phase.START => Seq(
			(new StarterStrategy(gameContext), 8),
			(new PawsMajorityStrategy(), 2)
		)
		case Phase.MID => Seq(
			(new PawsMajorityStrategy(), 6),
			(new KingEscapeStrategy(gameContext), 2),
			(new HotspotStrategy(gameContext), 1),
			(new KingKillingStrategy, 1)
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
