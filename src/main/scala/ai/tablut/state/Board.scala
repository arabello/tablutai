package ai.tablut.state

case class Board(rows: Int, cols: Int, grid: Vector[Vector[BoardCell]]) extends GameRulesComplied {

	def apply(x: Int)(y: Int): BoardCell = grid(x)(y)

	def apply(action: Action): Board = {
		val contentMoved = grid(action.from.coords._1)(action.from.coords._2).cellContent
		//implicit val afterMove: Board =
		copy(grid = grid.map(row => row.map(cell =>
			cell.coords match {
				case action.from.coords => cell.copy(cellContent = CellContent.EMPTY)
				case action.to.coords => cell.copy(cellContent = contentMoved)
				case _ => cell
			}
		)))
	}

	/**
	  * List of all cells flatten via row than column
	  * @return
	  */
	def getCells: Vector[BoardCell] = grid.flatMap(row => row.map(c => c))

	/**
	  * Synthesize a new board with [[ai.tablut.state.BoardCell#cellContent]] equals to [[ai.tablut.state.CellContent#EMPTY]]
	  * for the given coordinates
	  * @param coords
	  * @return
	  */
	def clearCell(coords: (Int, Int)): Board = copy(grid = grid.map(row => row.map(cell => cell.coords match{
		case (coords._1, coords._2) => cell.copy(cellContent = CellContent.EMPTY)
		case _ => cell
	})))

	/**
	  * List of the 4 cells surrounding the given coordinates at the specified distance in the order of
	  * up, right, down, left
	  * @param coords
	  * @param distance
	  * @return
	  */
	def surroundingOf(coords: (Int, Int), distance: Int): Vector[BoardCell] = {
		val (x, y) = coords
		Vector(grid(x - distance)(y), grid(x)(y + distance), grid(x + distance)(y), grid(x)(y - distance))
	}

	override def isGameRulesComplied(gameRules: GameContext): Boolean = ???
}

object Board{
	implicit class BoardCellImplicits(from: BoardCell){
		private def segment(from: (Int, Int), to: (Int, Int), board: Board): Vector[BoardCell] = {
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

		def until(to: BoardCell)(implicit board: Board): Vector[BoardCell] = segment(from.coords, to.coords, board).dropRight(1)
		def to(to: BoardCell)(implicit board: Board): Vector[BoardCell] = segment(from.coords, to.coords, board)
	}
}
