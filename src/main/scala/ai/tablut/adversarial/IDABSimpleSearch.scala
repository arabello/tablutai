package ai.tablut.adversarial

import ai.tablut.adversarial.heuristic.{HFAdapter, HFFactory}
import ai.tablut.state._
import aima.core.search.adversarial.IterativeDeepeningAlphaBetaSearch

class IDABSimpleSearch(context: GameContext, game: TablutGame, time: Int) extends IterativeDeepeningAlphaBetaSearch(game, 0, 1, time){
	val hfunction = new HFFactory(context)

	/**
	  * Heuristic evaluation of non-terminal states
	  * @param state
	  * @param player
	  * @return
	  */
	// IMPORTANT: When overriding, first call the super implementation!
	override def eval(state: State, player: Turn.Value): Double = {
		super.eval(state, player)
		val blockEscapePointsHeuristic = hfunction.createBlockEscapePoints
		val hfAdapter = new HFAdapter(blockEscapePointsHeuristic, this.utilMin, this.utilMax)
		val hfValue = hfAdapter.adjustEval(state, player)
		getMetrics.set("hfValue", hfValue)
		hfValue
	}

	/*{
		val utility = super.eval(state, player)
		if (game.isTerminal(state))
			utility
		else {
			val (whites, blacks) = state.board.grid.flatMap(row => row.map(c => c)).foldLeft[(Int, Int)]((0,0))((acc, cell) =>
					if (cell.cellContent == CellContent.WHITE)
						acc.copy(_1 = acc._1 + 1)
					else if (cell.cellContent == CellContent.BLACK)
						acc.copy(_2 = acc._2 + 1)
					else
						acc
				)

			val (value, min, max) = (whites > blacks, player) match {
				case (true, Player.WHITE) => (whites - blacks, context.maxWhites - context.maxBlacks, context.maxWhites)
				case (false, Player.BLACK) => (- (whites - blacks), context.maxWhites - context.maxBlacks, context.maxWhites)
				case (true, Player.BLACK) => (whites - blacks, context.maxBlacks - context.maxWhites, context.maxBlacks)
				case (false, Player.WHITE) => (- (whites - blacks), context.maxBlacks - context.maxWhites, context.maxBlacks)
				case _ => return 0
			}

			normalizeHeuristicValue(value, min, max)
		}
	}*/

	/**
	  *
	  * @param newUtility
	  * @param utility
	  * @return
	  */
	override def isSignificantlyBetter(newUtility: Double, utility: Double): Boolean =
		super.isSignificantlyBetter(newUtility, utility)
}
