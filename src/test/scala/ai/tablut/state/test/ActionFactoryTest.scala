package ai.tablut.state.test

import ai.tablut.state._
import org.scalatest.WordSpec

class ActionFactoryTest extends WordSpec{
	"ActionFactory" when {
		"using normal game rules" should {
			val factory = StateFacade.normalStateFactory()

			"list all initial actions for WHITE" in {
				val initState = factory.createInitialState()
				val actionsFactory = new ActionFactory(initState, factory.context)
				val b = initState.board
				val p = initState.turn
				val expected = Set(
					Action(p, b(2)(4), b(2)(0)), Action(p, b(2)(4), b(2)(1)), Action(p, b(2)(4), b(2)(2)), Action(p, b(2)(4), b(2)(3)),
					Action(p, b(2)(4), b(2)(5)), Action(p, b(2)(4), b(2)(6)), Action(p, b(2)(4), b(2)(7)), Action(p, b(2)(4), b(2)(8)),

					Action(p, b(3)(4), b(3)(1)), Action(p, b(3)(4), b(3)(2)), Action(p, b(3)(4), b(3)(3)),
					Action(p, b(3)(4), b(3)(5)), Action(p, b(3)(4), b(3)(6)), Action(p, b(3)(4), b(3)(7)),

					Action(p, b(5)(4), b(5)(1)), Action(p, b(5)(4), b(5)(2)), Action(p, b(5)(4), b(5)(3)),
					Action(p, b(5)(4), b(5)(5)), Action(p, b(5)(4), b(5)(6)), Action(p, b(5)(4), b(5)(7)),

					Action(p, b(6)(4), b(6)(0)), Action(p, b(6)(4), b(6)(1)), Action(p, b(6)(4), b(6)(2)), Action(p, b(6)(4), b(6)(3)),
					Action(p, b(6)(4), b(6)(5)), Action(p, b(6)(4), b(6)(6)), Action(p, b(6)(4), b(6)(7)), Action(p, b(6)(4), b(6)(8)),


					Action(p, b(4)(2), b(0)(2)), Action(p, b(4)(2), b(1)(2)), Action(p, b(4)(2), b(2)(2)), Action(p, b(4)(2), b(3)(2)),
					Action(p, b(4)(2), b(5)(2)), Action(p, b(4)(2), b(6)(2)), Action(p, b(4)(2), b(7)(2)), Action(p, b(4)(2), b(8)(2)),

					Action(p, b(4)(3), b(1)(3)), Action(p, b(4)(3), b(2)(3)), Action(p, b(4)(3), b(3)(3)),
					Action(p, b(4)(3), b(5)(3)), Action(p, b(4)(3), b(6)(3)), Action(p, b(4)(3), b(7)(3)),

					Action(p, b(4)(5), b(1)(5)), Action(p, b(4)(5), b(2)(5)), Action(p, b(4)(5), b(3)(5)),
					Action(p, b(4)(5), b(5)(5)), Action(p, b(4)(5), b(6)(5)), Action(p, b(4)(5), b(7)(5)),

					Action(p, b(4)(6), b(0)(6)), Action(p, b(4)(6), b(1)(6)), Action(p, b(4)(6), b(2)(6)), Action(p, b(4)(6), b(3)(6)),
					Action(p, b(4)(6), b(5)(6)), Action(p, b(4)(6), b(6)(6)), Action(p, b(4)(6), b(7)(6)), Action(p, b(4)(6), b(8)(6)),
				)
				val actions = actionsFactory.createActions
				assert(actions.toSet == expected)
			}

			"list actions for KING" in{
				val initState = factory.createInitialState()
				val state = initState.transform(Map(
					(4,3) -> CellContent.EMPTY
				))

				val actions = new ActionFactory(state, factory.context).createActions
				assert(actions.exists(a => a.from.cellContent == CellContent.KING))
			}
		}
	}
}
