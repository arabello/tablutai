package ai.tablut.state.test

import ai.tablut.serialization.TablutSerializer
import ai.tablut.state.{Action, CellContent, Player, StateFacade}
import org.scalatest.WordSpec

class ActionTest extends WordSpec{
	"Action" when {
		"using normal game rules" should {
			val factory = StateFacade.normalStateFactory()
			val rules = factory.context
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

			val board =  TablutSerializer.fromJson(json, factory).board

			"be game rules complied" in {
				assert(!Action(Player.WHITE, board(2)(4), board(3)(2)).isGameRulesComplied(rules))
				assert(!Action(Player.WHITE, board(2)(4), board(3)(2)).isGameRulesComplied(rules))
			}

			"a legal move" in{
				assert(!Action(Player.WHITE, board(2)(4), board(6)(4)).isLegal(board))
				assert(!Action(Player.WHITE, board(3)(4), board(4)(4)).isLegal(board))
			}
		}
	}
}
