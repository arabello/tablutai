package ai.tablut.state

/**
  * Context data for the game. Contains all the rules that can be different
  * between game implementation (game variants)
  */
trait GameContext{
	val nRows: Int
	val nCols: Int
	val throne: (Int, Int)
	val camps: Set[(Int, Int)]
	val escapePoints: Set[(Int, Int)]
	val invalidBoardCell: Set[(CellType.Value, CellContent.Value)]

	def isWinner(state: State): Boolean
}