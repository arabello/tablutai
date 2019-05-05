package ai.tablut.adversarial.heuristic

import ai.tablut.state.{GameContext, Player}
import ai.tablut.state.Player.Player

object HeuristicFactory{
	private def whiteStrategies(gameContext: GameContext) = Seq(
		(new CellPriorityStrategy(gameContext), 60)
		(new KingEscapeStrategy(gameContext), 20),
		(new HotspotStrategy(gameContext), 10),
		(new PawsMajorityStrategy(), 10)
	)

	private def blackStrategies(gameContext: GameContext) = Seq(
		(new KingKillingStrategy, 60),
		(new PawsMajorityStrategy(), 20),
		(new KingEscapeStrategy(gameContext), 10),
		(new HotspotStrategy(gameContext), 10)
	)

	def createHeuristicFunction(gameContext: GameContext, player: Player): HeuristicFunction =
		new HeuristicFunctionImpl(player match{
			case Player.WHITE => whiteStrategies(gameContext)
			case Player.BLACK => blackStrategies(gameContext)
			case _ => Seq()
		})
}
