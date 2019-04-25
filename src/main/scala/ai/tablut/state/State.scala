package ai.tablut.state

import ai.tablut.state.CellContent.KING
import ai.tablut.state.Turn.Turn

case class State(board: Board, turn: Turn, isDraw: Boolean = false){
	override def toString: String = s"$board\nturn: $turn"

	def findKing: Option[BoardCell] = board.getCellsWithFilter(c => c.cellContent == KING).headOption
}
