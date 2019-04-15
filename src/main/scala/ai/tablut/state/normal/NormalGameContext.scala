package ai.tablut.state.normal

import ai.tablut.state.CellContent.{Value => _, _}
import ai.tablut.state.CellType._
import ai.tablut.state.{CellContent, CellType, GameContext, State}

private object NormalGameContext extends GameContext{

	override val nRows: Int = 9

	override val nCols: Int = 9

	override val throne: (Int, Int) = (4, 4)

	override val camps: Set[(Int, Int)] = Set(
		(0,3), (0,4), (0,5), (1,4),
		(8,3), (8,4), (8,5), (7,4),
		(3,0), (4,0), (5,0), (4,1),
		(3,8), (4,8), (5,8), (4,8)
	)

	override val escapePoints: Set[(Int, Int)] = Set(
		(0,1), (0,2), (0,6), (0,7),
		(8,1), (8,2), (8,6), (8,7),
		(1,0), (2,0), (6,0), (7,0),
		(1,8), (2,8), (6,8), (7,8)
	)

	override def isWinner(state: State): Boolean = ???

	override val invalidBoardCell: Set[(CellType.Value, CellContent.Value)] = Set(
		(CAMP, WHITE), (CAMP, KING), (CASTLE, WHITE), (CASTLE, BLACK)
	)
}
