package ai.tablut.adversarial.heuristic

import ai.tablut.state.Player.Player
import ai.tablut.state.State

private class HFRandom extends HeuristicFunction {
	override def eval(state: State, player: Player): Double = scala.math.random()
}
