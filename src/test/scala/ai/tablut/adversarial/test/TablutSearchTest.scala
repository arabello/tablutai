package ai.tablut.adversarial.test

import ai.tablut.adversarial.{TablutGame, TablutSearch}
import ai.tablut.state.{ActionFactory, CellContent, Player, StateFacade}
import org.scalatest.WordSpec

class TablutSearchTest extends WordSpec{
	"TablutSearch" when {
		"using normal game rules" should{
			val factory = StateFacade.normalStateFactory()
			val initState = factory.createInitialState()
			val game = new TablutGame(factory, initState)
			val search = new TablutSearch(factory.context, game, 1)

			"eval WHITE winner" in {
				val state = initState.transform(Map(
					(4,4) -> CellContent.EMPTY,
					(8,2) -> CellContent.KING
				))

				assert(search.eval(state, Player.WHITE) == search.utilMax)
				assert(search.eval(state, Player.BLACK) == search.utilMin)
			}

			"eval BLACK winner" in {
				val state = initState.transform(Map(
					(4,4) -> CellContent.EMPTY,
					(4,2) -> CellContent.KING,
					(3,2) -> CellContent.BLACK,
					(5,2) -> CellContent.BLACK
				)).nextPlayer

				assert(search.eval(state, Player.WHITE) == search.utilMin)
				assert(search.eval(state, Player.BLACK) == search.utilMax)

				val state2 = initState.transform(Map(
					(4,4) -> CellContent.EMPTY,
					(4,2) -> CellContent.KING,
					(4,3) -> CellContent.BLACK,
				)).nextPlayer

				assert(search.eval(state2, Player.WHITE) == search.utilMin)
				assert(search.eval(state2, Player.BLACK) == search.utilMax)
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
