package ai.tablut.adversarial.heuristic

import ai.tablut.state.{GameContext, Player}
import ai.tablut.state.Player.Player

class HeuristicFactory(player: Player, gameContext: GameContext) {
	private val whiteStrategies = Seq(
		(new PawsMajorityStrategy, 1)
	)
	private val blackStrategies = Seq(
		(new HotspotStrategy(gameContext), 6),
		(new PawsMajorityStrategy, 2)
	)

	val strategies: Seq[(HeuristicStrategy, Int)] = player match {
		case Player.WHITE => whiteStrategies
		case Player.BLACK => blackStrategies
		case _ => Seq()
	}

	val totWeight: Int = strategies.foldLeft[Int](0)((acc, h) => acc + h._2)
}
