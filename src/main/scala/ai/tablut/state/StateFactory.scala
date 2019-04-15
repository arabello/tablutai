package ai.tablut.state

import ai.tablut.state.CellContent.CellContent
import ai.tablut.state.Player.Player

/**
  * Entry point to create state components
  */
trait StateFactory {
	val context: GameContext

	def createState(grid: Seq[Seq[CellContent]], turn: Player): State

	def createBoardCell(coords: (Int, Int), cellContent: CellContent): Option[BoardCell]
}
