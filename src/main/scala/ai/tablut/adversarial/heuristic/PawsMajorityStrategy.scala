package ai.tablut.adversarial.heuristic

import ai.tablut.state.CellContent._
import ai.tablut.state.Player.Player
import ai.tablut.state.{Player, State}

class PawsMajorityStrategy extends HeuristicStrategy {

	override val minValue: Int = -9
	override val maxValue: Int = 16

	override def eval(state: State, player: Player): Int = {
		val (whites, blacks) = state.board.flatMap(row => row.map(c => c)).foldLeft[(Int, Int)]((0,0))((acc, cell) =>
			if (cell.cellContent == WHITE || cell.cellContent == KING)
				acc.copy(_1 = acc._1 + 1)
			else if (cell.cellContent == BLACK)
				acc.copy(_2 = acc._2 + 1)
			else
				acc
		)

		player match {
			case Player.BLACK => blacks - whites
			case Player.WHITE => whites - blacks
			case _ => 0
		}
	}

}
