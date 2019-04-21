package ai.tablut.adversarial.heuristic

import ai.tablut.state.State
import ai.tablut.state.Turn.Turn

class HeuristicBuilder {
	private var functions = Map[HeuristicFunction, Int]()
	private var utilMin = 0.toDouble
	private var utilMax = 1.toDouble

	def add(heuristicFunction: HeuristicFunction, weight: Int): HeuristicBuilder = {
		functions ++= Map(heuristicFunction -> weight)
		this
	}

	def setDomain(utilMin: Double, utilMax: Double): HeuristicBuilder = {
		this.utilMin = utilMin
		this.utilMax = utilMax
		this
	}

	def build = (state: State, player: Turn) =>  {
		val fadjusted = functions.map{ case (f, w) =>
			new HFAdapter(f, utilMin, utilMax) -> w
		}
		fadjusted.foldLeft[Double](0)((acc, f) => acc + (f._1.adjustEval(state, player) * f._2)) / functions.foldLeft[Double](0)((acc, f) => acc + f._2)
	}
}
