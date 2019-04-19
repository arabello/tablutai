package ai.tablut.state

object implicits {
	implicit class CoordComplies(coord: (Int, Int)){
		def isGameRulesComplied(gameRules: GameContext): Boolean =
			coord._1 >= 0 && coord._1 < gameRules.nRows && coord._2 >= 0 && coord._2 < gameRules.nCols
	}

	implicit class BoardCellImplicits(from: BoardCell){
		private def segment(from: (Int, Int), to: (Int, Int), board: Board): Seq[BoardCell] = {
			val direction = if (from._1 == to._1) Direction.HORIZONTAL else if (from._2 == to._2) Direction.VERTICAL else return Vector()

			direction match {
				case Direction.HORIZONTAL =>
					if (from._2 >= to._2)
						(for(y <- from._2 to to._2 by -1) yield board(from._1)(y)).toVector
					else
						(for(y <- from._2 to to._2) yield board(from._1)(y)).toVector

				case Direction.VERTICAL =>
					if (from._1 >= to._1)
						(for(x <- from._1 to to._1 by -1) yield board(x)(from._2)).toVector
					else
						(for(x <- from._1 to to._1) yield board(x)(from._2)).toVector
			}
		}

		/**
		  * Create a Vector cells iterating horizontally or vertically from this cell to the given one excluded
		  * within the implicit board
		  * @param to
		  * @param board
		  * @return
		  */
		def until(to: BoardCell)(implicit board: Board): Seq[BoardCell] = segment(from.coords, to.coords, board).dropRight(1)

		/**
		  * Create a Vector cells iterating horizontally or vertically from this cell to the given one included
		  * within the implicit board
		  * @param to
		  * @param board
		  * @return
		  */
		def to(to: BoardCell)(implicit board: Board): Seq[BoardCell] = segment(from.coords, to.coords, board)

		/**
		  * List of the 4 cells surrounding the given coordinates at the specified distance in the order of
		  * up, right, down, left
		  * @param coords
		  * @param distance
		  * @return
		  */
		def surroundingAt(distance: Int)(implicit board: Board): Seq[Option[BoardCell]] = {
			val (x, y) = from.coords
			Vector(board.get(x - distance)(y), board.get(x)(y + distance), board.get(x + distance)(y), board.get(x)(y - distance))
		}
	}
}
