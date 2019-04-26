package ai.tablut.adversarial

import java.util

import ai.tablut.adversarial.heuristic.{HeuristicBuilder, HeuristicFunction, NormalGameHeuristicFactory}
import ai.tablut.state.Player.Player
import ai.tablut.state.implicits._
import ai.tablut.state.{Player, _}
import aima.core.search.adversarial.IterativeDeepeningAlphaBetaSearch

import scala.collection.JavaConverters._

class IDABSimpleSearch(context: GameContext, game: TablutGame, time: Int) extends IterativeDeepeningAlphaBetaSearch(game, 0, 1, time){

	private val hKingAssasination: HeuristicFunction = NormalGameHeuristicFactory.createKingAssasination()
	private val hBlockEscapePoints: HeuristicFunction = NormalGameHeuristicFactory.createBlockEscapePoints()
	private val hPawsMajority: HeuristicFunction = NormalGameHeuristicFactory.createPawsMajority()
	private val hBuilder = new HeuristicBuilder().adaptDomain(this.utilMin, this.utilMax)

	val heuristic: (State, Player) => Double = hBuilder
		.add(hPawsMajority, 4)
		.add(hBlockEscapePoints, 4)
		.add(hKingAssasination, 4)
		.build


	/**
	  * Heuristic evaluation of non-terminal states
	  * @param state
	  * @param player
	  * @return
	  */
	// IMPORTANT: When overriding, first call the super implementation!
	override def eval(state: State, player: Player.Value): Double = { // CRITICAL called 161.000 with 20 seconds thinking
		super.eval(state, player)

		val hValue = heuristic(state, player)
		getMetrics.set("hfValue", hValue)
		hValue
	}

	override def orderActions(state: State, actions: util.List[Action], player: Player.Value, depth: Int): util.List[Action] = player match {
		case Player.WHITE => actions.asScala.sortWith((a1, a2) => a2.who == CellContent.KING).asJava
		case Player.BLACK =>
			val king = state.findKing
			if (king.isEmpty)
				return actions

			val kingSurrounding = king.get.surroundingAt(1)(state).filter(c => c.isDefined && c.get.cellContent == CellContent.EMPTY).map(c => c.get)
			actions.asScala.sortWith((a1, a2) => kingSurrounding.contains(a2.to)).asJava
	}

}
