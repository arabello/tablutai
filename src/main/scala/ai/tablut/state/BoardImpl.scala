package ai.tablut.state

private case class BoardImpl(rows: Int, cols: Int, grid: Vector[Vector[BoardCell]]) extends Board{

	override def applyAction(action: Action): Board = ???

	/**
	  * @return True if it meets the game rules. False otherwise.
	  */
	override def isGameRulesComplied(gameRules: GameContext): Boolean = ???
}
