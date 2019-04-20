package ai.tablut.adversarial.heuristic

import ai.tablut.state.State
import ai.tablut.state.Turn.Turn

private class HFRandom extends HeuristicFunction {
	override def eval(state: State, player: Turn): Double = scala.math.random()
}
