package ai.tablut.adversarial.heuristic

import ai.tablut.state.{GameContext, Player}
import ai.tablut.state.Player.Player

object HeuristicFactory{
	private def createStrategies(gameContext: GameContext) = Seq(
		(new KingEscapeStrategy(gameContext), 55),
		(new PawsMajorityStrategy(), 25),
		(new HotspotStrategy(gameContext), 20)
	)

	def createHeuristicFunction(gameContext: GameContext): HeuristicFunction = new HeuristicFunctionImpl(createStrategies(gameContext))
}
