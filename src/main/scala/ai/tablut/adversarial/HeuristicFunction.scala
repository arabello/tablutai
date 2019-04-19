package ai.tablut.adversarial

import ai.tablut.state.{CellContent, GameContext, Player, State}

class HeuristicFunction(context: GameContext, utilMin: Double, utilMax: Double) {
	def random: Double = scala.math.random()

	def blockEscapePoints(state: State): Double = {
		val playerMultiplier = if (state.turn == Player.BLACK) 1 else -1
		val blockerCells = state.board.get((2,2), (2,6), (6,2), (6,6))(context)
		val step = playerMultiplier * (utilMax - utilMin) / blockerCells.size
		val start = if (state.turn == Player.BLACK) utilMin else utilMax
		blockerCells.foldLeft[Double](start)((acc, cell) =>
			if (cell.cellContent == CellContent.BLACK)
				acc + step
			else
				acc
		)
	}
}
