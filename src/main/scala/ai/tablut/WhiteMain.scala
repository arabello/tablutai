package ai.tablut

import ai.tablut.connectivity.ConnFactory
import ai.tablut.serialization.TablutSerializer
import ai.tablut.state.StateFacade

object WhiteMain {
	def main(args: Array[String]): Unit = {
		val whiteClient = ConnFactory.createWhiteClient()

		val worstStateValue = -1.0
		val bestStateValue = 1.0
		val maxComputationSeconds = 50

		val stateFactory = StateFacade.normalStateFactory()

		//val game = new TablutGame(stateFactory)
		//val search = new IterativeDeepeningAlphaBetaSearch(game, worstStateValue, bestStateValue, maxComputationSeconds)
		//val gson = GsonTablut.gson

		whiteClient.writeTeamName()
		val jsonInitState = whiteClient.readState()
		val initState = TablutSerializer.fromJson(jsonInitState, stateFactory)
		println(jsonInitState)
		println(initState)

		while(true) {
			val jsonState = whiteClient.readState()
			val currState = TablutSerializer.fromJson(jsonState, stateFactory)
			// val nextAction = search.makeDecision(currState)
			// val jsonAction = gson.toJson(nextAction, Action::class.java)
			// send jsonAction to server
			// End Loop
		}
	}
}
