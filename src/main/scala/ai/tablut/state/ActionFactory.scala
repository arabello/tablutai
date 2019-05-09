package ai.tablut.state

import scala.collection.parallel.mutable.ParArray

object Way extends Enumeration {
	type Way = Value
	val UP, RIGHT, DOWN, LEFT = Value
}

class ActionFactory(state: State, gameContext: GameContext) {
	private val playerCells: Seq[BoardCell] = {
		val c = state.getCellsWithFilter(c => c.cellContent match {
			case CellContent.WHITE => state.turn == Player.WHITE
			case CellContent.BLACK => state.turn == Player.BLACK
			case _ => false
		})

		if (state.turn == Player.WHITE) state.findKing.get +: c else c
	}

	private def upActions(cells: Seq[BoardCell]): Seq[Action] = cells.flatMap(current =>
		for ( x <- (current.coords._1 - 1 to 0 by -1).takeWhile(x => state(x)(current.coords._2).exists(pathCell => pathCell.cellContent == CellContent.EMPTY));
		      a = Action(state.turn, current, state(x)(current.coords._2).get)
		      if a.validate(gameContext, state)
		) yield a)

	private def downActions(cells: Seq[BoardCell]): Seq[Action] = cells.flatMap(current =>
		for ( x <- (current.coords._1 +1 until state.rows).takeWhile(x => state(x)(current.coords._2).exists(pathCell => pathCell.cellContent == CellContent.EMPTY));
			a = Action(state.turn, current, state(x)(current.coords._2).get)
			if a.validate(gameContext, state)
		) yield a)

	private def rightActions(cells: Seq[BoardCell]): Seq[Action] = cells.flatMap(current =>
		for (y <- (current.coords._2 +1  until state.cols).takeWhile(y => state(current.coords._1)(y).exists(pathCell => pathCell.cellContent == CellContent.EMPTY));
		     a = Action(state.turn, current, state(current.coords._1)(y).get)
		     if a.validate(gameContext, state)
		) yield a)

	private def leftActions(cells: Seq[BoardCell]): Seq[Action] = cells.flatMap(current =>
		for ( y <- (current.coords._2 -1 to 0 by -1).takeWhile(y => state(current.coords._1)(y).exists(pathCell => pathCell.cellContent == CellContent.EMPTY));
		      a = Action(state.turn, current, state(current.coords._1)(y).get)
		      if a.validate(gameContext, state)
		) yield a)

	private def allWayActions(cells: Seq[BoardCell]): Seq[Action] =
		//upActions(cells) ++ rightActions(cells) ++ downActions(cells) ++ leftActions(cells)
		ParArray(upActions(cells), rightActions(cells), downActions(cells), leftActions(cells))
			.foldLeft[Seq[Action]](Seq())((acc, actions) => acc ++ actions)

	def createKingActions: Seq[Action] = allWayActions(state.findKing.toList)

	def createActions: Seq[Action] = allWayActions(playerCells)

}
