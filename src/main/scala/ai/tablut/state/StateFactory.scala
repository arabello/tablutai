package ai.tablut.state

/**
  * Entry point to create state components
  */
trait StateFactory {
	val context: GameContext

	def createInitialState(): State

	/**
	  * Create a new cell. If the given content is not valid for the coordinates related
	  * returns [[ai.tablut.state.InvalidBoardCell]]
	  * @param coords
	  * @param cellContent
	  * @return
	  */
	def createBoardCell(coords: (Int, Int), cellContent: CellContent.Value): BoardCell
}
