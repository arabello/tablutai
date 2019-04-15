package ai.tablut.state

case class Board(rows: Int, cols: Int, grid: Vector[Vector[BoardCell]]) extends GameRulesComplied {

	def apply(x: Int)(y: Int): BoardCell = grid(x)(y)

	def applyAction(action: Action): Board = {
		val contentMoved = grid(action.from.coords._1)(action.from.coords._2).cellContent
		copy(grid = grid.map(row => row.map(cell =>
			cell.coords match {
				case action.from.coords => cell.copy(cellContent = CellContent.EMPTY)
				case action.to.coords => cell.copy(cellContent = contentMoved)
				case _ => cell
			}
		)))
	}

	/**
	  * @return True if it meets the game rules. False otherwise.
	  */
	override def isGameRulesComplied(gameRules: GameContext): Boolean = ???
}
