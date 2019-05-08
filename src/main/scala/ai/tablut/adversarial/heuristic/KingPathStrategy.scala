package ai.tablut.adversarial.heuristic
import java.util

import ai.tablut.state.Player.Player
import ai.tablut.state._
import aima.core.agent
import aima.core.agent.impl.DynamicAction
import aima.core.search.framework.problem.{ActionsFunction, GoalTest, Problem, ResultFunction}
import aima.core.search.uninformed.{DepthLimitedSearch, IterativeDeepeningSearch}

import scala.collection.JavaConverters._

class KingPathStrategy(gameContext: GameContext) extends HeuristicStrategy {
	override val minValue: Int = 0
	override val maxValue: Int = 1

	private val search = new DepthLimitedSearch(2)

	private case class KingAction(action: Action) extends DynamicAction(action.toString){
		override def toString: String = getName
	}

	private class KingActionsFunction extends ActionsFunction{
		override def actions(s: Any): util.Set[agent.Action] = {
			val state = s.asInstanceOf[State]
			val findKing = state.findKing
			if (findKing.isEmpty)
				return Set[agent.Action]().asJava

			val king = findKing.get
			val actionFactory = new ActionFactory(state, gameContext)
			val actions = actionFactory.createKingActions
			val set = new util.HashSet[agent.Action]()
			actions.foreach(a => set.add(KingAction(a)))
			set
		}
	}

	private class KingResultFunction extends ResultFunction{
		override def result(s: Any, a: agent.Action): AnyRef = {
			val state = s.asInstanceOf[State]
			val action = a.asInstanceOf[KingAction]
			state.applyAction(action.action).nextPlayer
		}
	}

	private class KingGoalTest extends GoalTest{
		override def isGoalState(s: Any): Boolean = {
			val state = s.asInstanceOf[State]
			val findKing = state.findKing
			if (findKing.isEmpty)
				return false
			val king = findKing.get
			gameContext.escapePoints.contains(king.coords)
		}
	}

	override def eval(state: State, player: Player): Int = {
		val (min, max) = if (player == Player.WHITE) (minValue, maxValue) else (maxValue, minValue)

		val findKing = state.findKing
		if (findKing.isEmpty)
			return min

		val problem = new Problem(state, new KingActionsFunction(), new KingResultFunction(), new KingGoalTest())
		val actionsToGoals = search.findActions(problem)

		if (actionsToGoals.isEmpty)
			min
		else
			max
	}
}
