package ai.tablut.adversarial.heuristic

import ai.tablut.state.{GameContext, Player}
import ai.tablut.state.Player.Player

class HeuristicFacade(gameContext: GameContext) {
	val strategies: Seq[(HeuristicStrategy, Int)] = Seq(
		(new KingEscape(gameContext), 55),
		(new PawsMajorityStrategy(), 25),
		(new HotspotStrategy(gameContext), 20)
	)

	val totWeight: Int = strategies.foldLeft[Int](0)((acc, h) => acc + h._2)
}
