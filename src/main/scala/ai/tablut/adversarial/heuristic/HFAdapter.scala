package ai.tablut.adversarial.heuristic

import ai.tablut.state.Turn.Player
import ai.tablut.state.State

class HFAdapter(hf: HeuristicFunction, utilMin: Double, utilMax: Double) {
	private val epsilon = 0.0000001

	private def excludeBoundaries(value: Double): Double = if (value == utilMin) value + epsilon else if (value == utilMax) value - epsilon else value

	def adjustEval(state: State, player: Player): Double = {
		val output = utilMin + ((utilMax - utilMin) / (1f - 0f)) * (hf.eval(state, player) - 0f)
		excludeBoundaries(output)
	}
}
