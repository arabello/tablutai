package ai.tablut.state

import ai.tablut.state.CellContent.CellContent
import ai.tablut.state.Ending.Ending
import ai.tablut.state.Player.Player

/**
  * Entry point to create state components
  */
trait StateFactory {
	val context: GameContext

	def createInitialState(): State

	def createState(board: Seq[Seq[CellContent]], turn: Player, ending: Option[Ending] = None): State

	def createBoardCell(coords: (Int, Int), cellContent: CellContent): Option[BoardCell]
}
