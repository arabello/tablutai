package ai.tablut.state

case class BoardImpl(rows: Int, cols: Int) extends Board[BoardCell]{
	override def applyAction(action: Action): Board[BoardCell] = ???

	/**
	  * @return True if it meets the game rules. False otherwise.
	  */
	override def isGameRulesComplied(gameRules: GameRules): Boolean = ???
}
