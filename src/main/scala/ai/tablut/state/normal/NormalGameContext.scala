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
		val kingCell = (for(
			x <- state.board.grid.indices;
			y <- state.board.grid(x).indices;
			c = state.board.grid(x)(y)
			if c.cellContent == KING
		) yield c).head

		if (state.turn == Player.WHITE){
			escapePoints.contains(kingCell.coords)
		}else{
			val (x,y) = kingCell.coords

			kingCell.coords match {

				// King in the castle
				case (throne._1, throne._2) =>

					state.board.grid(x - 1)(y).cellContent == BLACK &&
					state.board.grid(x)(y + 1).cellContent == BLACK &&
					state.board.grid(x + 1)(y).cellContent == BLACK &&
					state.board.grid(x)(y - 1).cellContent == BLACK

				// Near Castle (down)
				case (3,4) =>
					state.board.grid(x - 1)(y).cellContent == BLACK &&
					state.board.grid(x)(y + 1).cellContent == BLACK &&
					state.board.grid(x)(y - 1).cellContent == BLACK

				// Near Castle (right)
				case (4,5) =>
					state.board.grid(x - 1)(y).cellContent == BLACK &&
					state.board.grid(x + 1)(y).cellContent == BLACK &&
					state.board.grid(x)(y + 1).cellContent == BLACK


				// Near Castle (up)
				case (5,4) =>
					state.board.grid(x + 1)(y).cellContent == BLACK &&
					state.board.grid(x)(y + 1).cellContent == BLACK &&
					state.board.grid(x)(y - 1).cellContent == BLACK

				// Near Castle (left)
				case (4,3) =>
					state.board.grid(x - 1)(y).cellContent == BLACK &&
					state.board.grid(x + 1)(y).cellContent == BLACK &&
					state.board.grid(x)(y - 1).cellContent == BLACK


				// Nearc up camp
				case (1,3) | (2,4) | (1,5) => state.board.grid(x + 1)(y).cellContent == BLACK
				// Nearc left camp
				case (3,1) | (4,2) | (5,1) => state.board.grid(x)(y + 1).cellContent == BLACK
				// Nearc right camp
				case (3,7) | (4,6) | (5,7) => state.board.grid(x)(y - 1).cellContent == BLACK
				// Nearc down camp
				case (7,3) | (6,4) | (7,5) => state.board.grid(x - 1)(y).cellContent == BLACK

				case _ =>
					(state.board.grid(x - 1)(y).cellContent == BLACK && state.board.grid(x + 1)(y).cellContent == BLACK) ||
					(state.board.grid(x)(y - 1).cellContent == BLACK && state.board.grid(x)(y - 1).cellContent == BLACK)
			}
		}
	}

	override val maxWhites: Int = 9
	override val maxBlacks: Int = 16
}
