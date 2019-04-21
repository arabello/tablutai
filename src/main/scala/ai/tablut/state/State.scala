package ai.tablut.state

import ai.tablut.state.Turn.Turn

case class State(board: Board, turn: Turn, isDraw: Boolean = false){
	override def toString: String = s"$board\nturn: $turn"

	def allActions(gameContext: GameContext): Seq[Action] =
		board.getCells.filter(c => c.cellContent match {
				case CellContent.WHITE | CellContent.KING => turn == Turn.WHITE
				case CellContent.BLACK => turn == Turn.BLACK
				case _ => false
			}).flatMap(cell =>
				(for (x <- 0 until board.rows;
				      a = Action(turn, cell, board.grid(x)(cell.coords._2))
				      if a.validate(gameContext, board))
				yield a)
					++
				(for (y <- 0 until board.cols;
				      a = Action(turn, cell, board.grid(cell.coords._1)(y))
				      if a.validate(gameContext, board))
				yield a)
			)
}
