package ai.tablut.state

private case class BoardImpl(rows: Int, cols: Int) extends Board[BoardCell]{
        
        val grid: Array[Array[BoardCell]] = Array.ofDim[BoardCell](rows, cols)

	override def applyAction(action: Action): Board[BoardCell] = ???

	/**
	  * @return True if it meets the game rules. False otherwise.
	  */
	override def isGameRulesComplied(gameRules: GameRules): Boolean = ???
}
