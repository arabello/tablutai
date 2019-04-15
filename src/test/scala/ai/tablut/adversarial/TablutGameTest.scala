package ai.tablut.adversarial

import ai.tablut.serialization.TablutSerializer
import ai.tablut.state.StateFacade
import aima.core.search.adversarial.IterativeDeepeningAlphaBetaSearch
import org.scalatest.{FlatSpec, WordSpec}

class TablutGameTest extends WordSpec{
	"TablutGame" when {
		"using normal game rules" should {
			val factory = StateFacade.normalStateFactory()
			val jsonInitState =
				"""
				  |{"board":[
				  |	["EMPTY","EMPTY","EMPTY","BLACK","BLACK","BLACK","EMPTY","EMPTY","EMPTY"],
				  |	["EMPTY","EMPTY","EMPTY","EMPTY","BLACK","EMPTY","EMPTY","EMPTY","EMPTY"],
				  |	["EMPTY","EMPTY","EMPTY","EMPTY","WHITE","EMPTY","EMPTY","EMPTY","EMPTY"],
				  |	["BLACK","EMPTY","EMPTY","EMPTY","WHITE","EMPTY","EMPTY","EMPTY","BLACK"],
				  |	["BLACK","BLACK","WHITE","WHITE","KING","WHITE","WHITE","BLACK","BLACK"],
				  |	["BLACK","EMPTY","EMPTY","EMPTY","WHITE","EMPTY","EMPTY","EMPTY","BLACK"],
				  |	["EMPTY","EMPTY","EMPTY","EMPTY","WHITE","EMPTY","EMPTY","EMPTY","EMPTY"],
				  |	["EMPTY","EMPTY","EMPTY","EMPTY","BLACK","EMPTY","EMPTY","EMPTY","EMPTY"],
				  |	["EMPTY","EMPTY","EMPTY","BLACK","BLACK","BLACK","EMPTY","EMPTY","EMPTY"]
				  |	],
				  |	"turn":"WHITE"}""".stripMargin
			val initState = TablutSerializer.fromJson(jsonInitState, factory)

			val worstStateValue = -1.0
			val bestStateValue = 1.0
			val maxComputationSeconds = 50

			val game = new TablutGame(factory, initState)
			val search = new IterativeDeepeningAlphaBetaSearch(game, worstStateValue, bestStateValue, maxComputationSeconds)

			"list all initial actions for WHITE" in {
				val actions = game.getActions(initState)
				assert(actions.size() != 0)
				println(actions)
			}
		}
	}
}
