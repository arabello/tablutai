package ai.tablut.state
import ai.tablut.state.CellContent._

case class Board(rows: Int, cols: Int, grid: Vector[Vector[BoardCell]]) extends GameRulesComplied {

	def apply(x: Int)(y: Int): BoardCell = grid(x)(y)

	def get(x: Int)(y: Int): Option[BoardCell] = try{ Some(grid(x)(y)) } catch { case _: Throwable => None }

	def apply(action: Action): Board = {
		import ai.tablut.state.Board.BoardCellImplicits

		val contentMoved = grid(action.from.coords._1)(action.from.coords._2).cellContent
		implicit val afterMove: Board = copy(grid = grid.map(row => row.map(cell =>
			cell.coords match {
				case action.from.coords => cell.copy(cellContent = EMPTY)
				case action.to.coords => cell.copy(cellContent = contentMoved)
				case _ => cell
			}
		)))

		val allies = (action.to surroundingAt 2).filter(c => c.orNull != null && c.get.cellContent == action.who.toCellContent).map(c => c.get)
		allies.map(c => (action.to until c).drop(1).head).foldLeft[Board](afterMove)((acc, enemy) =>
			if ((enemy.cellContent == BLACK && action.who.toCellContent == WHITE) ||
				( (enemy.cellContent == WHITE || enemy.cellContent == KING) && action.who.toCellContent == BLACK))
				acc.clearCell(enemy.coords)
			else
				acc
		)
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
		case (coords._1, coords._2) => cell.copy(cellContent = EMPTY)
		case _ => cell
	})))

	override def isGameRulesComplied(gameRules: GameContext): Boolean = ???

	override def toString: String = {
		val sb = scala.collection.mutable.StringBuilder.newBuilder

		sb.append("\n")
		grid.foreach { row =>
			row.foreach(cell => sb.append(s" (${cell.cellContent}) "))
			sb.append("\n")
		}
		sb.mkString
	}
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

		/**
		  * Create a Vector cells iterating horizontally or vertically from this cell to the given one excluded
		  * within the implicit board
		  * @param to
		  * @param board
		  * @return
		  */
		def until(to: BoardCell)(implicit board: Board): Vector[BoardCell] = segment(from.coords, to.coords, board).dropRight(1)

		/**
		  * Create a Vector cells iterating horizontally or vertically from this cell to the given one included
		  * within the implicit board
		  * @param to
		  * @param board
		  * @return
		  */
		def to(to: BoardCell)(implicit board: Board): Vector[BoardCell] = segment(from.coords, to.coords, board)

		/**
		  * List of the 4 cells surrounding the given coordinates at the specified distance in the order of
		  * up, right, down, left
		  * @param coords
		  * @param distance
		  * @return
		  */
		def surroundingAt(distance: Int)(implicit board: Board): Vector[Option[BoardCell]] = {
			val (x, y) = from.coords
			Vector(board.get(x - distance)(y), board.get(x)(y + distance), board.get(x + distance)(y), board.get(x)(y - distance))
		}
	}
}
