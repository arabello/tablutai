package ai.tablut.adversarial

import java.util.Properties

import ai.tablut.adversarial.heuristic.Phase._
import ai.tablut.state.Player.Player
import ai.tablut.state.{Player, State}

class PhaseFactory(props: Properties) {
	private val midPhaseTurn = props.getProperty("MID_PHASE_TURN", "3").toInt
	private val endPhaseTurn = props.getProperty("END_PHASE_TURN", "10").toInt

	def createPhase(state: State, player: Player, nTurn: Int): Phase = player match {
		case Player.WHITE =>
			if (state.findKing.exists(c => c.coords != (4,4)))
				MID
			else if (nTurn > endPhaseTurn)
				END
			else START
		case _ =>
			if (state.findKing.exists(c => c.coords == (4,4)))
				START
			else if (nTurn > endPhaseTurn)
				END
			else
				MID
	}
}
