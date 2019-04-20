package ai.tablut.adversarial.heuristic

import ai.tablut.state.Turn.Player
import ai.tablut.state.State

trait HeuristicFunction {
	def eval(state: State, player: Player): Double
}
