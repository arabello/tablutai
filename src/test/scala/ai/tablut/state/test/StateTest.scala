package ai.tablut.state.test

import ai.tablut.serialization.TablutSerializer
import ai.tablut.state._
import org.scalatest.WordSpec

class StateTest extends WordSpec{
	"State" when {
		"using normal game rules" should{
			val factory = StateFacade.normalStateFactory()
			val state = factory.createInitialState()

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
				                 |"turn":"BLACK"}""".stripMargin.replace("\n", "").trim
				val action = Action(Player.WHITE, state(3)(4).get, state(3)(7).get)
				val result = state.applyAction(action)
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
				val a1 = Action(Player.WHITE, state(4)(7).get, state(0)(7).get)
				val r1 = state.applyAction(a1)
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
				           |"turn":"WHITE"}""".stripMargin.replace("\n", "").trim
				val a2 = Action(Player.BLACK, r1(0)(7).get, r1(0)(8).get)
				val r2 = r1.applyAction(a2)
				assert(TablutSerializer.toJson(r2) == e2)
			}

			"remove a captured pawn" in{
				val start = """
				              |{"board":[
				              |	["EMPTY","EMPTY","EMPTY","BLACK","BLACK","EMPTY","EMPTY","EMPTY","EMPTY"],
				              |	["EMPTY","EMPTY","EMPTY","EMPTY","BLACK","EMPTY","EMPTY","EMPTY","EMPTY"],
				              |	["EMPTY","EMPTY","EMPTY","EMPTY","WHITE","EMPTY","EMPTY","EMPTY","EMPTY"],
				              |	["BLACK","EMPTY","EMPTY","EMPTY","WHITE","BLACK","EMPTY","EMPTY","BLACK"],
				              |	["BLACK","BLACK","WHITE","WHITE","KING","WHITE","WHITE","BLACK","BLACK"],
				              |	["BLACK","EMPTY","EMPTY","EMPTY","WHITE","EMPTY","EMPTY","EMPTY","BLACK"],
				              |	["EMPTY","EMPTY","EMPTY","EMPTY","WHITE","EMPTY","EMPTY","EMPTY","EMPTY"],
				              |	["EMPTY","EMPTY","EMPTY","EMPTY","BLACK","EMPTY","EMPTY","EMPTY","EMPTY"],
				              |	["EMPTY","EMPTY","EMPTY","BLACK","BLACK","BLACK","EMPTY","EMPTY","EMPTY"]
				              |	],
				              |	"turn":"WHITE"}""".stripMargin.replace("\n", "").trim
				val startState = TablutSerializer.fromJson(start, factory)

				val expected = """
				              |{"board":[
				              |["EMPTY","EMPTY","EMPTY","BLACK","BLACK","EMPTY","EMPTY","EMPTY","EMPTY"],
				              |["EMPTY","EMPTY","EMPTY","EMPTY","BLACK","EMPTY","EMPTY","EMPTY","EMPTY"],
				              |["EMPTY","EMPTY","EMPTY","EMPTY","WHITE","EMPTY","EMPTY","EMPTY","EMPTY"],
				              |["BLACK","EMPTY","EMPTY","EMPTY","WHITE","EMPTY","WHITE","EMPTY","BLACK"],
				              |["BLACK","BLACK","WHITE","WHITE","KING","WHITE","EMPTY","BLACK","BLACK"],
				              |["BLACK","EMPTY","EMPTY","EMPTY","WHITE","EMPTY","EMPTY","EMPTY","BLACK"],
				              |["EMPTY","EMPTY","EMPTY","EMPTY","WHITE","EMPTY","EMPTY","EMPTY","EMPTY"],
				              |["EMPTY","EMPTY","EMPTY","EMPTY","BLACK","EMPTY","EMPTY","EMPTY","EMPTY"],
				              |["EMPTY","EMPTY","EMPTY","BLACK","BLACK","BLACK","EMPTY","EMPTY","EMPTY"]
				              |],
				              |"turn":"BLACK"}""".stripMargin.replace("\n", "").trim

				val action = Action(Player.WHITE, state(4)(6).get, state(3)(6).get)
				val result = startState.applyAction(action)
				assert(TablutSerializer.toJson(result) == expected)


			}

			"remove a captured pawn against walls" in{
				val initState = factory.createInitialState()
				val nearCastleEating = initState.transform(Map(
					(4,2) ->  CellContent.EMPTY
				))
				val expectedResult = nearCastleEating.transform(Map(
					(4,1) -> CellContent.EMPTY,
					(4,2) -> CellContent.BLACK,
					(4,3) -> CellContent.EMPTY
				)).nextPlayer

				val action = Action(Player.BLACK, nearCastleEating(4)(1).get, nearCastleEating(4)(2).get)
				assert(nearCastleEating.applyAction(action) == expectedResult)

				val nearCampEating = initState.transform(Map(
					(4,2) ->  CellContent.BLACK,
					(4,3) ->  CellContent.EMPTY,
					(3,3) ->  CellContent.WHITE,
				))
				val expectedResult2 = nearCampEating.transform(Map(
					(4,2) -> CellContent.EMPTY,
					(4,3) -> CellContent.WHITE,
					(3,3) -> CellContent.EMPTY,
				)).nextPlayer

				val action2 = Action(Player.WHITE, nearCampEating(3)(3).get, nearCastleEating(4)(3).get)
				assert(nearCampEating.applyAction(action2) == expectedResult2)
			}

			"getCells with coordinates" in{
				val cells = state.get((2,4),(3,4),(15,15))(factory.context)

				assert(cells.size == 2)
				assert(cells.contains(state(2)(4).get))
				assert(cells.contains(state(3)(4).get))
			}

			"clearCell with coordinates" in{
				val initState = factory.createInitialState()
				val newBoard = initState.clearCells((2,4),(3,4),(4,5))
				assert(newBoard(2)(4).exists(c=>c.cellContent == CellContent.EMPTY))
				assert(newBoard(3)(4).exists(c=>c.cellContent == CellContent.EMPTY))
				assert(newBoard(4)(5).exists(c=>c.cellContent == CellContent.EMPTY))
			}

			"transform with coordinates and content" in {
				val initState = factory.createInitialState()
				val newBoard = initState.transform(Map(
					(4,4) -> CellContent.EMPTY,
					(2,4) -> CellContent.KING
					)
				)

				assert(newBoard(4)(4).exists(c=>c.cellContent == CellContent.EMPTY))
				assert(newBoard(2)(4).exists(c=>c.cellContent == CellContent.KING))
			}

			"king coords tracking" in {
				val initState = factory.createInitialState()
				val newBoard = initState.transform(Map(
					(4,3) -> CellContent.EMPTY,
					(4,2) -> CellContent.EMPTY
				))

				val after = newBoard.applyAction(Action(Player.WHITE, newBoard(4)(4).get, newBoard(4)(2).get))
				assert(after(4)(2).exists(c => c.cellContent == CellContent.KING))
				assert(after.findKing.exists(c => c.coords == (4,2)))
			}
		}
	}
}
