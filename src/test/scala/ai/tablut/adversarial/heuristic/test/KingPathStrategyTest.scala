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

			"evaluate strategy" in{
				val twoSteps = initState.transform(Map(
					(4,2) -> CellContent.EMPTY,
					(4,3) -> CellContent.EMPTY
				))

				assert(strategy.eval(twoSteps, Player.WHITE) == strategy.minValue + 1)
				assert(strategy.eval(twoSteps, Player.WHITE) == strategy.maxValue - 1)
			}
		}
	}
}
