package ai.tablut

import ai.tablut.state.StateFacade
import ai.tablut.adversarial.TablutGame
import aima.core.search.adversarial.IterativeDeepeningAlphaBetaSearch

object Main {
	def main(args: Array[String]): Unit = {
		val worstStateValue = -1.0
		val bestStateValue = 1.0
		val maxComputationSeconds = 50

		val stateFactory = StateFacade.normalStateFactory()

		val game = new TablutGame(stateFactory)
		val search = new IterativeDeepeningAlphaBetaSearch(game, worstStateValue, bestStateValue, maxComputationSeconds)
		//val gson = GsonTablut.gson

		// Loop
		// val jsonState = receive json from server
		// val currState = gson.fromJson(jsonState, Board::class.java)
		// val nextAction = search.makeDecision(currState)
		// val jsonAction = gson.toJson(nextAction, Action::class.java)
		// send jsonAction to server
		// End Loop
	}
}
