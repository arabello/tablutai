package ai.tablut.adversarial.heuristic
import ai.tablut.state.Player.Player
import ai.tablut.state.{CellContent, GameContext, Player, State}

private class CellPriorityStrategy(gameContext: GameContext) extends HeuristicStrategy {
	override val minValue: Int = -10
	override val maxValue: Int = 70

	val outerCircle: Seq[(Int, Int)] = Seq(
		(1,2), (1,6),
		(2,1), (2,7),
		(6,1), (6,7),
		(7,2), (7,6))

	val innerCircle: Seq[(Int, Int)] = Seq(
		(2,3), (3,5),
		(3,2), (3,6),
		(5,2), (5,6),
		(6,3), (6,5))

	val castleSides: Seq[(Int, Int)] = Seq(
		(4, 3), (4, 5),
		(3, 4), (5, 4)
	)

	val hotspot: Seq[(Int, Int)] = Seq(
		(2, 2), (2, 6),
		(6, 2), (6, 6)
	)

	val boardCorners: Seq[(Int, Int)] = Seq(
		(0,0),(0, gameContext.nCols-1),(gameContext.nRows-1, 0), (gameContext.nRows-1, gameContext.nCols-1)
	)

	private def contained(value: Int): Int = if (value < minValue) minValue else if (value > maxValue) maxValue else value

	override def eval(state: State, player: Player): Int = player match{
		case Player.WHITE =>
			var value = minValue

			state.get(castleSides: _*)(gameContext)
				.withFilter(c => c.cellContent == CellContent.EMPTY).foreach(_ => value += 1)

			state.get(boardCorners: _*)(gameContext)
                .withFilter(c => c.cellContent == CellContent.WHITE).foreach(_ => value -= 1)

			state.get(hotspot: _*)(gameContext)
				.withFilter(c => c.cellContent == CellContent.KING).foreach(_ => value += 20)

			state.get(gameContext.escapePoints.toList: _*)(gameContext).foreach(c => c.cellContent match {
				case CellContent.BLACK | CellContent.WHITE => value -= 1
				case CellContent.EMPTY => value += 4
				case _ =>
			})

			contained(value)

		case Player.BLACK =>
			var value = minValue

			state.get(gameContext.escapePoints.toList: _*)(gameContext)
				.withFilter(c => c.cellContent == CellContent.BLACK).foreach(_ => value += 1)

			state.get(innerCircle: _*)(gameContext)
				.withFilter(c => c.cellContent == CellContent.BLACK).foreach(_ => value += 4)

			state.get(outerCircle: _*)(gameContext)
				.withFilter(c => c.cellContent == CellContent.BLACK).foreach(_ => value += 4)

			state.get(hotspot: _*)(gameContext)
				.withFilter(c => c.cellContent == CellContent.KING).foreach(_ => value -= 10)

			contained(value)
	}
}
