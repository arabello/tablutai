package ai.tablut.adversarial.heuristic.test

import ai.tablut.adversarial.heuristic.NormalGameHeuristicFactory
import ai.tablut.state.StateFacade
import ai.tablut.state.Turn.{BLACK, DRAW, WHITE}
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

				val minus3Whites = initState.copy(board = initState.board.clearCells((2,4),(3,4),(5,4)))
				assert(heuristic.eval(minus3Whites, BLACK) > 0.6)

				val minus7Blacks = initState.copy(board = initState.board.clearCells((0,3),(0,4),(0,5),(1,4),(8,3),(8,4),(8,5)))
				assert(heuristic.eval(minus7Blacks, DRAW) == 0.5)
			}
		}
	}
}
