package ai.tablut.adversarial.heuristic

import ai.tablut.state.CellContent._
import ai.tablut.state.Player.Player
import ai.tablut.state.{GameContext, Player, State}

private class HFPawsMajority(context: GameContext) extends HeuristicFunction {

	private val max = context.maxBlacks // 16
	private val min = - context.maxWhites // -9

	override def eval(state: State, player: Player): Double = {
		val (whites, blacks) = state.board.flatMap(row => row.map(c => c)).foldLeft[(Int, Int)]((0,0))((acc, cell) =>
			if (cell.cellContent == WHITE || cell.cellContent == KING)
				acc.copy(_1 = acc._1 + 1)
			else if (cell.cellContent == BLACK)
				acc.copy(_2 = acc._2 + 1)
			else
				acc
		)

		val delta = player match {
			case Player.BLACK => blacks - whites
			case Player.WHITE => whites - blacks
			case _ => return 0.5
		}

		Normalizer.createNormalizer(min, max)(delta)
	}
}
