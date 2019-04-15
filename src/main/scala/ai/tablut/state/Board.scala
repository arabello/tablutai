package ai.tablut.state

trait Board extends GameRulesComplied {
	val cols: Int
	val rows: Int

	/**
	  * The data structure of a board is a two dimensional arrays.
	  * The first dimension must be used for the row by the implementation
	  * in order to maintain consistency to [[ai.tablut.state.BoardCell]]
	  */
	val grid: Array[Array[BoardCell]]

	def applyAction(action: Action): Board
}
