package ai.tablut.adversarial

import java.util

import ai.tablut.adversarial.heuristic._
import ai.tablut.state.implicits._
import ai.tablut.state.{Player, _}
import akka.actor.{ActorSystem, Props}
import akka.util.Timeout
import akka.pattern.ask

import scala.collection.JavaConverters._
import scala.concurrent.Await
import scala.concurrent.duration._
import scala.language.postfixOps

class TablutSearch(gameContext: GameContext, game: TablutGame, time: Int) extends IterativeDeepingAlphaBetaSearch(game, 0, 1, time){
	private object Normalizer {
		def createNormalizer(min: Double, max: Double): Double => Double = (value: Double) => {
			val res = (value - min) / (max - min)
			if (res < 0) 0 else if (res > 1) 1 else res
		}
	}

	private implicit val timeout: Timeout = Timeout(1 seconds)
	private val system = ActorSystem("HeuristicActorSystem")

	private val hotspotStrategy = new HotspotStrategy(gameContext)
	private val pawsMajorityStrategy = new PawsMajorityStrategy()

	private val heuristics = Seq(
		HeuristicBox(hotspotStrategy, system.actorOf(HeuristicActorImpl.props(hotspotStrategy)), 6),
		HeuristicBox(pawsMajorityStrategy, system.actorOf(HeuristicActorImpl.props(pawsMajorityStrategy)), 4)
	)

	private val totWeight = heuristics.foldLeft[Int](0)((acc, hb) => acc + hb.weight)

	private val normalizer = Normalizer.createNormalizer(
		heuristics.foldLeft[Int](0)((acc, hb) => acc + hb.strategy.minValue),
		heuristics.foldLeft[Int](0)((acc, hb) => acc + hb.strategy.maxValue)
	)

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

		// Send msg and map relative weights
		val futuresAndWeights = heuristics.map( hb => (hb.actor ? Message(state, player), hb.weight))

		// Wait results and sum weighting them
		val num = futuresAndWeights.foldLeft[Int](0)((acc, futureAndWeight) =>
			(Await.result(futureAndWeight._1, timeout.duration).asInstanceOf[Int] * futureAndWeight._2) + acc)

		// Heuristic strategies average
		val avg = num.toDouble / totWeight

		// Normalization to 0 - 1 range
		val hValue = normalizer(avg)
		getMetrics.set("hfValue", hValue)
		hValue
	}

	// RUNTIME
	override def orderActions(state: State, actions: util.List[Action], player: Player.Value, depth: Int): List[Action] = player match {
		case Player.WHITE => actions.asScala.sortWith((a1, a2) => a2.who == CellContent.KING).toList
		case Player.BLACK =>
			val king = state.findKing
			if (king.isEmpty)
				return actions.asScala.toList

			val kingSurrounding = king.get.surroundingAt(1)(state).filter(c => c.isDefined && c.get.cellContent == CellContent.EMPTY).map(c => c.get)
			actions.asScala.sortWith((a1, a2) => kingSurrounding.contains(a2.to)).toList
	}

}
