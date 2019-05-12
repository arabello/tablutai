package ai.tablut.adversarial

import ai.tablut.Config
import ai.tablut.adversarial.heuristic.Phase._
import ai.tablut.state.Player.Player
import ai.tablut.state.State

class PhaseFactory() {
	private val midPhaseTurn = Config.MID_PHASE_TURN
	private val endPhaseTurn = Config.END_PHASE_TURN

	def createPhase(state: State, player: Player, nTurn: Int): Phase = {
		if (nTurn > endPhaseTurn)
			return END

		if (state.findKing.exists(c => c.coords == (4,4)))
			START
		else
			MID
	}
}
