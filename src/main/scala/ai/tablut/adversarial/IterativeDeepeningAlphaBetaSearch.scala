package ai.tablut.adversarial

import aima.core.search.adversarial.AdversarialSearch
import aima.core.search.framework.Metrics


/**
  * Implements an iterative deepening Minimax search with alpha-beta pruning and
  * action ordering. Maximal computation time is specified in seconds. The
  * algorithm is implemented as template method and can be configured and tuned
  * by subclassing.
  *
  * @param < S> Type which is used for states in the game.
  * @param < A> Type which is used for actions in the game.
  * @param < P> Type which is used for players in the game.
  * @author Ruediger Lunde
  */
object IterativeDeepeningAlphaBetaSearch {
	val METRICS_NODES_EXPANDED = "nodesExpanded"
	val METRICS_MAX_DEPTH = "maxDepth"

	/**
	  * Creates a new search object for a given game.
	  *
	  * @param game    The game.
	  * @param utilMin Utility value of worst state for this player. Supports
	  *                evaluation of non-terminal states and early termination in
	  *                situations with a safe winner.
	  * @param utilMax Utility value of best state for this player. Supports
	  *                evaluation of non-terminal states and early termination in
	  *                situations with a safe winner.
	  * @param time    Maximal computation time in seconds.
	  */
	def createFor[STATE, ACTION, PLAYER](game: Game[STATE, ACTION, PLAYER], utilMin: Double, utilMax: Double, time: Int) = new IterativeDeepeningAlphaBetaSearch[STATE, ACTION, PLAYER](game, utilMin, utilMax, time)

	private class Timer private[adversarial](val maxSeconds: Int) {
		private var duration = 1000 * maxSeconds
		private var startTime = 0L

		private[adversarial] def start(): Unit = {
			startTime = System.currentTimeMillis
		}

		private[adversarial] def timeOutOccurred = System.currentTimeMillis > startTime + duration
	}

	/**
	  * Orders actions by utility.
	  */
	private class OrderedActions[A] {
		private var data = List[(A, Double)]()

		private def insert[T](list: List[T], i: Int, value: T): List[T] = list match {
			case head :: tail if i > 0 => head :: insert(tail, i-1, value)
			case _ => value :: list
		}

		def add(action: A, utilValue: Double): Unit = {
			var idx = 0
			while (idx < data.size && utilValue <= data(idx)._2)
				idx += 1

			data = insert(data, idx, (action, utilValue))
		}

		def size: Int = data.size

		lazy val actions: List[A] = data.map(e => e._1)
		lazy val utilValues: List[Double] = data.map(e => e._2)
	}

}

