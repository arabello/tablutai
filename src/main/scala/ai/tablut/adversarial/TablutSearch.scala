package ai.tablut.adversarial

import ai.tablut.adversarial.heuristic.Phase.Phase
import ai.tablut.adversarial.heuristic._
import ai.tablut.state.implicits._
import ai.tablut.state.{Player, _}

import scala.language.postfixOps

class TablutSearch(gameContext: GameContext, game: TablutGame, time: Int) extends IterativeDeepeningAlphaBetaSearch(game, 0, 1, time){
	private var phase: Phase = Phase.START
	getMetrics.set("phase", phase.id)
	private var whiteHeuristic = HeuristicFactory.createHeuristicFunction(gameContext, Player.WHITE, phase)
	private var blackHeuristic = HeuristicFactory.createHeuristicFunction(gameContext, Player.BLACK, phase)

	def setPhase(phase: Phase): Unit = {
		whiteHeuristic = HeuristicFactory.createHeuristicFunction(gameContext, Player.WHITE, phase)
		blackHeuristic = HeuristicFactory.createHeuristicFunction(gameContext, Player.BLACK, phase)
		getMetrics.set("phase", phase.id)
	}

	/**
	  * Heuristic evaluation of non-terminal states
	  * @param state
	  * @param player
	  * @return
	  */
	// IMPORTANT: When overriding, first call the super implementation!ù
	// RUNTIME
	override def eval(state: State, player: Player.Value): Double = {
		super.eval(state, player)

		val heuristic = if (player == Player.WHITE) whiteHeuristic else blackHeuristic

		val hValue = heuristic.eval(state, player)
		getMetrics.set("hfValue", hValue)
		hValue
	}

	// RUNTIME
	override def orderActions(state: State, actions: List[Action], player: Player.Value, depth: Int): List[Action] = {
		if (depth < 4)
			actions
		else player match {
			case Player.WHITE => actions.sortWith{(a1, a2) =>
				a2.from.cellContent == CellContent.KING && state.distance(a1.from.coords, a1.to.coords) < state.distance(a2.from.coords, a2.to.coords)
			}
			case Player.BLACK =>
				val king = state.findKing
				if (king.isEmpty)
					return actions

				val kingSurrounding = king.get.surroundingAt(1)(state).withFilter(c => c.isDefined && c.get.cellContent == CellContent.EMPTY).map(c => c.get)
				actions.sortWith((a1, a2) => kingSurrounding.contains(a2.to))
		}
	}

}
