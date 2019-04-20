package ai.tablut.state

import ai.tablut.state
import ai.tablut.state.Turn.Player
import ai.tablut.state.implicits._

/**
  * Data structure to handle a player game move
 *
  * @param who
  * @param from
  * @param to
  */
case class Action(who: Player, from: BoardCell, to: BoardCell) extends GameRulesComplied with Legal {

	/**
	  * Check if game rules complied and legal within the current game rules and board
	  * @param gameRules
	  * @param board
	  * @return
	  */
	def validate(gameRules: GameContext, board: Board): Boolean = isGameRulesComplied(gameRules) && isLegal(board)

	/**
	  * @return True if it meets the game rules. False otherwise.
	  */
	override def isGameRulesComplied(gameRules: GameContext): Boolean = {
		val fromX = from.coords._1
		val fromY = from.coords._2
		val toX = to.coords._1
		val toY = to.coords._2

		((from.cellContent == CellContent.WHITE && who == Turn.WHITE) || (from.cellContent == CellContent.BLACK && who == Turn.BLACK)) &&
		from.cellContent != CellContent.EMPTY &&
		to.cellContent == CellContent.EMPTY &&
		(to.cellType == CellType.NOTHING || to.cellType == CellType.ESCAPE_POINT) // TODO("Enhance to allow BLACK re-entering camps")
		((fromX, fromY) isGameRulesComplied gameRules) &&
		((toX, toY) isGameRulesComplied gameRules) &&
		((fromX == toX && fromY != toY) || (fromY == toY && fromX != toX))
	}

	/**
	  *
	  * @param board
	  * @return True if this element is legitimate according the given board. False otherwise
	  */
	override def isLegal(board: Board): Boolean = {
		import scala.math.{max, min}

		if (from == to)
			return false

		val fromX = min(from.coords._1, to.coords._1)
		val fromY = min(from.coords._2, to.coords._2)
		val toX = max(from.coords._1, to.coords._1)
		val toY = max(from.coords._2, to.coords._2)

		val fromCoord = if (fromX == toX) fromY else fromX
		val toCoord = if (fromY == toY) toX else toY

		val isXFixed = fromX == toX

		val f = fromCoord + (if (isXFixed) // Horizontal move
					if (from.coords._2 < to.coords._2) 1 else 0
				else // Vertical move
					if (from.coords._1 < to.coords._1) 1 else 0)

		val t = toCoord + (if (isXFixed) // Horizontal move
					if (from.coords._2 < to.coords._2) 0 else -1
				else // Vertical move
					if (from.coords._1 < to.coords._1) 0 else -1)

		val between = if (isXFixed) for (y <- f to t) yield (fromX, y) else for(x <- f to t) yield (x, fromY)

		for(coords <- between; cell = board.grid(coords._1)(coords._2))
			if (cell.cellContent != CellContent.EMPTY || cell.cellType == CellType.CASTLE || cell.cellType == CellType.CAMP)
				return false

		true
	}

	override def toString: String = s"$who : ${from.coords}[${from.cellContent}] -> ${to.coords}[${to.cellContent}]"
}
