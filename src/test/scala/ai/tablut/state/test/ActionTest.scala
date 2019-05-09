package ai.tablut.state.test

import ai.tablut.state.{Action, CellContent, Player, StateFacade}
import org.scalatest.WordSpec

class ActionTest extends WordSpec{
	"Action" when {
		"using normal game rules" should {
			val factory = StateFacade.normalStateFactory()
			val rules = factory.context

			val initState = factory.createInitialState()

			"game rules complied" in {
				assert(!Action(Player.WHITE, initState(2)(4).get, initState(3)(2).get).isGameRulesComplied(rules))
				assert(!Action(Player.WHITE, initState(2)(4).get, initState(3)(2).get).isGameRulesComplied(rules))

				val state = initState.transform(Map(
					(4,7) -> CellContent.EMPTY
				))

				assert(!Action(Player.WHITE, state(4)(6).get, state(4)(7).get).isGameRulesComplied(factory.context))
			}

			"legal move" in{
				assert(!Action(Player.WHITE, initState(2)(4).get, initState(6)(4).get).isLegal(initState))
				assert(!Action(Player.WHITE, initState(3)(4).get, initState(4)(4).get).isLegal(initState))
				assert(!Action(Player.WHITE, initState(4)(6).get, initState(4)(7).get).isLegal(initState))

				val state = initState.transform(Map(
					(4,7) -> CellContent.EMPTY
				))

				assert(!Action(Player.WHITE, state(4)(6).get, state(4)(7).get).isLegal(state))
			}
		}
	}
}
