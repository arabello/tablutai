package ai.tablut.state.test

import ai.tablut.serialization.TablutSerializer
import ai.tablut.state._
import org.scalatest.WordSpec

class BoardTest extends WordSpec{
	"Board" when {
		"using normal game rules" should{
			val factory = StateFacade.normalStateFactory()
			val state = factory.createInitialState

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
				val action = Action(Turn.WHITE, state.board(3)(4), state.board(3)(7))
				val result = state.copy(board = state.board.apply(action))
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
				val a1 = Action(Turn.BLACK, state.board(4)(7), state.board(0)(7))
				val r1 = state.copy(board = state.board.apply(a1), turn = Turn.BLACK)
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
				val a2 = Action(Turn.BLACK, r1.board(0)(7), r1.board(0)(8))
				val r2 = r1.copy(board = r1.board.apply(a2), turn = Turn.BLACK)
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
				              |"turn":"WHITE"}""".stripMargin.replace("\n", "").trim

				val action = Action(Turn.WHITE, state.board(4)(6), state.board(3)(6))
				val result = state.copy(board = startState.board.apply(action))
				assert(TablutSerializer.toJson(result) == expected)
			}

			"getCells with coordinates" in{
				val cells = state.board.get((2,4),(3,4),(15,15))(factory.context)

				assert(cells.size == 2)
				assert(cells.contains(state.board(2)(4)))
				assert(cells.contains(state.board(3)(4)))
			}

			"clearCell with coordinates" in{
				val initState = factory.createInitialState()
				val newBoard = initState.board.clearCells((2,4),(3,4),(4,5))
				assert(newBoard(2)(4).cellContent == CellContent.EMPTY)
				assert(newBoard(3)(4).cellContent == CellContent.EMPTY)
				assert(newBoard(4)(5).cellContent == CellContent.EMPTY)
			}

			"transform with coordinates and content" in {
				val initState = factory.createInitialState()
				val newBoard = initState.board.transform(Map(
					(4,4) -> CellContent.EMPTY,
					(2,4) -> CellContent.KING
					)
				)

				assert(newBoard(4)(4).cellContent == CellContent.EMPTY)
				assert(newBoard(2)(4).cellContent == CellContent.KING)
			}
		}
	}
}
