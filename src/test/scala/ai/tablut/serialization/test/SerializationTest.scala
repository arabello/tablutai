package ai.tablut.serialization.test

import ai.tablut.serialization.TablutSerializer
import ai.tablut.state.{Action, CellContent, Player, StateFacade}
import org.scalatest.WordSpec

class SerializationTest extends WordSpec{
	"TablutSerializer" when {
		"using normal game rules" should{
			val factory = StateFacade.normalStateFactory()

			"serialize an action" in{
				val action = Action(Player.WHITE,
					factory.createBoardCell((3,4), CellContent.WHITE).get,
					factory.createBoardCell((3,5), CellContent.WHITE).get)

				val json = TablutSerializer.toJson(action)

				assert(json == """{"from":"e4","to":"f4","turn":"WHITE"}""")
			}

			"parse a state" in{
				val json = """
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

				val state = TablutSerializer.fromJson(json, factory)
			}
		}
	}
}


