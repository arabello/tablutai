package ai.tablut.state

case class BoardCellImpl(
	                    coords: (Int, Int),
	                    cellType: GameRules.CellType.Value,
	                    cellContent: GameRules.CellContent.Value) extends BoardCell with GameRulesComplied {
	/**
	  * @return True if it meets the game rules. False otherwise.
	  */
	override def isGameRulesComplied(gameRules: GameRules): Boolean = ???
}
