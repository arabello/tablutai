package ai.tablut.adversarial.heuristic

import ai.tablut.state.Player.Player
import ai.tablut.state.State

class HFBoundariesAdapter(hf: HeuristicFunction, utilMin: Double, utilMax: Double) extends HeuristicFunction {
	private val epsilon = 0.0000001

	private def excludeBoundaries(value: Double): Double = if (value == utilMin) value + epsilon else if (value == utilMax) value - epsilon else value

	override def eval(state: State, player: Player): Double = {
		val output = utilMin + ((utilMax - utilMin) / (1f - 0f)) * (hf.eval(state, player) - 0f)
		excludeBoundaries(output)
	}
}
