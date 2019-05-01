package ai.tablut.adversarial.heuristic

import ai.tablut.state.Player.Player
import ai.tablut.state._


class HotspotStrategy(gameContext: GameContext) extends HeuristicStrategy {
	override val minValue: Int = -4
	override val maxValue: Int = 10

	override def eval(state: State, player: Player): Int =
		state.get((2, 2), (2, 6), (6, 2), (6, 6))(gameContext).foldLeft[Int](0){ (acc, cell) =>
			if (player == Player.BLACK)
				if (cell.cellContent == CellContent.BLACK)
					acc + 1
				else
					acc - 1
			else cell.cellContent match {
				case CellContent.KING => acc + 4
				case CellContent.EMPTY => acc + 2
				case CellContent.WHITE => acc + 1
				case CellContent.BLACK => acc - 1
				case _ => 0
			}
		}
}