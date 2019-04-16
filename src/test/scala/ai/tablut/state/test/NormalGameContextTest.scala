package ai.tablut.state.test

import ai.tablut.serialization.TablutSerializer
import ai.tablut.state.StateFacade
import org.scalatest.WordSpec

class NormalGameContextTest extends WordSpec{
	"NormalGameContext" when{
		"using normal game rules" should{
			"detect WHITE winner" in{
				val factory = StateFacade.normalStateFactory()
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

				assert(factory.context.isWinner(TablutSerializer.fromJson(jsonStateWhiteWinner, factory)))

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

				assert(!factory.context.isWinner(TablutSerializer.fromJson(jsonState, factory)))
			}

			"detect BLACK winner" in{
				val factory = StateFacade.normalStateFactory()
				val doubleSideLeftRight =
					"""
					  |{"board":[
					  |	["EMPTY","EMPTY","EMPTY","BLACK","BLACK","BLACK","EMPTY","EMPTY","EMPTY"],
					  |	["EMPTY","EMPTY","EMPTY","EMPTY","BLACK","EMPTY","EMPTY","EMPTY","EMPTY"],
					  |	["EMPTY","EMPTY","EMPTY","EMPTY","WHITE","BLACK","KING","BLACK","EMPTY"],
					  |	["BLACK","EMPTY","EMPTY","EMPTY","WHITE","EMPTY","EMPTY","EMPTY","EMPTY"],
					  |	["BLACK","BLACK","WHITE","WHITE","EMPTY","WHITE","WHITE","BLACK","EMPTY"],
					  |	["BLACK","EMPTY","EMPTY","EMPTY","WHITE","EMPTY","EMPTY","EMPTY","BLACK"],
					  |	["EMPTY","EMPTY","EMPTY","EMPTY","WHITE","EMPTY","EMPTY","EMPTY","EMPTY"],
					  |	["EMPTY","EMPTY","EMPTY","EMPTY","BLACK","EMPTY","EMPTY","EMPTY","EMPTY"],
					  |	["EMPTY","EMPTY","EMPTY","BLACK","BLACK","BLACK","EMPTY","EMPTY","EMPTY"]
					  |	],
					  |	"turn":"BLACK"}""".stripMargin

				assert(factory.context.isWinner(TablutSerializer.fromJson(doubleSideLeftRight, factory)))

				val doubleSideUpDown =
					"""
					  |{"board":[
					  |	["EMPTY","EMPTY","EMPTY","BLACK","BLACK","BLACK","EMPTY","EMPTY","EMPTY"],
					  |	["EMPTY","EMPTY","EMPTY","EMPTY","BLACK","EMPTY","BLACK","EMPTY","EMPTY"],
					  |	["EMPTY","EMPTY","EMPTY","EMPTY","WHITE","EMPTY","KING","EMPTY","EMPTY"],
					  |	["BLACK","EMPTY","EMPTY","EMPTY","WHITE","EMPTY","BLACK","EMPTY","EMPTY"],
					  |	["BLACK","BLACK","WHITE","WHITE","EMPTY","WHITE","WHITE","BLACK","EMPTY"],
					  |	["BLACK","EMPTY","EMPTY","EMPTY","WHITE","EMPTY","EMPTY","EMPTY","BLACK"],
					  |	["EMPTY","EMPTY","EMPTY","EMPTY","WHITE","EMPTY","EMPTY","EMPTY","EMPTY"],
					  |	["EMPTY","EMPTY","EMPTY","EMPTY","BLACK","EMPTY","EMPTY","EMPTY","EMPTY"],
					  |	["EMPTY","EMPTY","EMPTY","BLACK","BLACK","BLACK","EMPTY","EMPTY","EMPTY"]
					  |	],
					  |	"turn":"BLACK"}""".stripMargin

				assert(factory.context.isWinner(TablutSerializer.fromJson(doubleSideUpDown, factory)))

				val s1 = TablutSerializer.fromJson("""
                    |{"board":[
                    |	["EMPTY","EMPTY","EMPTY","BLACK","BLACK","BLACK","EMPTY","EMPTY","EMPTY"],
                    |	["EMPTY","EMPTY","EMPTY","EMPTY","BLACK","EMPTY","EMPTY","EMPTY","EMPTY"],
                    |	["EMPTY","EMPTY","EMPTY","EMPTY","WHITE","EMPTY","EMPTY","EMPTY","EMPTY"],
                    |	["BLACK","EMPTY","EMPTY","EMPTY","WHITE","EMPTY","EMPTY","EMPTY","BLACK"],
                    |	["BLACK","BLACK","WHITE","WHITE","EMPTY","WHITE","WHITE","BLACK","BLACK"],
                    |	["BLACK","EMPTY","EMPTY","EMPTY","WHITE","EMPTY","EMPTY","EMPTY","EMPTY"],
                    |	["EMPTY","EMPTY","EMPTY","EMPTY","WHITE","BLACK","EMPTY","EMPTY","EMPTY"],
                    |	["EMPTY","EMPTY","EMPTY","EMPTY","BLACK","KING","EMPTY","EMPTY","EMPTY"],
                    |	["EMPTY","EMPTY","EMPTY","BLACK","BLACK","BLACK","EMPTY","EMPTY","EMPTY"]
                    |	],
                    |	"turn":"BLACK"}""".stripMargin, factory)
				assert(factory.context.isWinner(s1))

				val s2 = TablutSerializer.fromJson("""
                     |{"board":[
                     |	["EMPTY","EMPTY","EMPTY","BLACK","BLACK","BLACK","EMPTY","EMPTY","EMPTY"],
                     |	["EMPTY","EMPTY","EMPTY","EMPTY","BLACK","EMPTY","EMPTY","EMPTY","EMPTY"],
                     |	["EMPTY","EMPTY","EMPTY","EMPTY","WHITE","EMPTY","EMPTY","EMPTY","EMPTY"],
                     |	["BLACK","EMPTY","EMPTY","EMPTY","WHITE","EMPTY","EMPTY","EMPTY","BLACK"],
                     |	["BLACK","BLACK","WHITE","WHITE","EMPTY","WHITE","WHITE","BLACK","BLACK"],
                     |	["BLACK","EMPTY","EMPTY","EMPTY","BLACK","EMPTY","EMPTY","EMPTY","EMPTY"],
                     |	["EMPTY","EMPTY","EMPTY","EMPTY","KING","WHITE","EMPTY","EMPTY","EMPTY"],
                     |	["EMPTY","EMPTY","EMPTY","EMPTY","BLACK","EMPTY","EMPTY","EMPTY","EMPTY"],
                     |	["EMPTY","EMPTY","EMPTY","BLACK","BLACK","BLACK","EMPTY","EMPTY","EMPTY"]
                     |	],
                     |	"turn":"BLACK"}""".stripMargin, factory)
				assert(factory.context.isWinner(s2))

				val s3 = TablutSerializer.fromJson("""
                     |{"board":[
                     |	["EMPTY","EMPTY","EMPTY","BLACK","BLACK","BLACK","EMPTY","EMPTY","EMPTY"],
                     |	["EMPTY","EMPTY","EMPTY","EMPTY","BLACK","EMPTY","EMPTY","EMPTY","EMPTY"],
                     |	["EMPTY","EMPTY","EMPTY","EMPTY","WHITE","EMPTY","EMPTY","EMPTY","EMPTY"],
                     |	["BLACK","EMPTY","EMPTY","EMPTY","WHITE","EMPTY","EMPTY","EMPTY","BLACK"],
                     |	["BLACK","BLACK","KING","BLACK","EMPTY","WHITE","WHITE","BLACK","BLACK"],
                     |	["BLACK","EMPTY","EMPTY","EMPTY","BLACK","EMPTY","EMPTY","EMPTY","EMPTY"],
                     |	["EMPTY","EMPTY","EMPTY","EMPTY","EMPTY","WHITE","EMPTY","EMPTY","EMPTY"],
                     |	["EMPTY","EMPTY","EMPTY","EMPTY","BLACK","EMPTY","EMPTY","EMPTY","EMPTY"],
                     |	["EMPTY","EMPTY","EMPTY","BLACK","BLACK","BLACK","EMPTY","EMPTY","EMPTY"]
                     |	],
                     |	"turn":"BLACK"}""".stripMargin, factory)
				assert(factory.context.isWinner(s3))

				val s4 = TablutSerializer.fromJson("""
                     |{"board":[
                     |	["EMPTY","EMPTY","EMPTY","BLACK","BLACK","BLACK","EMPTY","EMPTY","EMPTY"],
                     |	["EMPTY","EMPTY","EMPTY","EMPTY","BLACK","EMPTY","EMPTY","EMPTY","EMPTY"],
                     |	["EMPTY","EMPTY","EMPTY","EMPTY","WHITE","EMPTY","EMPTY","EMPTY","EMPTY"],
                     |	["BLACK","EMPTY","EMPTY","EMPTY","WHITE","EMPTY","EMPTY","EMPTY","BLACK"],
                     |	["BLACK","BLACK","EMPTY","BLACK","EMPTY","BLACK","KING","BLACK","BLACK"],
                     |	["BLACK","EMPTY","EMPTY","EMPTY","BLACK","EMPTY","EMPTY","EMPTY","EMPTY"],
                     |	["EMPTY","EMPTY","EMPTY","EMPTY","EMPTY","WHITE","EMPTY","EMPTY","EMPTY"],
                     |	["EMPTY","EMPTY","EMPTY","EMPTY","BLACK","EMPTY","EMPTY","EMPTY","EMPTY"],
                     |	["EMPTY","EMPTY","EMPTY","BLACK","BLACK","BLACK","EMPTY","EMPTY","EMPTY"]
                     |	],
                     |	"turn":"BLACK"}""".stripMargin, factory)
				assert(factory.context.isWinner(s4))

				val s5 = TablutSerializer.fromJson("""
                     |{"board":[
                     |	["EMPTY","EMPTY","EMPTY","BLACK","BLACK","BLACK","EMPTY","EMPTY","EMPTY"],
                     |	["EMPTY","EMPTY","EMPTY","EMPTY","BLACK","EMPTY","EMPTY","EMPTY","EMPTY"],
                     |	["EMPTY","EMPTY","EMPTY","EMPTY","KING","EMPTY","EMPTY","EMPTY","EMPTY"],
                     |	["BLACK","EMPTY","EMPTY","EMPTY","BLACK","EMPTY","EMPTY","EMPTY","BLACK"],
                     |	["BLACK","BLACK","EMPTY","BLACK","EMPTY","BLACK","EMPTY","BLACK","BLACK"],
                     |	["BLACK","EMPTY","EMPTY","EMPTY","BLACK","EMPTY","EMPTY","EMPTY","EMPTY"],
                     |	["EMPTY","EMPTY","EMPTY","EMPTY","EMPTY","WHITE","EMPTY","EMPTY","EMPTY"],
                     |	["EMPTY","EMPTY","EMPTY","EMPTY","BLACK","EMPTY","EMPTY","EMPTY","EMPTY"],
                     |	["EMPTY","EMPTY","EMPTY","BLACK","BLACK","BLACK","EMPTY","EMPTY","EMPTY"]
                     |	],
                     |	"turn":"BLACK"}""".stripMargin, factory)
				assert(factory.context.isWinner(s5))


				val s6 = TablutSerializer.fromJson("""
                     |{"board":[
                     |	["EMPTY","EMPTY","EMPTY","BLACK","BLACK","BLACK","EMPTY","EMPTY","EMPTY"],
                     |	["EMPTY","EMPTY","EMPTY","EMPTY","BLACK","KING","EMPTY","EMPTY","EMPTY"],
                     |	["EMPTY","EMPTY","EMPTY","EMPTY","EMPTY","BLACK","EMPTY","EMPTY","EMPTY"],
                     |	["BLACK","EMPTY","EMPTY","EMPTY","BLACK","EMPTY","EMPTY","EMPTY","BLACK"],
                     |	["BLACK","BLACK","EMPTY","BLACK","EMPTY","BLACK","EMPTY","BLACK","BLACK"],
                     |	["BLACK","EMPTY","EMPTY","EMPTY","BLACK","EMPTY","EMPTY","EMPTY","EMPTY"],
                     |	["EMPTY","EMPTY","EMPTY","EMPTY","EMPTY","WHITE","EMPTY","EMPTY","EMPTY"],
                     |	["EMPTY","EMPTY","EMPTY","EMPTY","BLACK","EMPTY","EMPTY","EMPTY","EMPTY"],
                     |	["EMPTY","EMPTY","EMPTY","BLACK","BLACK","BLACK","EMPTY","EMPTY","EMPTY"]
                     |	],
                     |	"turn":"BLACK"}""".stripMargin, factory)
				assert(factory.context.isWinner(s6))
			}
		}
	}
}
