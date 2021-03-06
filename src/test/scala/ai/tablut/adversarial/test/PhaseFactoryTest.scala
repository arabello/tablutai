package ai.tablut.adversarial.test

import ai.tablut.Config
import ai.tablut.adversarial.PhaseFactory
import ai.tablut.adversarial.heuristic.Phase
import ai.tablut.state.{CellContent, Player, StateFacade}
import org.scalatest.WordSpec

class PhaseFactoryTest extends WordSpec{
	"PhaseFactory" when {
		"using normal game rules" should {
			val factory = StateFacade.normalStateFactory()
			val initState = factory.createInitialState()
			val config = Config()
			val phaseFactory = new PhaseFactory(config.midPhaseTurn, config.endPhaseTurn)

			"detect phase" in {
				assert(phaseFactory.createPhase(initState, Player.WHITE, 1) == Phase.START)
				assert(phaseFactory.createPhase(initState, Player.BLACK, 1) == Phase.START)

				val state = initState.transform(Map(
					(4,4) -> CellContent.EMPTY,
					(4,3) -> CellContent.KING
				))

				assert(phaseFactory.createPhase(state, Player.WHITE, 1) == Phase.MID)
				assert(phaseFactory.createPhase(state, Player.WHITE, 11) == Phase.END)
				assert(phaseFactory.createPhase(state, Player.BLACK, 1) == Phase.MID)
				assert(phaseFactory.createPhase(state, Player.BLACK, 11) == Phase.END)
			}
		}
	}
}
