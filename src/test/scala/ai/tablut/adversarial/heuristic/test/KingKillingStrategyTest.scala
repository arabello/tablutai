package ai.tablut.adversarial.heuristic.test

import ai.tablut.adversarial.heuristic.KingKillingStrategy
import ai.tablut.state.{CellContent, Player, StateFacade}
import org.scalatest.WordSpec

class KingKillingStrategyTest extends WordSpec{
	"KingKillingStrategyTest" when {
		"using normal game rules" should{
			val factory = StateFacade.normalStateFactory()
			val initState = factory.createInitialState()
			val strategy = new KingKillingStrategy()

			"evaluate strategy" in{
				assert(strategy.eval(initState, Player.WHITE) == strategy.maxValue)
				assert(strategy.eval(initState, Player.BLACK) == strategy.minValue)

				val one = initState.transform(Map(
					(4,4) -> CellContent.EMPTY,
					(2,6) -> CellContent.KING,
					(2,5) -> CellContent.BLACK
 				))

				assert(strategy.eval(one, Player.WHITE) == strategy.maxValue - 1)
				assert(strategy.eval(one, Player.BLACK) == strategy.minValue + 1)

				val two = one.transform(Map(
					(1,6) -> CellContent.BLACK
				))

				assert(strategy.eval(two, Player.WHITE) == strategy.maxValue - 2)
				assert(strategy.eval(two, Player.BLACK) == strategy.minValue + 2)

				val three = two.transform(Map(
					(2,7) -> CellContent.BLACK
				))

				assert(strategy.eval(three, Player.WHITE) == strategy.maxValue - 5)
				assert(strategy.eval(three, Player.BLACK) == strategy.minValue + 5)

				val four = three.transform(Map(
					(3,6) -> CellContent.BLACK
				))

				assert(strategy.eval(four, Player.WHITE) == strategy.maxValue - 8)
				assert(strategy.eval(four, Player.BLACK) == strategy.minValue + 8)

				val nearCastle = initState.transform(Map(
					(4,4) -> CellContent.EMPTY,
					(4,5) -> CellContent.KING,
					(4,6) -> CellContent.BLACK
				))

				assert(strategy.eval(nearCastle, Player.WHITE) == strategy.maxValue - 5)
				assert(strategy.eval(nearCastle, Player.BLACK) == strategy.minValue + 5)
			}
		}
	}
}
