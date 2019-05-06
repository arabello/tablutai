package ai.tablut.adversarial.heuristic
import ai.tablut.state.Player.Player
import ai.tablut.state.{CellContent, GameContext, Player, State}

class WhiteStarterStrategy(gameContext: GameContext) extends HeuristicStrategy {
	override val minValue: Int = 0
	override val maxValue: Int = 4

	override def eval(state: State, player: Player): Int = player match {
		case Player.WHITE =>
			state.get(
				(3,3), (3,5),
				(5,3), (5,5)
			)(gameContext).foldLeft[Int](0)((acc, c) =>
				if (c.cellContent == CellContent.WHITE) acc + 1 else acc)

		case _ => minValue
	}
}
