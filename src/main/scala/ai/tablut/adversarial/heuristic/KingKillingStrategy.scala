package ai.tablut.adversarial.heuristic
import ai.tablut.state.Player.Player
import ai.tablut.state.{CellContent, CellType, Player, State}
import ai.tablut.state.implicits._

private class KingKillingStrategy extends HeuristicStrategy {
	override val minValue: Int = -12
	override val maxValue: Int = 12

	override def eval(state: State, player: Player): Int = {
		val findKing = state.findKing
		if (findKing.isEmpty)
			return if (player == Player.WHITE) minValue else maxValue

		val king = findKing.get
		val mulp = if (player == Player.WHITE) -1 else 1

		val surrounding = king.surroundingAt(1)(state)
		val points: Seq[Int] = surrounding.map(s => s.map(c => if (c.cellContent == CellContent.BLACK) 1 else 0).getOrElse(0))
		val start = if (player == Player.WHITE) maxValue else minValue

		var value = points.foldLeft(start)((acc, c) => acc + (mulp * c))

		if (points(0) == 1 && points(2) == 1) value += 2 * mulp
		if (points(1) == 1 && points(3) == 1) value += 2 * mulp
		//if (value == 8 && king.cellType == CellType.CASTLE) value += 88

		surrounding.withFilter(c => c.isDefined && c.get.cellType == CellType.CASTLE).foreach(_ => value += 4 * mulp)
		value
	}
}
