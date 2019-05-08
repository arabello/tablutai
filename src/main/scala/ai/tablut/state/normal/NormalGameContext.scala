package ai.tablut.state.normal

import ai.tablut.state.CellContent._
import ai.tablut.state.CellType._
import ai.tablut.state._

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

	override val invalidBoardCell: Set[(CellType, CellContent)] = Set(
		(CAMP, WHITE), (CAMP, KING), (CASTLE, WHITE), (CASTLE, BLACK)
	)

	override val escapePoints: Set[(Int, Int)] = Set(
		(0,1), (0,2), (0,6), (0,7),
		(8,1), (8,2), (8,6), (8,7),
		(1,0), (2,0), (6,0), (7,0),
		(1,8), (2,8), (6,8), (7,8)
	)

	override def isWinner(state: State): Boolean = {
		val findKing = state.findKing

		if (findKing.isEmpty){
			if (state.turn == Player.WHITE)
				return false
			else
				return true
		}

		val kingCell = findKing.get

		val isEscaped = escapePoints.contains(kingCell.coords)

		if (isEscaped) state.turn match {
			case Player.WHITE => return true
			case _ => return false
		}

		val (x,y) = kingCell.coords

		val isKilled = kingCell.coords match {

			// King in the castle
			case (throne._1, throne._2) =>

				state(x - 1)(y).exists(c => c.cellContent == BLACK) &&
				state(x)(y + 1).exists(c => c.cellContent == BLACK) &&
				state(x + 1)(y).exists(c => c.cellContent == BLACK) &&
				state(x)(y - 1).exists(c => c.cellContent == BLACK)

			// Near Castle (down)
			case (3,4) =>
				state(x - 1)(y).exists(c => c.cellContent == BLACK) &&
				state(x)(y + 1).exists(c => c.cellContent == BLACK) &&
				state(x)(y - 1).exists(c => c.cellContent == BLACK)

			// Near Castle (right)
			case (4,5) =>
				state(x - 1)(y).exists(c => c.cellContent == BLACK) &&
				state(x + 1)(y).exists(c => c.cellContent == BLACK) &&
				state(x)(y + 1).exists(c => c.cellContent == BLACK)


			// Near Castle (up)
			case (5,4) =>
				state(x + 1)(y).exists(c => c.cellContent == BLACK) &&
				state(x)(y + 1).exists(c => c.cellContent == BLACK) &&
				state(x)(y - 1).exists(c => c.cellContent == BLACK)

			// Near Castle (left)
			case (4,3) =>
				state(x - 1)(y).exists(c => c.cellContent == BLACK) &&
				state(x + 1)(y).exists(c => c.cellContent == BLACK) &&
				state(x)(y - 1).exists(c => c.cellContent == BLACK)

			case _ =>
				(state(x - 1)(y).exists(c => c.cellContent == BLACK || c.cellType == CAMP) && state(x + 1)(y).exists(c => c.cellContent == BLACK || c.cellType == CAMP)) ||
				(state(x)(y - 1).exists(c => c.cellContent == BLACK || c.cellType == CAMP) && state(x)(y + 1).exists(c => c.cellContent == BLACK || c.cellType == CAMP))
		}

		if (isKilled) state.turn match {
			case Player.WHITE => return false
			case _ => return true
		}

		false
	}

	override val maxWhites: Int = 9
	override val maxBlacks: Int = 16
}
