package ai.tablut.adversarial.heuristic

import ai.tablut.state.{GameContext, Player}
import ai.tablut.state.Player.Player

object HeuristicFactory{
	private def createStrategies(gameContext: GameContext) = Seq(
		(new KingKillingStrategy, 60),
		(new PawsMajorityStrategy(), 20),
		(new KingEscapeStrategy(gameContext), 10),
		(new HotspotStrategy(gameContext), 10)
	)

	def createHeuristicFunction(gameContext: GameContext): HeuristicFunction = new HeuristicFunctionImpl(createStrategies(gameContext))
}
