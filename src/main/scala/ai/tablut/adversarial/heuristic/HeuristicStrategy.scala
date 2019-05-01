package ai.tablut.adversarial.heuristic

import ai.tablut.state.Player.Player
import ai.tablut.state.State

trait HeuristicStrategy{
	val minValue: Int
	val maxValue: Int

	def eval(state: State, player: Player): Int
}
