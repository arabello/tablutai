package ai.tablut.state

import ai.tablut.state.Way.Way

class ActionFactory(state: State, gameContext: GameContext) {

	private def upActions(cells: Seq[BoardCell]): Seq[Action] =cells.flatMap(c =>
		for ( x <- (c.coords._1 - 1 to 0 by -1).takeWhile(x => state.board(x)(c.coords._2).cellContent == CellContent.EMPTY);
		      a = Action(state.turn, c, state.board(x)(c.coords._2))
		      if a.validate(gameContext, state.board)
		) yield a)

	private def downActions(cells: Seq[BoardCell]): Seq[Action] = cells.flatMap(c =>
		for ( x <- (c.coords._1 +1 until state.board.rows).takeWhile(x => state.board(x)(c.coords._2).cellContent == CellContent.EMPTY);
			a = Action(state.turn, c, state.board(x)(c.coords._2))
			if a.validate(gameContext, state.board)
		) yield a)

	private def rightActions(cells: Seq[BoardCell]): Seq[Action] = cells.flatMap(c =>
		for (y <- (c.coords._2 +1  until state.board.cols).takeWhile(y => state.board(c.coords._1)(y).cellContent == CellContent.EMPTY);
		     a = Action(state.turn, c, state.board(c.coords._1)(y))
		     if a.validate(gameContext, state.board)
		) yield a)

	private def leftActions(cells: Seq[BoardCell]): Seq[Action] = cells.flatMap(c =>
		for ( y <- (c.coords._2 -1 to 0 by -1).takeWhile(y => state.board(c.coords._1)(y).cellContent == CellContent.EMPTY);
		      a = Action(state.turn, c, state.board(c.coords._1)(y))
		      if a.validate(gameContext, state.board)
		) yield a)

	private def actions(cells: Seq[BoardCell], way: Way): Seq[Action] = way match {
		case Way.UP => upActions(cells)
		case Way.RIGHT => rightActions(cells)
		case Way.LEFT => leftActions(cells)
		case Way.DOWN => downActions(cells)
		case _ => Seq()
	}

	def allActions: Seq[Action] = {
		val cells = state.board.getCells.filter(c => c.cellContent match {
			case CellContent.WHITE | CellContent.KING => state.turn == Turn.WHITE
			case CellContent.BLACK => state.turn == Turn.BLACK
			case _ => false
		})

		Way.values.foldLeft[Seq[Action]](Seq())((acc, way) => acc ++ actions(cells, way))
	}

	/*
		val cells = state.board.getCells.filter(c => c.cellContent match {
			case CellContent.WHITE | CellContent.KING => state.turn == Turn.WHITE
			case CellContent.BLACK => state.turn == Turn.BLACK
			case _ => false
		})

		Way.values.foldLeft[Seq[Action]](Seq())((acc, way) => acc ++ actions(cells, way))
	 */


	/*
		val cells = state.board.getCells.filter(c => c.cellContent match {
			case CellContent.WHITE | CellContent.KING => state.turn == Turn.WHITE
			case CellContent.BLACK => state.turn == Turn.BLACK
			case _ => false
		})

		val promises = for (w <- Way.values) yield w -> Promise[Seq[Action]]()
		val futures = for (p <- promises) yield p._2.future

		promises.foreach { case (w, p) =>
			Future{
				p success actions(cells, w)
			}
		}

		futures.foldLeft[Seq[Action]](Vector())((acc, f) => acc ++ Await.result(f, 500 millis))
	 */
}
