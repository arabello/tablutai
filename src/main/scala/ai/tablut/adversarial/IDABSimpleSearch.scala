package ai.tablut.adversarial

import ai.tablut.adversarial.heuristic.{HFAdapter, NormalGameHeuristicFactory}
import ai.tablut.state._
import aima.core.search.adversarial.IterativeDeepeningAlphaBetaSearch

class IDABSimpleSearch(context: GameContext, game: TablutGame, time: Int) extends IterativeDeepeningAlphaBetaSearch(game, 0, 1, time){

	/**
	  * Heuristic evaluation of non-terminal states
	  * @param state
	  * @param player
	  * @return
	  */
	// IMPORTANT: When overriding, first call the super implementation!
	override def eval(state: State, player: Turn.Value): Double = {
		super.eval(state, player)
		val heuristic = NormalGameHeuristicFactory.createBlockEscapePoints()
		val adapter = new HFAdapter(heuristic, this.utilMin, this.utilMax)
		val value = adapter.adjustEval(state, player)
		getMetrics.set("hfValue", value)
		value
	}


	/**
	  *
	  * @param newUtility
	  * @param utility
	  * @return
	  */
	override def isSignificantlyBetter(newUtility: Double, utility: Double): Boolean =
		super.isSignificantlyBetter(newUtility, utility)
}
