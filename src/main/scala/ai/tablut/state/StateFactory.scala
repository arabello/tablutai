package ai.tablut.state

import ai.tablut.state.CellContent.CellContent
import ai.tablut.state.Turn.Turn

/**
  * Entry point to create state components
  */
trait StateFactory {
	val context: GameContext

	def createInitialState(): State

	def createState(grid: Seq[Seq[CellContent]], turn: Turn): State

	def createBoardCell(coords: (Int, Int), cellContent: CellContent): Option[BoardCell]
}
