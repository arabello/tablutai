package ai.tablut.adversarial.heuristic

import ai.tablut.state.Player.Player
import ai.tablut.state.State

trait HeuristicFunction {
	val minValue: Double = 0.0
	val maxValue: Double = 1.0
	val strategies: Seq[(HeuristicStrategy, Int)]

	def eval(state: State, player: Player): Double

	protected object Normalizer {
		def createNormalizer(min: Double, max: Double): Double => Double = (value: Double) => {
			val res = (value - min) / (max - min)
			if (res < 0) 0 else if (res > 1) 1 else res
		}
	}
}
