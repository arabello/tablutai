package ai.tablut.state

private case class BoardCellImpl(
	                                coords: (Int, Int),
	                                cellType: GameContext.CellType.Value,
	                                cellContent: GameContext.CellContent.Value) extends BoardCell{
	/**
	  * @return True if it meets the game rules. False otherwise.
	  */
	override def isGameRulesComplied(gameRules: GameContext): Boolean = ???

	/*
	Example for normal Tablut

	  a b c d e f g h i
	1
	2
	3
	4
	5
	6
	7
	8
	9
    */
	override def getHumanCoords: String = {
		val decASCII: Char = (coords._1 + 97).toChar
		val adjustedRow = coords._2 + 1
		s"$decASCII$adjustedRow"
	}
}
