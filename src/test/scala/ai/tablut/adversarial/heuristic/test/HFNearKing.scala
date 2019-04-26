package ai.tablut.adversarial.heuristic.test

import ai.tablut.adversarial.heuristic.NormalGameHeuristicFactory
import ai.tablut.serialization.TablutSerializer
import ai.tablut.state.Player.{BLACK, WHITE}
import ai.tablut.state.StateFacade
import org.scalatest.WordSpec

class HFNearKing extends WordSpec{
	"HFKingAssasination" when {
		"using normal game rules" should {
			val factory = StateFacade.normalStateFactory()
			val heuristic = NormalGameHeuristicFactory.createKingAssasination()
			val initState = factory.createInitialState()

			"recognize king position" in{
				val jsonNear =
					"""
					  |{"board":[
					  |	["EMPTY","EMPTY","EMPTY","BLACK","BLACK","BLACK","EMPTY","EMPTY","EMPTY"],
					  |	["EMPTY","EMPTY","EMPTY","EMPTY","BLACK","EMPTY","EMPTY","EMPTY","EMPTY"],
					  |	["EMPTY","EMPTY","EMPTY","EMPTY","WHITE","EMPTY","EMPTY","EMPTY","EMPTY"],
					  |	["BLACK","EMPTY","EMPTY","EMPTY","WHITE","BLACK","EMPTY","EMPTY","EMPTY"],
					  |	["BLACK","BLACK","WHITE","WHITE","KING","WHITE","WHITE","BLACK","BLACK"],
					  |	["BLACK","EMPTY","EMPTY","EMPTY","WHITE","EMPTY","EMPTY","EMPTY","BLACK"],
					  |	["EMPTY","EMPTY","EMPTY","EMPTY","WHITE","EMPTY","EMPTY","EMPTY","EMPTY"],
					  |	["EMPTY","EMPTY","EMPTY","EMPTY","BLACK","EMPTY","EMPTY","EMPTY","EMPTY"],
					  |	["EMPTY","EMPTY","EMPTY","BLACK","BLACK","BLACK","EMPTY","EMPTY","EMPTY"]
					  |	],
					  |	"turn":"BLACK"}""".stripMargin
				val near = TablutSerializer.fromJson(jsonNear, factory)
				assert(heuristic.eval(near, BLACK) > heuristic.eval(initState, BLACK))
				assert(heuristic.eval(near, WHITE) < heuristic.eval(initState, BLACK))
				assert(heuristic.eval(near, BLACK) > heuristic.eval(initState, WHITE))
			}
		}
	}
}
