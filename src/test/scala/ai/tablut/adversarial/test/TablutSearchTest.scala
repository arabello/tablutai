package ai.tablut.adversarial.test

import ai.tablut.adversarial.{TablutGame, TablutSearch}
import ai.tablut.state._
import org.scalatest.WordSpec

class TablutSearchTest extends WordSpec{
	"TablutSearch" when {
		"using normal game rules" should{
			val factory = StateFacade.normalStateFactory()
			val initState = factory.createInitialState()
			val game = new TablutGame(factory, initState)
			val search = new TablutSearch(factory.context, game, 1)

			"find action to win WHITE" in{
				val state = initState.transform(Map(
					(4,4) -> CellContent.EMPTY,
					(4,2) -> CellContent.KING
				))

				val action = search.makeDecision(state)
				assert(action == Action(Player.WHITE, state(4)(2).get, state(0)(2).get))

				val terminal = state.applyAction(action)
				assert(game.isTerminal(terminal))
				assert(search.eval(terminal, Player.WHITE) == search.utilMax)
				assert(search.eval(terminal, Player.BLACK) == search.utilMin)
			}

			"find action to win BLACK" in{
				val state = initState.transform(Map(
					(4,4) -> CellContent.EMPTY,
					(4,3) -> CellContent.EMPTY,
					(4,2) -> CellContent.KING
				)).nextPlayer

				val action = search.makeDecision(state)
				assert(action.to.coords == (4,3))

				val terminal = state.applyAction(action)
				assert(game.isTerminal(terminal))
				assert(search.eval(terminal, Player.WHITE) == search.utilMin)
				assert(search.eval(terminal, Player.BLACK) == search.utilMax)
			}

			"find action to counter attack WHITE win" in{
				val state = initState.transform(Map(
					(4,4) -> CellContent.EMPTY,
					(3,2) -> CellContent.WHITE,
					(4,2) -> CellContent.KING
				)).nextPlayer

				val action = search.makeDecision(state)
				assert(action.to.coords == (8,2) || action.to.coords == (5,2) )

				val terminal = state.applyAction(action)
				assert(!game.isTerminal(terminal))
			}

			"eval WHITE winner" in {
				val state = initState.transform(Map(
					(4,4) -> CellContent.EMPTY,
					(8,2) -> CellContent.KING
				))

				assert(search.eval(state, Player.WHITE) == search.utilMax)
				assert(search.eval(state, Player.BLACK) == search.utilMin)
			}

			"WHITE winner special case" in {
				val state2 = initState.transform(Map(
					(4,4) -> CellContent.EMPTY,
					(6,1) -> CellContent.KING,
					(5,0) -> CellContent.EMPTY,
					(6,2) -> CellContent.WHITE,
					(8,1) -> CellContent.BLACK,
					(4,1) -> CellContent.EMPTY,
					(3,1) -> CellContent.BLACK
				)).nextPlayer

				val nextAction = search.makeDecision(state2)
				assert(nextAction == Action(Player.BLACK, state2(4)(0).get, state2(6)(0).get))
			}

			"eval BLACK winner" in {
				val state = initState.transform(Map(
					(4,4) -> CellContent.EMPTY,
					(4,2) -> CellContent.KING,
					(3,2) -> CellContent.BLACK
				)).nextPlayer

				val eatKing = state.applyAction(Action(Player.BLACK, state(5,0).get, state(5,2).get))
				assert(search.eval(eatKing, Player.WHITE) == search.utilMin)
				assert(search.eval(eatKing, Player.BLACK) == search.utilMax)
			}

			"sort action for WHITE player" in{
				val state = initState.transform(Map(
					(4,3) -> CellContent.EMPTY,
					(4,2) -> CellContent.EMPTY
				))

				val actions = new ActionFactory(state, factory.context).createActions
				assert(actions.exists(a => a.from.cellContent == CellContent.KING))

				val sorted = search.orderActions(state, actions, Player.WHITE, 1)
				assert(sorted.head.from.cellContent == CellContent.KING)
				assert(state.distance(sorted(1).from.coords, sorted(1).to.coords) < state.distance(sorted(0).from.coords, sorted(0).to.coords))
			}
		}
	}
}
