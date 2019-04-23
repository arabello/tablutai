package ai.tablut.adversarial

import ai.tablut.adversarial.heuristic.{HeuristicBuilder, HeuristicFunction, NormalGameHeuristicFactory}
import ai.tablut.state._
import aima.core.search.adversarial.IterativeDeepeningAlphaBetaSearch

class IDABSimpleSearch(context: GameContext, game: TablutGame, time: Int) extends IterativeDeepeningAlphaBetaSearch(game, 0, 1, time){

	val hKingAssasination: HeuristicFunction = NormalGameHeuristicFactory.createKingAssasination()
	val hBlockEscapePoints: HeuristicFunction = NormalGameHeuristicFactory.createBlockEscapePoints()
	val hPawsMajority: HeuristicFunction = NormalGameHeuristicFactory.createPawsMajority()

	private val hBuilder = new HeuristicBuilder().adaptDomain(this.utilMin, this.utilMax)

	/**
	  * Heuristic evaluation of non-terminal states
	  * @param state
	  * @param player
	  * @return
	  */
	// IMPORTANT: When overriding, first call the super implementation!
	override def eval(state: State, player: Turn.Value): Double = {
		super.eval(state, player)

		val heuristic = hBuilder
    		.add(hPawsMajority, 6)
			.add(hBlockEscapePoints, 4)
    		.add(hKingAssasination, 2)
    		.build

		val hValue = heuristic(state, player)
		getMetrics.set("hfValue", hValue)
		hValue
	}

}