class IterativeDeepeningAlphaBetaSearch[S, A, P](var game: Game[S, A, P], var utilMin: Double, var utilMax: Double, val time: Int)
	extends AdversarialSearch[S, A] {

	protected var currDepthLimit = 0
	private var heuristicEvaluationUsed = false // indicates that non-terminal
	protected var onDepthUpdate: Int => Unit = {_ => }

	// nodes
	// have been evaluated.
	private val timer = new IterativeDeepeningAlphaBetaSearch.Timer(time)
	private var logEnabled = false
	private var metrics = new Metrics

	def setLogEnabled(b: Boolean): Unit = {
		logEnabled = b
	}

	/**
	  * Template method controlling the search. It is based on iterative
	  * deepening and tries to make to a good decision in limited time. Credit
	  * goes to Behi Monsio who had the idea of ordering actions by utility in
	  * subsequent depth-limited search runs.
	  */
	override def makeDecision(state: S): A = {
		metrics = new Metrics

		val player = game.getPlayer(state)
		var orderedActions = orderActions(state, game.getActions(state), player, 0)

		timer.start()
		currDepthLimit = 0
		do {
			currDepthLimit += 1
			onDepthUpdate(currDepthLimit)

			heuristicEvaluationUsed = false

			val newResults = new IterativeDeepeningAlphaBetaSearch.OrderedActions[A]

			orderedActions.toParArray.map{action =>
				val value = minValue(game.getResult(state, action), player, Double.NegativeInfinity, Double.PositiveInfinity, 1)
				if (timer.timeOutOccurred)
					return orderedActions.head
				(action, value)
			}.seq.foreach(t => newResults.add(t._1, t._2))

			if (newResults.size > 0) {
				orderedActions = newResults.actions

				if (!timer.timeOutOccurred)
					if (hasSafeWinner(newResults.utilValues.head))
						return orderedActions.head
					else
					if (newResults.size > 1 && isSignificantlyBetter(newResults.utilValues.head, newResults.utilValues(1)))
						return orderedActions.head
			}

		}while (!timer.timeOutOccurred && heuristicEvaluationUsed)

		orderedActions.head
	}

	// returns an utility value
	def maxValue(state: S, player: P, inAlpha: Double, beta: Double, depth: Int): Double = {
		updateMetrics(depth)
		if (game.isTerminal(state) || depth >= currDepthLimit || timer.timeOutOccurred)
			eval(state, player)
		else {
			var value = Double.NegativeInfinity
			var alpha = inAlpha
			for (action <- orderActions(state, game.getActions(state), player, depth)) {
				value = Math.max(value, minValue(game.getResult(state, action), player, alpha, beta, depth + 1))
				if (value >= beta) return value
				alpha = Math.max(alpha, value)
			}
			value
		}
	}

	def minValue(state: S, player: P, alpha: Double, inBeta: Double, depth: Int): Double = {
		updateMetrics(depth)
		if (game.isTerminal(state) || depth >= currDepthLimit || timer.timeOutOccurred)
			eval(state, player)
		else {
			var value = Double.PositiveInfinity
			var beta = inBeta
			for (action <- orderActions(state, game.getActions(state), player, depth)) {
				value = Math.min(value, maxValue(game.getResult(state, action), player, alpha, beta, depth + 1))
				if (value <= alpha) return value
				beta = Math.min(beta, value)
			}
			value
		}
	}

	private def updateMetrics(depth: Int): Unit = {
		metrics.incrementInt(IterativeDeepeningAlphaBetaSearch.METRICS_NODES_EXPANDED)
		metrics.set(IterativeDeepeningAlphaBetaSearch.METRICS_MAX_DEPTH, Math.max(metrics.getInt(IterativeDeepeningAlphaBetaSearch.METRICS_MAX_DEPTH), depth))
	}

	/**
	  * Returns some statistic data from the last search.
	  */
	override def getMetrics: Metrics = metrics


	/**
	  * Primitive operation which is used to stop iterative deepening search in
	  * situations where a clear best action exists. This implementation returns
	  * always false.
	  */
	protected def isSignificantlyBetter(newUtility: Double, utility: Double): Boolean = false

	/**
	  * Primitive operation which is used to stop iterative deepening search in
	  * situations where a safe winner has been identified. This implementation
	  * returns true if the given value (for the currently preferred action
	  * result) is the highest or lowest utility value possible.
	  */
	protected def hasSafeWinner(resultUtility: Double): Boolean = resultUtility <= utilMin || resultUtility >= utilMax

	/**
	  * Primitive operation, which estimates the value for (not necessarily
	  * terminal) states. This implementation returns the utility value for
	  * terminal states and <code>(utilMin + utilMax) / 2</code> for non-terminal
	  * states. When overriding, first call the super implementation!
	  */
	protected def eval(state: S, player: P): Double = if (game.isTerminal(state)) {
			game.getUtility(state, player)
		}else {
			heuristicEvaluationUsed = true
			(utilMin + utilMax) / 2
		}


	/**
	  * Primitive operation for action ordering. This implementation preserves
	  * the original order (provided by the game).
	  */
	protected def orderActions(state: S, actions: Seq[A], player: P, depth: Int): Seq[A] = actions
}