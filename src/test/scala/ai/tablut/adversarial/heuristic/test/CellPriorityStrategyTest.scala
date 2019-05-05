package ai.tablut.adversarial.heuristic.test

import ai.tablut.adversarial.heuristic.CellPriorityStrategy
import ai.tablut.state.{CellContent, Player, StateFacade}
import org.scalatest.WordSpec

class CellPriorityStrategyTest extends WordSpec{
	"CellPriorityStrategyTest" when {
		"using normal game rules" should{
			val factory = StateFacade.normalStateFactory()
			val initState = factory.createInitialState()
			val strategy = new CellPriorityStrategy(factory.context)

			"evaluate strategy" in{
				assert(strategy.eval(initState, Player.WHITE) == strategy.minValue)
				assert(strategy.eval(initState, Player.BLACK) == strategy.maxValue)

				val campAngles = initState.transform(Map(
					(1,3) -> CellContent.WHITE,
					(1,5) -> CellContent.WHITE,
					(3,1) -> CellContent.WHITE,
					(3,7) -> CellContent.WHITE
				))

				assert(strategy.eval(campAngles, Player.WHITE) == strategy.minValue + 4)
				assert(strategy.eval(campAngles, Player.BLACK) == strategy.maxValue - 4)

				val hotspot = initState.transform(Map(
					(2,2) -> CellContent.KING
				))

				assert(strategy.eval(hotspot, Player.WHITE) == strategy.minValue + 8)
				assert(strategy.eval(hotspot, Player.BLACK) == strategy.maxValue - 8)

				val nearCastle = initState.transform(Map(
					(4,3) -> CellContent.KING
				))

				assert(strategy.eval(nearCastle, Player.WHITE) == strategy.minValue + 4)
				assert(strategy.eval(nearCastle, Player.BLACK) == strategy.maxValue - 4)
			}
		}
	}
}
