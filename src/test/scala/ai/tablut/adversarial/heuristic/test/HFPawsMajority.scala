package ai.tablut.adversarial.heuristic.test

import ai.tablut.adversarial.heuristic.NormalGameHeuristicFactory
import ai.tablut.state.Player.{BLACK, WHITE}
import ai.tablut.state.StateFacade
import org.scalatest.WordSpec

class HFPawsMajority extends WordSpec{
	"HFPawsMajority" when {
		"using normal game rules" should {
			val factory = StateFacade.normalStateFactory()
			val heuristic = NormalGameHeuristicFactory.createBlockEscapePoints()
			val initState = factory.createInitialState()

			"paws majority" in{
				val heuristic = NormalGameHeuristicFactory.createPawsMajority()

				assert(heuristic.eval(initState, WHITE) < 0.5)

				val minus3Whites = initState.clearCells((2,4),(3,4),(5,4))
				assert(heuristic.eval(minus3Whites, BLACK) > 0.6)
			}
		}
	}
}
