package ai.tablut.state

import ai.tablut.state.Way.Way

object Way extends Enumeration {
	type Way = Value
	val UP, RIGHT, DOWN, LEFT = Value
}

class ActionFactory(state: State, gameContext: GameContext) {

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

	private def actions(cells: Seq[BoardCell], way: Way): Seq[Action] = way match {
		case Way.UP => upActions(cells)
		case Way.RIGHT => rightActions(cells)
		case Way.LEFT => leftActions(cells)
		case Way.DOWN => downActions(cells)
		case _ => Seq()
	}

	def actions(cells: Seq[BoardCell]): Seq[Action] = Way.values.toParArray.foldLeft[Seq[Action]](Seq())((acc, way) => acc ++ actions(cells, way))

	def actions(boardCell: BoardCell): Seq[Action] = actions(Seq(boardCell))

	def allActions: Seq[Action] = {
		val cells = state.getCellsWithFilter(c => c.cellContent match {
			case CellContent.WHITE | CellContent.KING => state.turn == Player.WHITE
			case CellContent.BLACK => state.turn == Player.BLACK
			case _ => false
		})

		actions(cells)
	}

}
