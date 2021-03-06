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
	                            var kingCoords: Option[(Int, Int)] = None,
	                            ending: Option[Ending] = None) extends State{

	def apply(coord: (Int, Int)): Option[BoardCell] = apply(coord._1)(coord._2)

	def apply(x: Int)(y: Int): Option[BoardCell] = try{ Some(board(x)(y)) } catch { case _: Throwable => None }

	/**
	  * Retrieve all the cells considering only the valid coordinates given
	  * @param coords
	  */
	def get(coords: (Int, Int)*)(implicit gameContext: GameContext): Seq[BoardCell] = {
		coords.withFilter(coord => coord isGameRulesComplied gameContext).flatMap(coord => this (coord))
	}

	def findKing: Option[BoardCell] = if (kingCoords.isDefined) apply(kingCoords.get) else None
	//getCellsWithFilter(c => c.cellContent == KING).headOption

	override def nextPlayer: State =
		if (turn == Player.WHITE) copy(turn = Player.BLACK) else copy(turn = Player.WHITE)

	def applyAction(action: Action): State = {
		val contentMoved = board(action.from.coords._1)(action.from.coords._2).cellContent

		implicit val afterMove: StateImpl = copy(board = board.map(row => row.map(cell =>
			cell.coords match {
				case action.from.coords => cell.copy(cellContent = EMPTY)
				case action.to.coords => cell.copy(cellContent = contentMoved)
				case _ => cell
			}
		)))

		if (contentMoved == CellContent.KING)
			afterMove.kingCoords = Some(action.to.coords)

		val allies = (action.to surroundingAt 2).withFilter(c => c.orNull != null
			&& (c.get.cellContent == action.who.toCellContent || c.get.cellType == CellType.CASTLE || c.get.cellType == CellType.CAMP)).map(c => c.get)

		val eat = allies.map(c => (action.to until c).drop(1).head).foldLeft[State](afterMove)((acc, enemy) =>
			if ((enemy.cellContent == BLACK && action.who.toCellContent == WHITE) ||
				( (enemy.cellContent == WHITE || enemy.cellContent == KING) && action.who.toCellContent == BLACK))
				acc.clearCells(enemy.coords)
			else
				acc
		).nextPlayer.asInstanceOf[StateImpl] // DO NOT alternate player turn. For that call #nextPlayer

		if (eat.findKing.exists(c => c.cellContent == CellContent.EMPTY))
			eat.kingCoords = None

		eat
	}

	/**
	  * List of all cells flatten via row than column
	  * @return
	  */
	def getCells: Seq[BoardCell] = for (row <- board; cell <- row) yield cell


	/**
	  * List of filtered cells flatten via row than column
	  * @return
	  */
	def getCellsWithFilter(filter: BoardCell => Boolean): Seq[BoardCell] = for (row <- board; cell <- row; if filter(cell)) yield cell

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
		var kcoords: Option[(Int, Int)] = None
		val newBoard = board.map(row => row.map{ cell =>
			if (!coordsAndConent.contains(cell.coords))
				cell
			else {
				val content = coordsAndConent(cell.coords)
				if (content == CellContent.KING)
					kcoords = Some(cell.coords)
				cell.copy(cellContent = content)
			}
		})

		if (kcoords.isDefined)
			copy(board = newBoard, kingCoords = kcoords)
		else
			copy(board = newBoard)
	}

	override def isGameRulesComplied(gameRules: GameContext): Boolean = true

	override def toString: String = {
		val sb = scala.collection.mutable.StringBuilder.newBuilder
                var r = 0
		sb.append("\n")
		board.foreach { row =>
			row.foreach{cell =>
				val char: Char = cell.cellContent match {
					case EMPTY => '\u2591'
					case _ => cell.cellContent.toString.head
				}
				sb.append(s" $char")
			}
			sb.append(s" $r\n")
                        r += 1
		}
                for (c <- 0 until cols) sb.append(s" $c")
                sb.append("\n")
		sb.mkString
	}
}
