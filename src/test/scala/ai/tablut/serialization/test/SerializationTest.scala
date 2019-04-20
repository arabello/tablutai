package ai.tablut.serialization.test

import ai.tablut.serialization.TablutSerializer
import ai.tablut.state.{Action, CellContent, Turn, StateFacade}
import org.scalatest.WordSpec

class SerializationTest extends WordSpec{
	"TablutSerializer" when {
		"using normal game rules" should{
			val factory = StateFacade.normalStateFactory()

			"serialize an action" in{
				val action = Action(Turn.WHITE,
					factory.createBoardCell((3,4), CellContent.WHITE).get,
					factory.createBoardCell((3,5), CellContent.WHITE).get)

				val json = TablutSerializer.toJson(action)

				assert(json == """{"from":"e4","to":"f4","turn":"WHITE"}""")
			}
		}
	}
}


