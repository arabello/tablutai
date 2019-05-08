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

			"evaluation BLACK" in{
				val innerCircle = initState.transform(strategy.innerCircle.map(c => c -> CellContent.BLACK).toMap).nextPlayer
				val outerCircle = initState.transform(strategy.outerCircle.map(c => c -> CellContent.BLACK).toMap).nextPlayer

				assert(strategy.eval(innerCircle, Player.BLACK) > strategy.eval(initState, Player.BLACK))
				assert(strategy.eval(outerCircle, Player.BLACK) > strategy.eval(initState, Player.BLACK))
			}

			"evaluation WHITE" in{
				val hotpost = initState.transform(Map(strategy.hotspot.head -> CellContent.KING))
				val escapePointFilled = initState.transform(factory.context.escapePoints.map(c => c -> CellContent.BLACK).toMap).nextPlayer

				assert(strategy.eval(hotpost, Player.WHITE) > strategy.eval(escapePointFilled, Player.WHITE))
				assert(strategy.eval(hotpost, Player.WHITE) > strategy.eval(initState, Player.WHITE))

				val boardCorners = initState.transform(Map((0,0) -> CellContent.WHITE))
				assert(strategy.eval(boardCorners, Player.WHITE) < strategy.eval(initState, Player.WHITE))
			}
		}
	}
}
