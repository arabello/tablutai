package ai.tablut.adversarial.heuristic

import ai.tablut.state.Turn.Turn
import ai.tablut.state.{CellContent, GameContext, State, Turn}


private class HFBlockEscapePoints(context: GameContext) extends HeuristicFunction {

	override def eval(state: State, player: Turn): Double = {
		val playerMultiplier = if (state.turn == Turn.BLACK) 1 else -1
		val blockerCells = state.board.get((2,2), (2,6), (6,2), (6,6))(context)
		val step = playerMultiplier.toDouble / blockerCells.size
		val start = if (state.turn == Turn.BLACK) 0f else 1f

		blockerCells.foldLeft[Double](start)((acc, cell) =>
			if (cell.cellContent == CellContent.BLACK)
				acc + step
			else
				acc
		)
	}
}