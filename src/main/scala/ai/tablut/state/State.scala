package ai.tablut.state

import ai.tablut.state.CellContent.CellContent
import ai.tablut.state.Ending.Ending
import ai.tablut.state.Player.Player

trait State extends GameRulesComplied {
	val rows: Int
	val cols: Int
	val turn: Player
	val ending: Option[Ending]
	val board: Seq[Seq[BoardCell]]

	def apply(coord: (Int, Int)): BoardCell
	def apply(x: Int)(y: Int): BoardCell
	def get(x: Int)(y: Int): Option[BoardCell]

	def findKing: Option[BoardCell]

	def allActions(context: GameContext): Seq[Action]

	def nextPlayer: State

	/**
	  * Retrieve all the cells considering only the valid coordinates given
	  * @param coords
	  */
	def get(coords: (Int, Int)*)(implicit gameContext: GameContext): Seq[BoardCell]

	/**
	  * Synthesize a new state applying the given action. The new state has the next player set as well
	  * @param action
	  * @return
	  */
	def applyAction(action: Action): State

	/**
	  * List of all cells flatten via row than column
	  * @return
	  */
	def getCells: Seq[BoardCell]

	/**
	  * List of filtered cells flatten via row than column
	  * @return
	  */
	def getCellsWithFilter(filter: BoardCell => Boolean): Seq[BoardCell]

	/**
	  * Synthesize a new board with [[ai.tablut.state.BoardCell#cellContent]] equals to [[ai.tablut.state.CellContent#EMPTY]]
	  * for the given coordinates
	  * @param coords
	  * @return
	  */
	def clearCells(coords: (Int, Int)*): State

	/**
	  * Synthesize a new board with [[ai.tablut.state.BoardCell#cellContent]] equals to the given content
	  * for the given coordinates.
	  * @param coords
	  * @param content
	  * @return
	  */
	def transform(coordsAndContent: Map[(Int, Int), CellContent]): State

	def distance(first: (Int, Int), second: (Int, Int)): Int =
		math.abs(if (first._1 == second._1) first._2 - second._2 else first._1 - second._1)
}
