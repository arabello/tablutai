package ai.tablut.state

/**
  * Define a legal behaviour within a given [[ai.tablut.state.Board]]
  */
trait Legal {
	/**
	  *
	  * @param board
	  * @return True if this element is legitimate according the given board. False otherwise
	  */
	def isLegal(board: Board): Boolean
}
