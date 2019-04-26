package ai.tablut.adversarial.heuristic

import ai.tablut.state.Player.Player
import ai.tablut.state.State

trait HeuristicFunction {
	def eval(state: State, player: Player): Double
}
