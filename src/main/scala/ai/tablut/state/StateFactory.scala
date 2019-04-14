package ai.tablut.state

/**
  * Factory to create game state parts within game rules
  * depending by the implementation of this interface
  */
trait StateFactory {
	val context: GameContext

	def createInitialState(): State

	/**
	  * Create a new cell not bounded to a [[ai.tablut.state.Board]] using the current [[ai.tablut.state.GameContext]]
	  *
	  * @param coords Position of a cell. First value as column index, second value as the row index
	  * @param cellType
	  * @param cellContent
	  * @return A new instance
	  */
	def createBoardCell(coords: (Int, Int), cellContent: CellContent.Value): BoardCell
}
