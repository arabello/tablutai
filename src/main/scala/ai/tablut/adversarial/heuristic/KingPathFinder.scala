package ai.tablut.adversarial.heuristic
import java.util

import ai.tablut.state.Player.Player
import ai.tablut.state.{Action, ActionFactory, GameContext, State}
import aima.core.agent
import aima.core.agent.impl.DynamicAction
import aima.core.search.uninformed.DepthLimitedSearch
import aima.core.search.framework.problem.{ActionsFunction, GoalTest, Problem, ResultFunction}

import scala.collection.JavaConverters._

class KingPathFinder(gameContext: GameContext) extends HeuristicStrategy {
	override val minValue: Int = 0
	override val maxValue: Int = 4

	private val search = new DepthLimitedSearch[State, Action](2)

	private case class KingAction(action: Action) extends DynamicAction(s"${action.from.coords} ${action.to.coords}")

	private class KingActionsFunction extends ActionsFunction{
		override def actions(s: Any): util.Set[KingAction] = {
			val state = s.asInstanceOf[State]
			val findKing = state.findKing
			if (findKing.isEmpty)
				return Set[KingAction]().asJava

			val king = findKing.get
			val actionFactory = new ActionFactory(state, gameContext)

			actionFactory.actions(Seq(king)).map(a => KingAction(a)).toSet.asJava
		}
	}

	private class KingResultFunction extends ResultFunction{
		override def result(s: Any, a: agent.Action): AnyRef = {
			val state = s.asInstanceOf[State]
			val action = a.asInstanceOf[KingAction]
			state.applyAction(action.action)
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
		val findKing = state.findKing
		if (findKing.isEmpty)
			return minValue

		val problem = new Problem(state, new KingActionsFunction(), new KingResultFunction(), new KingGoalTest())

		val actionsToGoals = search.findActions(problem).asScala

		actionsToGoals.foldLeft[Int](0)((acc, a) => acc + 1)
	}
}
