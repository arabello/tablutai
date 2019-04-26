package ai.tablut.adversarial.heuristic

import ai.tablut.state.Player.Player
import ai.tablut.state.State

class HeuristicBuilder {
	private var functions = Map[HeuristicFunction, Int]()
	private var utilMin = 0.toDouble
	private var utilMax = 1.toDouble

	object avg extends HeuristicFunction{
		override def eval(state: State, player: Player): Double =
			functions.foldLeft[Double](0)((acc, f) => acc + (f._1.eval(state, player) * f._2)) / functions.foldLeft[Double](0)((acc, f) => acc + f._2)
	}

	def add(heuristicFunction: HeuristicFunction, weight: Int): HeuristicBuilder = {
		functions ++= Map(heuristicFunction -> weight)
		this
	}

	def adaptDomain(utilMin: Double, utilMax: Double): HeuristicBuilder = {
		this.utilMin = utilMin
		this.utilMax = utilMax
		this
	}

	def build: (State, Player) => Double = (state: State, player: Player) => new HFBoundariesAdapter(avg, utilMin, utilMax).eval(state, player)
}
