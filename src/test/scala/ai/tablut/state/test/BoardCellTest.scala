package ai.tablut.state.test

import ai.tablut.state._
import ai.tablut.state.implicits._
import org.scalatest.WordSpec

class BoardCellTest extends WordSpec{


	"BoardCell" when {
		"using normal game rules" should{
			val factory = StateFacade.normalStateFactory()

			 "create human coordinates correctly" in{
				val cell = factory.createBoardCell(
					(3,4),
					CellContent.WHITE
				).get

				 assert(cell.toHumanCoords == "e4")

				 val cell2 = factory.createBoardCell(
					 (6,6),
					 CellContent.EMPTY
				 ).get

				 assert(cell2.toHumanCoords == "g7")
			}

			"recognize its type" in {
				val cell = factory.createBoardCell((3,4), CellContent.WHITE).get
				assert(cell.cellType == CellType.NOTHING)

				val castle = factory.createBoardCell((4,4), CellContent.KING).get
				assert(castle.cellType == CellType.CASTLE)

				assert(factory.createBoardCell((4,4), CellContent.WHITE).orNull == null)

				val camp = factory.createBoardCell((0,4), CellContent.BLACK).get
				assert(camp.cellType == CellType.CAMP)

				val escape = factory.createBoardCell((0,1), CellContent.BLACK).get
				assert(escape.cellType == CellType.ESCAPE_POINT)
			}

			"has an extractor" in{
				val cell = BoardCell((3,4), CellType.NOTHING, CellContent.WHITE)
				cell match {
					case BoardCell(coords, cellType, cellContent) =>
						assert(coords == (3,4))
						assert(cellType == CellType.NOTHING)
						assert(cellContent == CellContent.WHITE)
					case _ => fail()
				}
			}

			"synthesize segments" in{
				implicit val state: State = factory.createInitialState()
				val start1 = state(2)(4).get
				val end1 = state(0)(4).get

				val b1 = start1 to end1

				assert(b1.head == start1)
				assert(b1.size == 3)
				assert(b1.contains(start1))
				assert(b1.contains(end1))

				val b2 = start1 until end1

				assert(b2.head == start1)
				assert(b2.size == 2)
				assert(b2.contains(start1))
				assert(!b2.contains(end1))

				val start2 = state(4)(6).get
				val end2 = state(4)(0).get

				val b3 = start2 to end2

				assert(b3.head == start2)
				assert(b3.size == 7)
				assert(b3.contains(state(4)(3).get))

				val b4 = start2 until end2

				assert(b3.head == start2)
				assert(b4.size == 6)
				assert(b4.contains(start2))
				assert(!b4.contains(end2))

				val start3 = state(1)(4).get
				val end3 = state(7)(4).get

				val b5 = start3 to end3

				assert(b5.head == start3)
				assert(b5.size == 7)
				assert(b5.contains(state(5)(4).get))

				val b6 = start3 until end3

				assert(b6.head == start3)
				assert(b6.size == 6)
				assert(b6.contains(start3))
				assert(!b6.contains(end3))
			}
		}
	}
}
