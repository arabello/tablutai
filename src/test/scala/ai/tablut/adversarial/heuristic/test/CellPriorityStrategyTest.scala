package ai.tablut.adversarial.heuristic.test

import ai.tablut.adversarial.heuristic.CellPriorityStrategy
import ai.tablut.state.{CellContent, Player, StateFacade}
import org.scalatest.WordSpec

class CellPriorityStrategyTest extends WordSpec{
	"CellPriorityStrategy" when {
		"using normal game rules" should{
			val factory = StateFacade.normalStateFactory()
			val initState = factory.createInitialState()
			val strategy = new CellPriorityStrategy(factory.context)

			"evaluate strategy" in{
				assert(strategy.eval(initState, Player.WHITE) == strategy.minValue + 8)
				assert(strategy.eval(initState, Player.BLACK) == strategy.maxValue - 16)

				val campAngles = initState.transform(Map(
					(1,2) -> CellContent.WHITE,
					(1,6) -> CellContent.WHITE,
					(2,1) -> CellContent.WHITE,
					(2,7) -> CellContent.WHITE
				))

				assert(strategy.eval(campAngles, Player.WHITE) == strategy.minValue + 4)
				assert(strategy.eval(campAngles, Player.BLACK) == strategy.maxValue - 12)

				val hotspot = initState.transform(Map(
					(4,4) -> CellContent.EMPTY,
					(2,2) -> CellContent.KING
				))

				assert(strategy.eval(hotspot, Player.WHITE) == strategy.minValue + 16)
				assert(strategy.eval(hotspot, Player.BLACK) == strategy.minValue)

				val nearCastle = initState.transform(Map(
					(4,3) -> CellContent.KING
				))

				assert(strategy.eval(nearCastle, Player.WHITE) == strategy.minValue + 12)
				assert(strategy.eval(nearCastle, Player.BLACK) == strategy.maxValue - 16)
			}
		}
	}
}
