package ai.tablut.state.test

import ai.tablut.state.{GameContext, StateFacade}
import org.scalatest.{FlatSpec, WordSpec}

class BoardCellTest extends WordSpec{


	"BoardCell" when {
		"using normal game rules" should{
			val factory = StateFacade.normalStateFactory()

			 "create human coordinates correctly" in{
				val cell = factory.createBoardCell(
					(4,3),
					GameContext.CellType.NOTHING,
					GameContext.CellContent.WHITE
				)

				 assert(cell.getHumanCoords == "e4")

				 val cell2 = factory.createBoardCell(
					 (6,6),
					 GameContext.CellType.NOTHING,
					 GameContext.CellContent.EMPTY
				 )

				 assert(cell2.getHumanCoords == "g7")
			}
		}
	}
}
