package ai.tablut.state

import ai.tablut.state.CellContent.CellContent
import ai.tablut.state.CellType.CellType

private case class BoardCellImpl(
	                                coords: (Int, Int),
	                                cellType: CellType,
	                                cellContent: CellContent) extends BoardCell{
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
	override def toHumanCoords: String = {
		val adjustedRow = coords._1 + 1
		val decASCII: Char = (coords._2 + 97).toChar
		s"$decASCII$adjustedRow"
	}
}
