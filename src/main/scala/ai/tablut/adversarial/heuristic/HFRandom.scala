package ai.tablut.adversarial.heuristic
import ai.tablut.state.Turn.Player
import ai.tablut.state.State

private object HFRandom extends HeuristicFunction {
	override def eval(state: State, player: Player): Double = scala.math.random()
}
