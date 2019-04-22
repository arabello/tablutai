package ai.tablut.adversarial.heuristic

import ai.tablut.state.Turn.Turn
import ai.tablut.state.{CellContent, GameContext, State, Turn}


private class HFBlockEscapePoints(context: GameContext) extends HeuristicFunction {

	override def eval(state: State, player: Turn): Double = {
		val blockerCells = state.board.get((2, 2), (2, 6), (6, 2), (6, 6))(context)
		val step = 1.toDouble / blockerCells.size

		blockerCells.foldLeft[Double](0f)((acc, cell) =>
			if (player == Turn.BLACK)
				if (cell.cellContent == CellContent.BLACK)
					acc + step
				else
					acc
			else cell.cellContent match {
				case CellContent.KING => acc + 0.4
				case CellContent.EMPTY => acc + 0.2
				case CellContent.WHITE => acc + 0.1
				case CellContent.BLACK => acc - 0.1
				case _ => acc
			}
		)
	}
}