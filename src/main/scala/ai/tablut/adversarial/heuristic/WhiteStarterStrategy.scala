package ai.tablut.adversarial.heuristic
import ai.tablut.state.Player.Player
import ai.tablut.state.{CellContent, GameContext, Player, State}

class WhiteStarterStrategy(gameContext: GameContext) extends HeuristicStrategy {
	override val minValue: Int = 0
	override val maxValue: Int = 9

	private val castleCorners = Seq(
		(3,3), (3,5),
		(5,3), (5,5)
	)

	val castleSides: Seq[(Int, Int)] = Seq(
		(4, 3), (4, 5),
		(3, 4), (5, 4)
	)

	override def eval(state: State, player: Player): Int = player match {
		case Player.WHITE =>
			var value = minValue
			state.get(castleCorners:_ *)(gameContext)
				.withFilter(c => c.cellContent == CellContent.WHITE).foreach(_ => value += 1)

			state.get(castleSides:_ *)(gameContext)
				.withFilter(c => c.cellContent == CellContent.KING).foreach(_ => value += 2)

			value
		case _ => minValue
	}
}
