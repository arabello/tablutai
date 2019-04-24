package ai.tablut.adversarial

import java.util

import ai.tablut.adversarial.heuristic.{HeuristicBuilder, HeuristicFunction, NormalGameHeuristicFactory}
import ai.tablut.state.{Turn, _}
import aima.core.search.adversarial.IterativeDeepeningAlphaBetaSearch
import scala.collection.JavaConverters._

class IDABSimpleSearch(context: GameContext, game: TablutGame, time: Int) extends IterativeDeepeningAlphaBetaSearch(game, 0, 1, time){

	val hKingAssasination: HeuristicFunction = NormalGameHeuristicFactory.createKingAssasination()
	val hBlockEscapePoints: HeuristicFunction = NormalGameHeuristicFactory.createBlockEscapePoints()
	val hPawsMajority: HeuristicFunction = NormalGameHeuristicFactory.createPawsMajority()

	private val hBuilder = new HeuristicBuilder().adaptDomain(this.utilMin, this.utilMax)

	/**
	  * Heuristic evaluation of non-terminal states
	  * @param state
	  * @param player
	  * @return
	  */
	// IMPORTANT: When overriding, first call the super implementation!
	override def eval(state: State, player: Turn.Value): Double = {
		super.eval(state, player)

		val heuristic = hBuilder
    		.add(hPawsMajority, 6)
			.add(hBlockEscapePoints, 4)
    		.add(hKingAssasination, 2)
    		.build

		val hValue = heuristic(state, player)
		getMetrics.set("hfValue", hValue)
		hValue
	}

	override def orderActions(state: State, actions: util.List[Action], player: Turn.Value, depth: Int): util.List[Action] = {
		val res = actions.asScala.sortWith{(a1, a2) =>
			val (fromX1, fromY1) = a1.from.coords
			val (fromX2, fromY2) = a2.from.coords
			val (toX1, toY1) = a1.to.coords
			val (toX2, toY2) = a2.to.coords

			val distance1 = math.abs(if (fromX1 == toX1) fromY1 - toY1 else fromX1 - toX1)
			val distance2 = math.abs(if (fromX2 == toX2) fromY2 - toY2 else fromX2 - toX2)

			distance1 > distance2
		}
		println(s"${res.head.distance} - ${res.last.distance}")
		res.asJava
	}
}
