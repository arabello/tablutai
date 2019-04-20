package ai.tablut.state.normal

import ai.tablut.state.CellContent._
import ai.tablut.state.Turn.Player
import ai.tablut.state._

object NormalStateFactory extends StateFactory{

	override val context: GameContext = NormalGameContext

	override def createInitialState: State = createState(Vector(
		Vector(EMPTY, EMPTY, EMPTY, BLACK, BLACK, BLACK, EMPTY, EMPTY, EMPTY),
		Vector(EMPTY, EMPTY, EMPTY, EMPTY, BLACK, EMPTY, EMPTY, EMPTY, EMPTY),
		Vector(EMPTY, EMPTY, EMPTY, EMPTY, WHITE, EMPTY, EMPTY, EMPTY, EMPTY),
		Vector(BLACK, EMPTY, EMPTY, EMPTY, WHITE, EMPTY, EMPTY, EMPTY, BLACK),
		Vector(BLACK, BLACK, WHITE, WHITE, KING, WHITE, WHITE, BLACK, BLACK),
		Vector(BLACK, EMPTY, EMPTY, EMPTY, WHITE, EMPTY, EMPTY, EMPTY, BLACK),
		Vector(EMPTY, EMPTY, EMPTY, EMPTY, WHITE, EMPTY, EMPTY, EMPTY, EMPTY),
		Vector(EMPTY, EMPTY, EMPTY, EMPTY, BLACK, EMPTY, EMPTY, EMPTY, EMPTY),
		Vector(EMPTY, EMPTY, EMPTY, BLACK, BLACK, BLACK, EMPTY, EMPTY, EMPTY)
	), Turn.WHITE)

	override def createState(grid: Seq[Seq[CellContent]], turn: Player): State = State(
		Board(grid.length, grid.head.length,
			(for(x <- grid.indices) yield (for(y <- grid.head.indices) yield createBoardCell((x,y), grid(x)(y)).get).toVector).toVector),
		turn
	)

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
}