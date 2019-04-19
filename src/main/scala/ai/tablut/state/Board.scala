package ai.tablut.state
import ai.tablut.state.CellContent._
import ai.tablut.state.implicits._

case class Board(rows: Int, cols: Int, grid: Seq[Seq[BoardCell]]) extends GameRulesComplied {

	def apply(coord: (Int, Int)): BoardCell = grid(coord._1)(coord._2)

	def apply(x: Int)(y: Int): BoardCell = grid(x)(y)

	def get(x: Int)(y: Int): Option[BoardCell] = try{ Some(grid(x)(y)) } catch { case _: Throwable => None }

	/**
	  * Retrieve all the cells considering only the valid coordinates given
	  * @param coords
	  */
	def get(coords: (Int, Int)*)(implicit gameContext: GameContext): Seq[BoardCell] = {
		coords.filter(coord => coord isGameRulesComplied gameContext).map(coord => this(coord))
	}

	def apply(action: Action): Board = {

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
	def getCells: Seq[BoardCell] = grid.flatMap(row => row.map(c => c))

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
