package ai.tablut.adversarial

import java.util

import ai.tablut.adversarial.heuristic._
import ai.tablut.state.implicits._
import ai.tablut.state.{Player, _}

import scala.collection.JavaConverters._
import scala.language.postfixOps

class TablutSearch(gameContext: GameContext, game: TablutGame, time: Int) extends IterativeDeepeningAlphaBetaSearch(game, 0, 1, time){
	private val heuristicFunction = HeuristicFactory.createHeuristicFunction(gameContext)

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

		val hValue = heuristicFunction.eval(state, player)
		getMetrics.set("hfValue", hValue)
		hValue
	}

	// RUNTIME
	override def orderActions(state: State, actions: util.List[Action], player: Player.Value, depth: Int): List[Action] = player match {
		case Player.WHITE => actions.asScala.sortWith{(a1, a2) =>
			a2.who == CellContent.KING && state.distance(a1.from.coords, a1.to.coords) < state.distance(a2.from.coords, a2.to.coords)
		}.toList
		case Player.BLACK =>
			val king = state.findKing
			if (king.isEmpty)
				return actions.asScala.toList

			val kingSurrounding = king.get.surroundingAt(1)(state).withFilter(c => c.isDefined && c.get.cellContent == CellContent.EMPTY).map(c => c.get)
			actions.asScala.sortWith((a1, a2) => kingSurrounding.contains(a2.to)).toList
	}

}
