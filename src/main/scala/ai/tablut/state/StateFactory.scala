package ai.tablut.state

import ai.tablut.state.CellContent.CellContent
import ai.tablut.state.Player.Player

/**
  * Entry point to create state components
  */
trait StateFactory {
	val context: GameContext

	def createState(grid: Seq[Seq[CellContent]], turn: Player): State

	/**
	  * Create a new cell. If the given content is not valid for the coordinates related
	  * returns [[ai.tablut.state.InvalidBoardCell]]
	  * @param coords
	  * @param cellContent
	  * @return
	  */
	def createBoardCell(coords: (Int, Int), cellContent: CellContent): BoardCell
}
