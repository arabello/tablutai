package ai.tablut.adversarial.heuristic

import ai.tablut.adversarial.heuristic.Phase.Phase
import ai.tablut.state.{GameContext, Player}
import ai.tablut.state.Player.Player

object HeuristicFactory{
	private def whiteStrategies(gameContext: GameContext) = Seq(
		(new CellPriorityStrategy(gameContext), 6),
		//(new KingEscapeStrategy(gameContext), 3),
		(new PawsMajorityStrategy(), 3),
		(new StarterStrategy(gameContext), 10)
	)

	private def blackStrategies(gameContext: GameContext) = Seq(
		(new KingKillingStrategy, 6),
		(new PawsMajorityStrategy(), 2),
		(new KingEscapeStrategy(gameContext), 1),
		(new HotspotStrategy(gameContext), 1)
	)

	def createHeuristicFunction(gameContext: GameContext, player: Player, phase: Phase): HeuristicFunction =
		new HeuristicFunctionImpl(player match{
			case Player.WHITE => whiteStrategies(gameContext)
			case Player.BLACK => blackStrategies(gameContext)
			case _ => Seq()
		})
}
