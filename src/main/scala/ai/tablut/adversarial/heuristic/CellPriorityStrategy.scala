package ai.tablut.adversarial.heuristic
import ai.tablut.state.Player.Player
import ai.tablut.state.{CellContent, GameContext, Player, State}

private class CellPriorityStrategy(gameContext: GameContext) extends HeuristicStrategy {
	override val minValue: Int = 0
	override val maxValue: Int = 20

	override def eval(state: State, player: Player): Int = {
		val mulp = if (player == Player.WHITE) 1 else -1
		var value = if (player == Player.WHITE) minValue else maxValue

		state.get( // block escapes
			(1,2), (1,6),
			(2,1), (2,7),
			(6,1), (6,7),
			(7,2), (7,6)
		)(gameContext).foreach(c => if (c.cellContent == CellContent.EMPTY) value += 1 * mulp)

		state.get( // near castle
			(4, 3), (4, 5),
			(3, 4), (5, 4)
		)(gameContext).foreach(c => if (c.cellContent == CellContent.KING) value += 4 * mulp)

		state.get( // hotspot
			(2, 2), (2, 6),
			(6, 2), (6, 6)
		)(gameContext).foreach(c => if (c.cellContent == CellContent.KING) value += 8 * mulp)

		value
	}
}
