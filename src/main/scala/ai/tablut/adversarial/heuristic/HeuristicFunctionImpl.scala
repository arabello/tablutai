package ai.tablut.adversarial.heuristic
import ai.tablut.state.Player.Player
import ai.tablut.state.State

private class HeuristicFunctionImpl(override val strategies: Seq[(HeuristicStrategy, Int)]) extends HeuristicFunction {
	private val normalizer = Normalizer.createNormalizer(
		strategies.foldLeft[Int](0)((acc, h) => acc + h._1.minValue),
		strategies.foldLeft[Int](0)((acc, h) => acc + h._1.maxValue)
	)
	private val totWeight: Int = strategies.foldLeft[Int](0)((acc, h) => acc + h._2)

	override def eval(state: State, player: Player): Double = {
		val num = strategies.foldLeft[Int](0)((acc, s) => (s._1.eval(state, player) * s._2) + acc)
		normalizer(num.toDouble / totWeight)
	}
}
