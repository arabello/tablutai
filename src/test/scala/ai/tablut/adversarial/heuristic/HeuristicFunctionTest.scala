package ai.tablut.adversarial.heuristic

import ai.tablut.serialization.TablutSerializer
import ai.tablut.state.Turn._
import ai.tablut.state.StateFacade
import org.scalatest.WordSpec

class HeuristicFunctionTest extends WordSpec{
	"HeuristicFunction" when{
		"using normal game rules" should{
			val factory = StateFacade.normalStateFactory()
			val hf = new HFFactory(factory.context)

			"eval blockEscapePoints for BLACK player" in{
				val jsonBlock1 =
					"""
					  |{"board":[
					  |	["EMPTY","EMPTY","EMPTY","BLACK","BLACK","BLACK","EMPTY","EMPTY","EMPTY"],
					  |	["EMPTY","EMPTY","EMPTY","EMPTY","BLACK","EMPTY","EMPTY","EMPTY","EMPTY"],
					  |	["EMPTY","EMPTY","BLACK","EMPTY","WHITE","EMPTY","EMPTY","EMPTY","EMPTY"],
					  |	["BLACK","EMPTY","EMPTY","EMPTY","WHITE","EMPTY","EMPTY","EMPTY","BLACK"],
					  |	["BLACK","BLACK","WHITE","WHITE","KING","WHITE","WHITE","BLACK","BLACK"],
					  |	["BLACK","EMPTY","EMPTY","EMPTY","WHITE","EMPTY","EMPTY","EMPTY","BLACK"],
					  |	["EMPTY","EMPTY","EMPTY","EMPTY","WHITE","EMPTY","EMPTY","EMPTY","EMPTY"],
					  |	["EMPTY","EMPTY","EMPTY","EMPTY","BLACK","EMPTY","EMPTY","EMPTY","EMPTY"],
					  |	["EMPTY","EMPTY","EMPTY","BLACK","BLACK","BLACK","EMPTY","EMPTY","EMPTY"]
					  |	],
					  |	"turn":"BLACK"}""".stripMargin
				val block1 = TablutSerializer.fromJson(jsonBlock1, factory)

				assert(hf.createBlockEscapePoints.eval(block1, BLACK) == 0.25f)

				val jsonBlock2 =
					"""
					  |{"board":[
					  |	["EMPTY","EMPTY","EMPTY","BLACK","BLACK","BLACK","EMPTY","EMPTY","EMPTY"],
					  |	["EMPTY","EMPTY","EMPTY","EMPTY","BLACK","EMPTY","EMPTY","EMPTY","EMPTY"],
					  |	["EMPTY","EMPTY","BLACK","EMPTY","WHITE","EMPTY","BLACK","EMPTY","EMPTY"],
					  |	["EMPTY","EMPTY","EMPTY","EMPTY","WHITE","EMPTY","EMPTY","EMPTY","EMPTY"],
					  |	["BLACK","BLACK","WHITE","WHITE","KING","WHITE","WHITE","BLACK","BLACK"],
					  |	["BLACK","EMPTY","EMPTY","EMPTY","WHITE","EMPTY","EMPTY","EMPTY","BLACK"],
					  |	["EMPTY","EMPTY","EMPTY","EMPTY","WHITE","EMPTY","EMPTY","EMPTY","EMPTY"],
					  |	["EMPTY","EMPTY","EMPTY","EMPTY","BLACK","EMPTY","EMPTY","EMPTY","EMPTY"],
					  |	["EMPTY","EMPTY","EMPTY","BLACK","BLACK","BLACK","EMPTY","EMPTY","EMPTY"]
					  |	],
					  |	"turn":"BLACK"}""".stripMargin
				val block2 = TablutSerializer.fromJson(jsonBlock2, factory)

				assert(hf.createBlockEscapePoints.eval(block2, BLACK) == 0.5f)

				val jsonBlock3 =
					"""
					  |{"board":[
					  |	["EMPTY","EMPTY","EMPTY","BLACK","BLACK","BLACK","EMPTY","EMPTY","EMPTY"],
					  |	["EMPTY","EMPTY","EMPTY","EMPTY","BLACK","EMPTY","EMPTY","EMPTY","EMPTY"],
					  |	["EMPTY","EMPTY","BLACK","EMPTY","WHITE","EMPTY","BLACK","EMPTY","EMPTY"],
					  |	["EMPTY","EMPTY","EMPTY","EMPTY","WHITE","EMPTY","EMPTY","EMPTY","EMPTY"],
					  |	["BLACK","BLACK","WHITE","WHITE","KING","WHITE","WHITE","BLACK","BLACK"],
					  |	["BLACK","EMPTY","EMPTY","EMPTY","WHITE","EMPTY","EMPTY","EMPTY","BLACK"],
					  |	["EMPTY","EMPTY","BLACK","EMPTY","WHITE","EMPTY","EMPTY","EMPTY","EMPTY"],
					  |	["EMPTY","EMPTY","EMPTY","EMPTY","BLACK","EMPTY","EMPTY","EMPTY","EMPTY"],
					  |	["EMPTY","EMPTY","EMPTY","EMPTY","BLACK","BLACK","EMPTY","EMPTY","EMPTY"]
					  |	],
					  |	"turn":"BLACK"}""".stripMargin
				val block3 = TablutSerializer.fromJson(jsonBlock3, factory)

				assert(hf.createBlockEscapePoints.eval(block3, BLACK) == 0.75f)

				val jsonBlock4 =
					"""
					  |{"board":[
					  |	["EMPTY","EMPTY","EMPTY","BLACK","BLACK","BLACK","EMPTY","EMPTY","EMPTY"],
					  |	["EMPTY","EMPTY","EMPTY","EMPTY","BLACK","EMPTY","EMPTY","EMPTY","EMPTY"],
					  |	["EMPTY","EMPTY","BLACK","EMPTY","WHITE","EMPTY","BLACK","EMPTY","EMPTY"],
					  |	["EMPTY","EMPTY","EMPTY","EMPTY","WHITE","EMPTY","EMPTY","EMPTY","EMPTY"],
					  |	["BLACK","BLACK","WHITE","WHITE","KING","WHITE","WHITE","BLACK","BLACK"],
					  |	["BLACK","EMPTY","EMPTY","EMPTY","WHITE","EMPTY","EMPTY","EMPTY","BLACK"],
					  |	["EMPTY","EMPTY","BLACK","EMPTY","WHITE","EMPTY","BLACK","EMPTY","EMPTY"],
					  |	["EMPTY","EMPTY","EMPTY","EMPTY","BLACK","EMPTY","EMPTY","EMPTY","EMPTY"],
					  |	["EMPTY","EMPTY","EMPTY","EMPTY","BLACK","EMPTY","EMPTY","EMPTY","EMPTY"]
					  |	],
					  |	"turn":"BLACK"}""".stripMargin
				val block4 = TablutSerializer.fromJson(jsonBlock4, factory)

				assert(hf.createBlockEscapePoints.eval(block4, BLACK) == 1f)
			}

			"eval blockEscapePoints for WHITE player" in{
				val jsonBlock1 =
					"""
					  |{"board":[
					  |	["EMPTY","EMPTY","EMPTY","BLACK","BLACK","BLACK","EMPTY","EMPTY","EMPTY"],
					  |	["EMPTY","EMPTY","EMPTY","EMPTY","BLACK","EMPTY","EMPTY","EMPTY","EMPTY"],
					  |	["EMPTY","EMPTY","BLACK","EMPTY","WHITE","EMPTY","EMPTY","EMPTY","EMPTY"],
					  |	["BLACK","EMPTY","EMPTY","EMPTY","WHITE","EMPTY","EMPTY","EMPTY","BLACK"],
					  |	["BLACK","BLACK","WHITE","WHITE","KING","WHITE","WHITE","BLACK","BLACK"],
					  |	["BLACK","EMPTY","EMPTY","EMPTY","WHITE","EMPTY","EMPTY","EMPTY","BLACK"],
					  |	["EMPTY","EMPTY","EMPTY","EMPTY","WHITE","EMPTY","EMPTY","EMPTY","EMPTY"],
					  |	["EMPTY","EMPTY","EMPTY","EMPTY","BLACK","EMPTY","EMPTY","EMPTY","EMPTY"],
					  |	["EMPTY","EMPTY","EMPTY","BLACK","BLACK","BLACK","EMPTY","EMPTY","EMPTY"]
					  |	],
					  |	"turn":"WHITE"}""".stripMargin
				val block1 = TablutSerializer.fromJson(jsonBlock1, factory)

				assert(hf.createBlockEscapePoints.eval(block1, WHITE) == 0.75f)

				val jsonBlock2 =
					"""
					  |{"board":[
					  |	["EMPTY","EMPTY","EMPTY","BLACK","BLACK","BLACK","EMPTY","EMPTY","EMPTY"],
					  |	["EMPTY","EMPTY","EMPTY","EMPTY","BLACK","EMPTY","EMPTY","EMPTY","EMPTY"],
					  |	["EMPTY","EMPTY","BLACK","EMPTY","WHITE","EMPTY","BLACK","EMPTY","EMPTY"],
					  |	["EMPTY","EMPTY","EMPTY","EMPTY","WHITE","EMPTY","EMPTY","EMPTY","EMPTY"],
					  |	["BLACK","BLACK","WHITE","WHITE","KING","WHITE","WHITE","BLACK","BLACK"],
					  |	["BLACK","EMPTY","EMPTY","EMPTY","WHITE","EMPTY","EMPTY","EMPTY","BLACK"],
					  |	["EMPTY","EMPTY","EMPTY","EMPTY","WHITE","EMPTY","EMPTY","EMPTY","EMPTY"],
					  |	["EMPTY","EMPTY","EMPTY","EMPTY","BLACK","EMPTY","EMPTY","EMPTY","EMPTY"],
					  |	["EMPTY","EMPTY","EMPTY","BLACK","BLACK","BLACK","EMPTY","EMPTY","EMPTY"]
					  |	],
					  |	"turn":"WHITE"}""".stripMargin
				val block2 = TablutSerializer.fromJson(jsonBlock2, factory)

				assert(hf.createBlockEscapePoints.eval(block2, WHITE) == 0.5f)

				val jsonBlock3 =
					"""
					  |{"board":[
					  |	["EMPTY","EMPTY","EMPTY","BLACK","BLACK","BLACK","EMPTY","EMPTY","EMPTY"],
					  |	["EMPTY","EMPTY","EMPTY","EMPTY","BLACK","EMPTY","EMPTY","EMPTY","EMPTY"],
					  |	["EMPTY","EMPTY","BLACK","EMPTY","WHITE","EMPTY","BLACK","EMPTY","EMPTY"],
					  |	["EMPTY","EMPTY","EMPTY","EMPTY","WHITE","EMPTY","EMPTY","EMPTY","EMPTY"],
					  |	["BLACK","BLACK","WHITE","WHITE","KING","WHITE","WHITE","BLACK","BLACK"],
					  |	["BLACK","EMPTY","EMPTY","EMPTY","WHITE","EMPTY","EMPTY","EMPTY","BLACK"],
					  |	["EMPTY","EMPTY","BLACK","EMPTY","WHITE","EMPTY","EMPTY","EMPTY","EMPTY"],
					  |	["EMPTY","EMPTY","EMPTY","EMPTY","BLACK","EMPTY","EMPTY","EMPTY","EMPTY"],
					  |	["EMPTY","EMPTY","EMPTY","EMPTY","BLACK","BLACK","EMPTY","EMPTY","EMPTY"]
					  |	],
					  |	"turn":"WHITE"}""".stripMargin
				val block3 = TablutSerializer.fromJson(jsonBlock3, factory)

				assert(hf.createBlockEscapePoints.eval(block3, WHITE) == 0.25f)

				val jsonBlock4 =
					"""
					  |{"board":[
					  |	["EMPTY","EMPTY","EMPTY","BLACK","BLACK","BLACK","EMPTY","EMPTY","EMPTY"],
					  |	["EMPTY","EMPTY","EMPTY","EMPTY","BLACK","EMPTY","EMPTY","EMPTY","EMPTY"],
					  |	["EMPTY","EMPTY","BLACK","EMPTY","WHITE","EMPTY","BLACK","EMPTY","EMPTY"],
					  |	["EMPTY","EMPTY","EMPTY","EMPTY","WHITE","EMPTY","EMPTY","EMPTY","EMPTY"],
					  |	["BLACK","BLACK","WHITE","WHITE","KING","WHITE","WHITE","BLACK","BLACK"],
					  |	["BLACK","EMPTY","EMPTY","EMPTY","WHITE","EMPTY","EMPTY","EMPTY","BLACK"],
					  |	["EMPTY","EMPTY","BLACK","EMPTY","WHITE","EMPTY","BLACK","EMPTY","EMPTY"],
					  |	["EMPTY","EMPTY","EMPTY","EMPTY","BLACK","EMPTY","EMPTY","EMPTY","EMPTY"],
					  |	["EMPTY","EMPTY","EMPTY","EMPTY","BLACK","EMPTY","EMPTY","EMPTY","EMPTY"]
					  |	],
					  |	"turn":"WHITE"}""".stripMargin
				val block4 = TablutSerializer.fromJson(jsonBlock4, factory)

				assert(hf.createBlockEscapePoints.eval(block4, WHITE) == 0f)
			}
		}
	}
}
