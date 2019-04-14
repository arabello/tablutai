package ai.tablut.state.test

import ai.tablut.state._
import org.scalatest.{FlatSpec, WordSpec}

class BoardCellTest extends WordSpec{


	"BoardCell" when {
		"using normal game rules" should{
			val factory = StateFacade.normalStateFactory()

			 "create human coordinates correctly" in{
				val cell = factory.createBoardCell(
					(4,3),
					CellContent.WHITE
				)

				 assert(cell.getHumanCoords == "e4")

				 val cell2 = factory.createBoardCell(
					 (6,6),
					 CellContent.EMPTY
				 )

				 assert(cell2.getHumanCoords == "g7")
			}

			"recognize its type" in {
				val cell = factory.createBoardCell((4,3), CellContent.WHITE)
				assert(cell.cellType == CellType.NOTHING)

				val castle = factory.createBoardCell((4,4), CellContent.WHITE)
				assert(castle.cellType == CellType.CASTLE)

				val camp = factory.createBoardCell((4,0), CellContent.BLACK)
				assert(camp.cellType == CellType.CAMP)

				val escape = factory.createBoardCell((1,0), CellContent.BLACK)
				assert(escape.cellType == CellType.ESCAPE_POINT)
			}
		}
	}
}
