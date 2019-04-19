package ai.tablut.adversarial

import ai.tablut.serialization.TablutSerializer
import ai.tablut.state.StateFacade
import org.scalatest.WordSpec

class HeuristicFunctionTest extends WordSpec{
	"HeuristicFunction" when{
		"using normal game rules" should{
			val factory = StateFacade.normalStateFactory()

			"eval blockEscapePoints for BLACK player" in{
				val hf = new HeuristicFunction(factory.context, 0f, 1f)

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

				assert(hf.blockEscapePoints(block1) == 0.25f)

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

				assert(hf.blockEscapePoints(block2) == 0.5f)

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

				assert(hf.blockEscapePoints(block3) == 0.75f)

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

				assert(hf.blockEscapePoints(block4) == 1f)
			}

			"eval blockEscapePoints for WHITE player" in{
				val hf = new HeuristicFunction(factory.context, 0f, 1f)

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

				assert(hf.blockEscapePoints(block1) == 0.75f)

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

				assert(hf.blockEscapePoints(block2) == 0.5f)

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

				assert(hf.blockEscapePoints(block3) == 0.25f)

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

				assert(hf.blockEscapePoints(block4) == 0f)
			}
		}
	}
}
