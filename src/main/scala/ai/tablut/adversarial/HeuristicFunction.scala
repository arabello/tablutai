package ai.tablut.adversarial

import ai.tablut.state.State

object HeuristicFunction {
	def random: Double = scala.math.random()

	def blockEscapePoints(state: State): Boolean = false
}
