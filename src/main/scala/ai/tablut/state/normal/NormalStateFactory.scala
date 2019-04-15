package ai.tablut.state.normal

import ai.tablut.state.CellContent.CellContent
import ai.tablut.state.Player.Player
import ai.tablut.state._

object NormalStateFactory extends StateFactory{

	override val context: GameContext = NormalGameContext

	override def createBoardCell(coords: (Int, Int), cellContent: CellContent.Value): Option[BoardCell] = {

		if(coords._1 > context.nCols || coords._2 > context.nRows)
			None

		val cellType = if (context.camps.contains(coords)) CellType.CAMP
		else if (context.escapePoints.contains(coords)) CellType.ESCAPE_POINT
		else if (context.throne == coords) CellType.CASTLE
		else CellType.NOTHING

		if (context.invalidBoardCell.contains((cellType, cellContent)))
			None
		else
			Some(BoardCell(coords, cellType, cellContent))
	}

	override def createState(grid: Seq[Seq[CellContent]], turn: Player): State = State(
		Board(grid.length, grid.head.length,
			(for(x <- grid.indices) yield (for(y <- grid.head.indices) yield createBoardCell((x,y), grid(x)(y)).get).toVector).toVector),
		turn
	)
}