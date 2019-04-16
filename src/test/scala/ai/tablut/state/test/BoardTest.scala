package ai.tablut.state.test

import ai.tablut.serialization.TablutSerializer
import ai.tablut.state.{Action, Player, StateFacade}
import org.scalatest.WordSpec

class BoardTest extends WordSpec{
	"Board" when {
		"using normal game rules" should{
			val factory = StateFacade.normalStateFactory()
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

			"transition state applying an action" in{
				val expected = """
				                 |{"board":[
				                 |["EMPTY","EMPTY","EMPTY","BLACK","BLACK","BLACK","EMPTY","EMPTY","EMPTY"],
				                 |["EMPTY","EMPTY","EMPTY","EMPTY","BLACK","EMPTY","EMPTY","EMPTY","EMPTY"],
				                 |["EMPTY","EMPTY","EMPTY","EMPTY","WHITE","EMPTY","EMPTY","EMPTY","EMPTY"],
				                 |["BLACK","EMPTY","EMPTY","EMPTY","EMPTY","EMPTY","EMPTY","WHITE","BLACK"],
				                 |["BLACK","BLACK","WHITE","WHITE","KING","WHITE","WHITE","BLACK","BLACK"],
				                 |["BLACK","EMPTY","EMPTY","EMPTY","WHITE","EMPTY","EMPTY","EMPTY","BLACK"],
				                 |["EMPTY","EMPTY","EMPTY","EMPTY","WHITE","EMPTY","EMPTY","EMPTY","EMPTY"],
				                 |["EMPTY","EMPTY","EMPTY","EMPTY","BLACK","EMPTY","EMPTY","EMPTY","EMPTY"],
				                 |["EMPTY","EMPTY","EMPTY","BLACK","BLACK","BLACK","EMPTY","EMPTY","EMPTY"]
				                 |],
				                 |"turn":"WHITE"}""".stripMargin.replace("\n", "").trim
				val action = Action(Player.WHITE, state.board(3)(4), state.board(3)(7))
				val result = state.copy(board = state.board.applyAction(action))
				assert(TablutSerializer.toJson(result) == expected)

				val e1 = """
				                 |{"board":[
				                 |["EMPTY","EMPTY","EMPTY","BLACK","BLACK","BLACK","EMPTY","BLACK","EMPTY"],
				                 |["EMPTY","EMPTY","EMPTY","EMPTY","BLACK","EMPTY","EMPTY","EMPTY","EMPTY"],
				                 |["EMPTY","EMPTY","EMPTY","EMPTY","WHITE","EMPTY","EMPTY","EMPTY","EMPTY"],
				                 |["BLACK","EMPTY","EMPTY","EMPTY","WHITE","EMPTY","EMPTY","EMPTY","BLACK"],
				                 |["BLACK","BLACK","WHITE","WHITE","KING","WHITE","WHITE","EMPTY","BLACK"],
				                 |["BLACK","EMPTY","EMPTY","EMPTY","WHITE","EMPTY","EMPTY","EMPTY","BLACK"],
				                 |["EMPTY","EMPTY","EMPTY","EMPTY","WHITE","EMPTY","EMPTY","EMPTY","EMPTY"],
				                 |["EMPTY","EMPTY","EMPTY","EMPTY","BLACK","EMPTY","EMPTY","EMPTY","EMPTY"],
				                 |["EMPTY","EMPTY","EMPTY","BLACK","BLACK","BLACK","EMPTY","EMPTY","EMPTY"]
				                 |],
				                 |"turn":"BLACK"}""".stripMargin.replace("\n", "").trim
				val a1 = Action(Player.BLACK, state.board(4)(7), state.board(0)(7))
				val r1 = state.copy(board = state.board.applyAction(a1), turn = Player.BLACK)
				assert(TablutSerializer.toJson(r1) == e1)

				val e2 = """
				           |{"board":[
				           |["EMPTY","EMPTY","EMPTY","BLACK","BLACK","BLACK","EMPTY","EMPTY","BLACK"],
				           |["EMPTY","EMPTY","EMPTY","EMPTY","BLACK","EMPTY","EMPTY","EMPTY","EMPTY"],
				           |["EMPTY","EMPTY","EMPTY","EMPTY","WHITE","EMPTY","EMPTY","EMPTY","EMPTY"],
				           |["BLACK","EMPTY","EMPTY","EMPTY","WHITE","EMPTY","EMPTY","EMPTY","BLACK"],
				           |["BLACK","BLACK","WHITE","WHITE","KING","WHITE","WHITE","EMPTY","BLACK"],
				           |["BLACK","EMPTY","EMPTY","EMPTY","WHITE","EMPTY","EMPTY","EMPTY","BLACK"],
				           |["EMPTY","EMPTY","EMPTY","EMPTY","WHITE","EMPTY","EMPTY","EMPTY","EMPTY"],
				           |["EMPTY","EMPTY","EMPTY","EMPTY","BLACK","EMPTY","EMPTY","EMPTY","EMPTY"],
				           |["EMPTY","EMPTY","EMPTY","BLACK","BLACK","BLACK","EMPTY","EMPTY","EMPTY"]
				           |],
				           |"turn":"BLACK"}""".stripMargin.replace("\n", "").trim
				val a2 = Action(Player.BLACK, r1.board(0)(7), r1.board(0)(8))
				val r2 = r1.copy(board = r1.board.applyAction(a2), turn = Player.BLACK)
				assert(TablutSerializer.toJson(r2) == e2)
			}
		}
	}
}
