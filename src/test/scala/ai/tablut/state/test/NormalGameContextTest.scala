package ai.tablut.state.test

import ai.tablut.serialization.TablutSerializer
import ai.tablut.state.{Action, CellContent, Player, StateFacade}
import org.scalatest.WordSpec

class NormalGameContextTest extends WordSpec{
	"NormalGameContext" when{
		"using normal game rules" should{
			val factory = StateFacade.normalStateFactory()
			val iniState = factory.createInitialState()

			"detect WHITE winner" in{
				val jsonStateWhiteWinner =
					"""
					  |{"board":[
					  |	["EMPTY","KING","EMPTY","BLACK","BLACK","BLACK","EMPTY","EMPTY","EMPTY"],
					  |	["EMPTY","EMPTY","EMPTY","EMPTY","BLACK","EMPTY","EMPTY","EMPTY","EMPTY"],
					  |	["EMPTY","EMPTY","EMPTY","EMPTY","WHITE","EMPTY","EMPTY","EMPTY","EMPTY"],
					  |	["BLACK","EMPTY","EMPTY","EMPTY","WHITE","EMPTY","EMPTY","EMPTY","BLACK"],
					  |	["BLACK","BLACK","WHITE","WHITE","EMPTY","WHITE","WHITE","BLACK","BLACK"],
					  |	["BLACK","EMPTY","EMPTY","EMPTY","WHITE","EMPTY","EMPTY","EMPTY","BLACK"],
					  |	["EMPTY","EMPTY","EMPTY","EMPTY","WHITE","EMPTY","EMPTY","EMPTY","EMPTY"],
					  |	["EMPTY","EMPTY","EMPTY","EMPTY","BLACK","EMPTY","EMPTY","EMPTY","EMPTY"],
					  |	["EMPTY","EMPTY","EMPTY","BLACK","BLACK","BLACK","EMPTY","EMPTY","EMPTY"]
					  |	],
					  |	"turn":"WHITE"}""".stripMargin

				assert(factory.context.getWinner(TablutSerializer.fromJson(jsonStateWhiteWinner, factory)).contains(Player.WHITE))

				val jsonState =
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

				assert(!factory.context.getWinner(TablutSerializer.fromJson(jsonState, factory)).contains(Player.WHITE))
			}

			"detect WHITE lost" in{
				val state = iniState.transform(Map(
					(4,4) -> CellContent.EMPTY,
					(4,5) -> CellContent.BLACK,
					(4,6) -> CellContent.KING
				))

				assert(!factory.context.getWinner(state).contains(Player.WHITE))
			}

			"detect BLACK winner" in{
				val state = iniState.transform(Map(
					(4,4) -> CellContent.EMPTY,
					(4,6) -> CellContent.KING
				)).nextPlayer


				assert(factory.context.getWinner(state.applyAction(Action(Player.BLACK, state(0)(5).get, state(4,5).get))).contains(Player.BLACK))
			}

			"detect BLACK lost" in{
				val state = iniState.transform(Map(
					(4,4) -> CellContent.EMPTY,
					(7,2) -> CellContent.KING
				)).nextPlayer

				assert(!factory.context.getWinner(state).contains(Player.BLACK))
			}
		}
	}
}
