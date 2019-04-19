package ai.tablut.adversarial

import ai.tablut.state._
import aima.core.search.adversarial.IterativeDeepeningAlphaBetaSearch

class IDABSimpleSearch(context: GameContext, game: TablutGame, utilMin: Double, utilMax: Double, time: Int) extends IterativeDeepeningAlphaBetaSearch(game, utilMin, utilMax, time){
	val heuristicFunction = new HeuristicFunction(context, utilMin, utilMax)

	private def normalizeHeuristicValue(value: Double, min: Double, max: Double): Double =
		value - min / max - min

	/**
	  * Heuristic evaluation of non-terminal states
	  * @param state
	  * @param player
	  * @return
	  */
	override def eval(state: State, player: Player.Value): Double = player match {
		case Player.WHITE => heuristicFunction.random
		case Player.BLACK => heuristicFunction.blockEscapePoints(state)
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
