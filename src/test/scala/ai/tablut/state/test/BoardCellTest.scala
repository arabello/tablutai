package ai.tablut.state.test

import ai.tablut.state._
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

			"pattern match over InvalidBoardCell" in{
				val camp = factory.createBoardCell((0, 4), CellContent.WHITE).orNull
				assert(camp == null)
			}
		}
	}
}
