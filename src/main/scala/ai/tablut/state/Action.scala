package ai.tablut.state

import ai.tablut.state.Player.Player

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

		((from.cellContent == CellContent.WHITE && who == Player.WHITE) || (from.cellContent == CellContent.BLACK && who == Player.BLACK)) &&
		from.cellContent != CellContent.EMPTY &&
		to.cellContent == CellContent.EMPTY &&
		(to.cellType == CellType.NOTHING || to.cellType == CellType.ESCAPE_POINT) // TODO("Enhance to allow BLACK re-entering camps")
		fromX >= 0 && fromX < gameRules.nRows && fromY >= 0 && fromY < gameRules.nCols &&
		toX >= 0 && toX < gameRules.nRows && toY >= 0 && toY < gameRules.nCols &&
		((fromX == toX && fromY != toY) || (fromY == toY && fromX != toX))
	}

	/**
	  *
	  * @param board
	  * @return True if this element is legitimate according the given board. False otherwise
	  */
	override def isLegal(board: Board): Boolean = {
		val fromX = from.coords._1
		val fromY = from.coords._2
		val toX = from.coords._1
		val toY = from.coords._2

		for (x <- fromX until toX; y <- fromY until toY)
			if (board(x)(y).cellContent != CellContent.EMPTY)
				return false
			else if (board(x)(y).cellType == CellType.CAMP || board(x)(y).cellType == CellType.CASTLE)
				return false

		true
	}
}
