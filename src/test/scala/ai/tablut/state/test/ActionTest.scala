package ai.tablut.state.test

import ai.tablut.state.{Action, Player, StateFacade}
import org.scalatest.WordSpec

class ActionTest extends WordSpec{
	"Action" when {
		"using normal game rules" should {
			val factory = StateFacade.normalStateFactory()
			val rules = factory.context

			val state = factory.createInitialState()

			"be game rules complied" in {
				assert(!Action(Player.WHITE, state(2)(4), state(3)(2)).isGameRulesComplied(rules))
				assert(!Action(Player.WHITE, state(2)(4), state(3)(2)).isGameRulesComplied(rules))
			}

			"a legal move" in{
				assert(!Action(Player.WHITE, state(2)(4), state(6)(4)).isLegal(state))
				assert(!Action(Player.WHITE, state(3)(4), state(4)(4)).isLegal(state))
			}
		}
	}
}
