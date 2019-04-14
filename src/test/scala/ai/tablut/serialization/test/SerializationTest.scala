package ai.tablut.serialization.test

import ai.tablut.serialization.TablutSerializer
import ai.tablut.state.normal.NormalStateFactory
import ai.tablut.state.{Action, CellContent, Player}
import org.scalatest.FlatSpec

class SerializationTest extends FlatSpec{
	"ActionJsonAdapter" should "serialize an action" in{
		val action = Action(Player.WHITE,
			NormalStateFactory.createBoardCell((4,3), CellContent.WHITE),
			NormalStateFactory.createBoardCell((5,3), CellContent.WHITE))

		val json = TablutSerializer.toJson(action)

		assert(json == """{"from":"e4","to":"f4","turn":"WHITE"}""")
	}
}


