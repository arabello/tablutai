package ai.tablut.adversarial

import ai.tablut.LogInterceptor
import ai.tablut.adversarial.heuristic.Phase.Phase
import ai.tablut.adversarial.heuristic._
import ai.tablut.state.{Player, _}

import scala.language.postfixOps

class TablutSearch(gameContext: GameContext, game: TablutGame, time: Int) extends IterativeDeepeningAlphaBetaSearch(game, 0, 1, time){
	private var phase: Phase = Phase.START
	private val heuristicFactory = new HeuristicFactory(time)
	private var whiteHeuristic = heuristicFactory.createHeuristicFunction(gameContext, Player.WHITE, phase, currDepthLimit)
	private var blackHeuristic = heuristicFactory.createHeuristicFunction(gameContext, Player.BLACK, phase, currDepthLimit)

	def setPhase(phase: Phase): Unit = {
		this.phase = phase
	}

	onDepthUpdate = depth => {
		whiteHeuristic = heuristicFactory.createHeuristicFunction(gameContext, Player.WHITE, phase, depth)
		blackHeuristic = heuristicFactory.createHeuristicFunction(gameContext, Player.BLACK, phase, depth)
		LogInterceptor{
			if (heuristicFactory.isIntensiveUsed)
				println(s"Intensive heuristic at depth $depth")
		}
	}

	/**
	  * Heuristic evaluation of non-terminal states
	  * @param state
	  * @param player
	  * @return
	  */
	// IMPORTANT: When overriding, first call the super implementation!
	// RUNTIME
	override def eval(state: State, player: Player.Value): Double = {
		val eval = super.eval(state, player)
		LogInterceptor{
			getMetrics.set("hfValue", eval)
			getMetrics.set("phase", phase.id)
		}

		if (eval == utilMin || eval == utilMax)
			return eval

		val heuristic = if (player == Player.WHITE) whiteHeuristic else blackHeuristic

		val hValue = heuristic.eval(state, player)
		LogInterceptor{
			getMetrics.set("hfValue", hValue)
		}
		hValue
	}

	// RUNTIME
	override def orderActions(state: State, actions: Seq[Action], player: Player.Value, depth: Int): Seq[Action] =
		actions.sortWith((a1, a2) =>
			(a1.from.cellContent == CellContent.KING && a2.from.cellContent != CellContent.KING) ||
			(a1.from.cellContent == CellContent.KING && a2.from.cellContent == CellContent.KING && state.distance(a1.from.coords, a1.to.coords) > state.distance(a2.from.coords, a2.to.coords))
		)



	/*
	case Player.BLACK =>
	val king = state.findKing
	if (king.isEmpty)
		return actions

	val kingSurrounding = king.get.surroundingAt(1)(state).withFilter(c => c.isDefined && c.get.cellContent == CellContent.EMPTY).map(c => c.get)
	actions.sortWith((a1, a2) => kingSurrounding.contains(a1.to))
	 */

}
