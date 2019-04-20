package ai.tablut.adversarial.heuristic

import ai.tablut.state.CellContent._
import ai.tablut.state.Turn.Turn
import ai.tablut.state.{GameContext, State, Turn}

private class HFPawsMajority(context: GameContext) extends HeuristicFunction {

	private val max = context.maxBlacks // 16
	private val min = - context.maxWhites // -9

	override def eval(state: State, player: Turn): Double = {
		val (whites, blacks) = state.board.grid.flatMap(row => row.map(c => c)).foldLeft[(Int, Int)]((0,0))((acc, cell) =>
			if (cell.cellContent == WHITE || cell.cellContent == KING)
				acc.copy(_1 = acc._1 + 1)
			else if (cell.cellContent == BLACK)
				acc.copy(_2 = acc._2 + 1)
			else
				acc
		)

		val delta = player match {
			case Turn.BLACK => blacks - whites
			case Turn.WHITE => whites - blacks
			case _ => return 0.5
		}

		(delta - min) / (max - min).toDouble
	}
}
