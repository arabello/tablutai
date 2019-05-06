package ai.tablut.state.test

import ai.tablut.state.{Action, Player, StateFacade}
import org.scalatest.WordSpec

class ActionTest extends WordSpec{
	"Action" when {
		"using normal game rules" should {
			val factory = StateFacade.normalStateFactory()
			val rules = factory.context

			val state = factory.createInitialState()

			"game rules complied" in {
				assert(!Action(Player.WHITE, state(2)(4).get, state(3)(2).get).isGameRulesComplied(rules))
				assert(!Action(Player.WHITE, state(2)(4).get, state(3)(2).get).isGameRulesComplied(rules))
			}

			"legal move" in{
				assert(!Action(Player.WHITE, state(2)(4).get, state(6)(4).get).isLegal(state))
				assert(!Action(Player.WHITE, state(3)(4).get, state(4)(4).get).isLegal(state))
				assert(!Action(Player.WHITE, state(4)(6).get, state(4)(7).get).isLegal(state))
			}
		}
	}
}
