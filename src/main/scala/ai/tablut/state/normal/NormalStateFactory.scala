package ai.tablut.state.normal

import ai.tablut.state._

object NormalStateFactory extends StateFactory{

	override val context: GameContext = NormalGameContext

	override def createInitialState(): State = ???

	override def createBoardCell(coords: (Int, Int), cellContent: CellContent.Value): BoardCell = {

		val cellType = if (context.camps.contains(coords)) CellType.CAMP
		else if (context.escapePoints.contains(coords)) CellType.ESCAPE_POINT
		else if (context.throne == coords) CellType.CASTLE
		else CellType.NOTHING

		BoardCellImpl(coords, cellType, cellContent)
	}
}