package ai.tablut.adversarial

import ai.tablut.adversarial.heuristic.Phase._
import ai.tablut.state.Player.Player
import ai.tablut.state.State

class PhaseFactory(val midPhaseTurn: Int, val endPhaseTurn: Int) {
	def createPhase(state: State, player: Player, nTurn: Int): Phase = {
		if (nTurn > endPhaseTurn)
			return END

		if (state.findKing.exists(c => c.coords == (4,4)))
			START
		else
			MID
	}
}
