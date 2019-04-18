package ai.tablut.state

import ai.tablut.state.CellContent.CellContent
import ai.tablut.state.CellType.CellType

/**
  * Coordinates are a pair of integer representing the position of a cell
  * within a [[ai.tablut.state.Board]].
  * The first value must be represent the row index and
  * the second value the column index by the implementation in order to maintain
  * consistency to [[ai.tablut.state.Board]]
  */
case class BoardCell(coords: (Int, Int), cellType: CellType, cellContent: CellContent) extends GameRulesComplied {

	/**
	  * @return True if it meets the game rules. False otherwise.
	  */
	override def isGameRulesComplied(gameRules: GameContext): Boolean = ???

	/**
	  * Board grid is a matrix. Considering the upper left corner as the origin point,
	  * x as the index of rows and y as the index of columns, this method transform
	  * coordinates (x,y) into the a string "ch1ch2" where ch1 is a lower case letter
	  * starting from "a" indexing the columns and ch2 is number starting from 1 indexing the rows.
	  * @return A string representing the coordinate using lower case letter for columns and number starting from 1 as rows
	  */
	def toHumanCoords: String = {
		val adjustedRow = coords._1 + 1
		val decASCII: Char = (coords._2 + 97).toChar
		s"$decASCII$adjustedRow"
	}
}

object BoardCell{
	def unapply(arg: BoardCell): Option[((Int, Int), CellType, CellContent)] = Some(arg.coords, arg.cellType, arg.cellContent)
}