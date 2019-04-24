package ai.tablut.adversarial.heuristic
import ai.tablut.state.CellContent._
import ai.tablut.state.Turn.Turn
import ai.tablut.state.{GameContext, State, StateFactory, Turn}

class HFNearKing(stateFactory: StateFactory) extends HeuristicFunction {

	private def distance(black: (Int, Int), king: (Int, Int)): Double =
		scala.math.sqrt(math.pow(king._1 - black._1, 2) + math.pow(king._2 - black._2, 2))

	override def eval(state: State, player: Turn): Double = {
		val findKing = state.board.getCellsWithFilter(c => c.cellContent == KING).headOption
		if (findKing.isEmpty)
			return 1
		val king = findKing.get
		val blacks = state.board.getCellsWithFilter(c => c.cellContent == BLACK)

		val min = distance((0,0), (0,1))
		val max = distance((0,0), (7,7)) * stateFactory.context.maxBlacks

		val start = if (player == Turn.BLACK) min else stateFactory.createInitialState().board
			.getCellsWithFilter(c => c.cellContent == BLACK)
			.foldLeft[Double](0){(acc, black) =>
			acc + distance(black.coords, king.coords)
		}

		val eval = blacks.foldLeft[Double](start.toDouble){(acc, black) =>
			acc + distance(black.coords, king.coords)
		}

		Normalizer.createNormalizer(min, max)(if (player == Turn.BLACK) max - eval else eval - start)
	}
}
