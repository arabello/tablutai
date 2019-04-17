package ai.tablut.adversarial

import ai.tablut.serialization.TablutSerializer
import ai.tablut.state.{Action, Player, StateFacade}
import aima.core.search.adversarial.IterativeDeepeningAlphaBetaSearch
import org.scalatest.{FlatSpec, WordSpec}
import scala.collection.JavaConverters._
import ai.tablut.Metrics._

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
				val b = initState.board
				val p = initState.turn
				val expected = Set(
					Action(p, b(2)(4), b(2)(0)),Action(p, b(2)(4), b(2)(1)),Action(p, b(2)(4), b(2)(2)),Action(p, b(2)(4), b(2)(3)),
					Action(p, b(2)(4), b(2)(5)),Action(p, b(2)(4), b(2)(6)),Action(p, b(2)(4), b(2)(7)),Action(p, b(2)(4), b(2)(8)),

					Action(p, b(3)(4), b(3)(1)),Action(p, b(3)(4), b(3)(2)),Action(p, b(3)(4), b(3)(3)),
					Action(p, b(3)(4), b(3)(5)),Action(p, b(3)(4), b(3)(6)),Action(p, b(3)(4), b(3)(7)),

					Action(p, b(5)(4), b(5)(1)),Action(p, b(5)(4), b(5)(2)),Action(p, b(5)(4), b(5)(3)),
					Action(p, b(5)(4), b(5)(5)),Action(p, b(5)(4), b(5)(6)),Action(p, b(5)(4), b(5)(7)),

					Action(p, b(6)(4), b(6)(0)),Action(p, b(6)(4), b(6)(1)),Action(p, b(6)(4), b(6)(2)),Action(p, b(6)(4), b(6)(3)),
					Action(p, b(6)(4), b(6)(5)),Action(p, b(6)(4), b(6)(6)),Action(p, b(6)(4), b(6)(7)),Action(p, b(6)(4), b(6)(8)),


					Action(p, b(4)(2), b(0)(2)),Action(p, b(4)(2), b(1)(2)),Action(p, b(4)(2), b(2)(2)),Action(p, b(4)(2), b(3)(2)),
					Action(p, b(4)(2), b(5)(2)),Action(p, b(4)(2), b(6)(2)),Action(p, b(4)(2), b(7)(2)),Action(p, b(4)(2), b(8)(2)),

					Action(p, b(4)(3), b(1)(3)),Action(p, b(4)(3), b(2)(3)),Action(p, b(4)(3), b(3)(3)),
					Action(p, b(4)(3), b(5)(3)),Action(p, b(4)(3), b(6)(3)),Action(p, b(4)(3), b(7)(3)),

					Action(p, b(4)(5), b(1)(5)),Action(p, b(4)(5), b(2)(5)),Action(p, b(4)(5), b(3)(5)),
					Action(p, b(4)(5), b(5)(5)),Action(p, b(4)(5), b(6)(5)),Action(p, b(4)(5), b(7)(5)),

					Action(p, b(4)(6), b(0)(6)),Action(p, b(4)(6), b(1)(6)),Action(p, b(4)(6), b(2)(6)),Action(p, b(4)(6), b(3)(6)),
					Action(p, b(4)(6), b(5)(6)),Action(p, b(4)(6), b(6)(6)),Action(p, b(4)(6), b(7)(6)),Action(p, b(4)(6), b(8)(6)),
				)

				printMillis("getActions time"){
					val actions = game.getActions(initState)
					assert(actions.asScala.toSet == expected)
				}
			}
		}
	}
}
