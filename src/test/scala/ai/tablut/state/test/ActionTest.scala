package ai.tablut.state.test

import ai.tablut.state.{Action, Turn, StateFacade}
import org.scalatest.WordSpec

class ActionTest extends WordSpec{
	"Action" when {
		"using normal game rules" should {
			val factory = StateFacade.normalStateFactory()
			val rules = factory.context

			val board =  factory.createInitialState.board

			"be game rules complied" in {
				assert(!Action(Turn.WHITE, board(2)(4), board(3)(2)).isGameRulesComplied(rules))
				assert(!Action(Turn.WHITE, board(2)(4), board(3)(2)).isGameRulesComplied(rules))
			}

			"a legal move" in{
				assert(!Action(Turn.WHITE, board(2)(4), board(6)(4)).isLegal(board))
				assert(!Action(Turn.WHITE, board(3)(4), board(4)(4)).isLegal(board))
			}
		}
	}
}
