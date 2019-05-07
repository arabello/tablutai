package ai.tablut.adversarial.heuristic.test

import ai.tablut.adversarial.heuristic.KingPathStrategy
import ai.tablut.state.{CellContent, Player, StateFacade}
import org.scalatest.WordSpec

class KingPathStrategyTest extends WordSpec{
	"KingPathFinderStrategy" when {
		"using normal game rules" should{
			val factory = StateFacade.normalStateFactory()
			val initState = factory.createInitialState()
			val strategy = new KingPathStrategy(factory.context)

			"see two steps forward" in{
				val twoSteps = initState.transform(Map(
					(4,2) -> CellContent.EMPTY,
					(4,3) -> CellContent.EMPTY
				))

				assert(strategy.eval(twoSteps, Player.WHITE) == strategy.maxValue)
				assert(strategy.eval(twoSteps, Player.BLACK) == strategy.minValue)
			}

			"see one and two steps forward" in{
				val oneAndTwoSteps = initState.transform(Map(
					(2,4) -> CellContent.EMPTY,
					(4,2) -> CellContent.EMPTY,
					(4,3) -> CellContent.EMPTY,
					(4,4) -> CellContent.EMPTY,
					(3,3) -> CellContent.KING
				))

				assert(strategy.eval(oneAndTwoSteps, Player.WHITE) == strategy.maxValue)
				assert(strategy.eval(oneAndTwoSteps, Player.BLACK) == strategy.minValue)
			}
		}
	}
}
