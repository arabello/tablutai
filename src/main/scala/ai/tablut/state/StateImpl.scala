package ai.tablut.state
import ai.tablut.state.CellContent._
import ai.tablut.state.Ending.Ending
import ai.tablut.state.Player.Player
import ai.tablut.state.implicits._

private case class StateImpl(
	                            rows: Int,
	                            cols: Int,
	                            board: Vector[Vector[BoardCell]],
	                            turn: Player,
	                            ending: Option[Ending] = None) extends State with GameRulesComplied {

	def apply(coord: (Int, Int)): Option[BoardCell] = apply(coord._1)(coord._2)

	def apply(x: Int)(y: Int): Option[BoardCell] = try{ Some(board(x)(y)) } catch { case _: Throwable => None }

	/**
	  * Retrieve all the cells considering only the valid coordinates given
	  * @param coords
	  */
	def get(coords: (Int, Int)*)(implicit gameContext: GameContext): Seq[BoardCell] = {
		coords.filter(coord => coord isGameRulesComplied gameContext).flatMap(coord => this (coord))
	}

	def findKing: Option[BoardCell] = getCellsWithFilter(c => c.cellContent == KING).headOption

	override def nextPlayer: State =
		if (turn == Player.WHITE) copy(turn = Player.BLACK) else copy(turn = Player.WHITE)

	def applyAction(action: Action): State = {
		val contentMoved = board(action.from.coords._1)(action.from.coords._2).cellContent
		implicit val afterMove: State = copy(board = board.map(row => row.map(cell =>
			cell.coords match {
				case action.from.coords => cell.copy(cellContent = EMPTY)
				case action.to.coords => cell.copy(cellContent = contentMoved)
				case _ => cell
			}
		))).asInstanceOf[State]

		val allies = (action.to surroundingAt 2).filter(c => c.orNull != null && c.get.cellContent == action.who.toCellContent).map(c => c.get)
		allies.map(c => (action.to until c).drop(1).head).foldLeft[State](afterMove)((acc, enemy) =>
			if ((enemy.cellContent == BLACK && action.who.toCellContent == WHITE) ||
				( (enemy.cellContent == WHITE || enemy.cellContent == KING) && action.who.toCellContent == BLACK))
				acc.clearCells(enemy.coords)
			else
				acc
		).nextPlayer
	}

	/**
	  * List of all cells flatten via row than column
	  * @return
	  */
	def getCells: Seq[BoardCell] = board.flatMap(row => row.map(c => c))


	/**
	  * List of filtered cells flatten via row than column
	  * @return
	  */
	def getCellsWithFilter(filter: BoardCell => Boolean): Seq[BoardCell] = board.flatMap(row => row.filter(filter))

	/**
	  * Synthesize a new state with [[ai.tablut.state.BoardCell#cellContent]] equals to [[ai.tablut.state.CellContent#EMPTY]]
	  * for the given coordinates
	  * @param coords
	  * @return
	  */
	def clearCells(coords: (Int, Int)*): State = {
		val newGrid = board.map(row => row.map(cell =>
			if (coords.contains(cell.coords))
				cell.copy(cellContent = EMPTY)
			else
				cell
		))

		copy(board = newGrid)
	}

	/**
	  * Synthesize a new state with [[ai.tablut.state.BoardCell#cellContent]] equals to the given content
	  * for the given coordinates.
	  * @param coords
	  * @param content
	  * @return
	  */
	def transform(coordsAndConent: Map[(Int, Int), CellContent]): State = {
		copy( board = board.map(row => row.map{ cell =>
			if (!coordsAndConent.contains(cell.coords))
				cell
			else
				cell.copy(cellContent = coordsAndConent(cell.coords))
		}))
	}

	override def allActions(context: GameContext): Seq[Action] = {
		val factory = new ActionFactory(this, context)
		factory.allActions
	}

	override def isGameRulesComplied(gameRules: GameContext): Boolean = ???

	override def toString: String = {
		val sb = scala.collection.mutable.StringBuilder.newBuilder

		sb.append("\n")
		board.foreach { row =>
			row.foreach(cell => sb.append(s" (${cell.cellContent}) "))
			sb.append("\n")
		}
		sb.mkString
	}
}
