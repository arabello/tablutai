package ai.tablut.adversarial

import ai.tablut.adversarial.heuristic.{HFAdapter, HeuristicBuilder, NormalGameHeuristicFactory}
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

		val heuristic = new HeuristicBuilder()
    		.adaptDomain(this.utilMin, this.utilMax)
			.add(NormalGameHeuristicFactory.createBlockEscapePoints(), 9)
    		.add(NormalGameHeuristicFactory.createPawsMajority(), 1)
    		.build

		val hValue = heuristic(state, player)
		getMetrics.set("hfValue", hValue)
		hValue
	}

}
