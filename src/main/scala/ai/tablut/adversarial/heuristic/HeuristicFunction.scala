package ai.tablut.adversarial.heuristic

import ai.tablut.state.State
import ai.tablut.state.Turn.Turn

trait HeuristicFunction {
	def eval(state: State, player: Turn): Double
}
