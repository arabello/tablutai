package ai.tablut.state.normal

import ai.tablut.state.CellContent._
import ai.tablut.state.Ending.Ending
import ai.tablut.state.Player.Player
import ai.tablut.state._

object NormalStateFactory extends StateFactory{

	override val context: GameContext = NormalGameContext

	private val initContent = Vector(
		Vector(EMPTY, EMPTY, EMPTY, BLACK, BLACK, BLACK, EMPTY, EMPTY, EMPTY),
		Vector(EMPTY, EMPTY, EMPTY, EMPTY, BLACK, EMPTY, EMPTY, EMPTY, EMPTY),
		Vector(EMPTY, EMPTY, EMPTY, EMPTY, WHITE, EMPTY, EMPTY, EMPTY, EMPTY),
		Vector(BLACK, EMPTY, EMPTY, EMPTY, WHITE, EMPTY, EMPTY, EMPTY, BLACK),
		Vector(BLACK, BLACK, WHITE, WHITE, KING, WHITE, WHITE, BLACK, BLACK),
		Vector(BLACK, EMPTY, EMPTY, EMPTY, WHITE, EMPTY, EMPTY, EMPTY, BLACK),
		Vector(EMPTY, EMPTY, EMPTY, EMPTY, WHITE, EMPTY, EMPTY, EMPTY, EMPTY),
		Vector(EMPTY, EMPTY, EMPTY, EMPTY, BLACK, EMPTY, EMPTY, EMPTY, EMPTY),
		Vector(EMPTY, EMPTY, EMPTY, BLACK, BLACK, BLACK, EMPTY, EMPTY, EMPTY)
	)

	private lazy val initState = StateImpl(
		initContent.size,
		initContent.head.size,
		(for(x <- initContent.indices)
			yield (for(y <- initContent.head.indices)
				yield createBoardCell((x,y), initContent(x)(y)).get).toVector).toVector,
		Player.WHITE
	)

	override def createInitialState(): State = initState.copy()

	override def createState(grid: Seq[Seq[CellContent]], turn: Player, ending: Option[Ending] = None): State = StateImpl(
		grid.length,
		grid.head.length,
		(for(x <- grid.indices)
			yield (for(y <- grid.head.indices)
				yield createBoardCell((x,y), grid(x)(y)).get).toVector).toVector,
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