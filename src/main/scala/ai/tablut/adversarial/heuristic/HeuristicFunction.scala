package ai.tablut.adversarial.heuristic

import ai.tablut.state.Player.Player
import ai.tablut.state.State

trait HeuristicFunction {
	private val minValue: Double = 0.0
	private val maxValue: Double = 1.0
	val strategies: Seq[(HeuristicStrategy, Int)]

	private lazy val normalizer: Double => Double = Normalizer.createNormalizer(
		strategies.foldLeft[Int](0)((acc, h) => acc + h._1.minValue),
		strategies.foldLeft[Int](0)((acc, h) => acc + h._1.maxValue)
	)

	private lazy val totWeight: Int = strategies.foldLeft[Int](0)((acc, h) => acc + h._2)

	private val epsilon = Double.MinPositiveValue

	private def excludeBoundaries(value: Double): Double =
		if (value <= minValue) epsilon else if (value >= maxValue) maxValue - epsilon else value

	protected def internalEval(state: State, player: Player): Double = strategies.foldLeft[Int](0)((acc, s) => (s._1.eval(state, player) * s._2) + acc)

	final def eval(state: State, player: Player): Double = {
		val num = internalEval(state, player)
		val value = normalizer(num.toDouble / totWeight)
		excludeBoundaries(value)
	}

	protected object Normalizer {
		def createNormalizer(min: Double, max: Double): Double => Double = (value: Double) => {
			val res = (value - min) / (max - min)
			if (res < 0) 0 else if (res > 1) 1 else res
		}
	}
}
