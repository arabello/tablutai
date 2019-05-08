package ai.tablut.adversarial.test

import ai.tablut.adversarial.TablutGame
import ai.tablut.state.{CellContent, Player, StateFacade}
import org.scalatest.WordSpec

class TablutGameTest extends WordSpec{
	"TablutGame" when{
		"using normal game rules" should {
			val factory = StateFacade.normalStateFactory()
			val initState = factory.createInitialState()
			val game = new TablutGame(factory, initState)

			"getUtility white winner" in {
				val whiteWinner = initState.transform(Map(
					(4,4) -> CellContent.EMPTY,
					(0,1) -> CellContent.KING
				))

				assert(game.isTerminal(whiteWinner))
				assert(game.getUtility(whiteWinner, Player.WHITE) == 1f)
				assert(game.getUtility(whiteWinner, Player.BLACK) == 0f)
			}

			"getUtility black winner" in {
				val blackWinner = initState.transform(Map(
					(4,4) -> CellContent.EMPTY,
					(4,2) -> CellContent.KING,
					(3,2) -> CellContent.BLACK,
					(5,2) -> CellContent.BLACK
				)).nextPlayer

				assert(game.isTerminal(blackWinner))
				assert(game.getUtility(blackWinner, Player.WHITE) == 0f)
				assert(game.getUtility(blackWinner, Player.BLACK) == 1f)
			}

			"no terminal state" in{
				assert(!game.isTerminal(initState))
			}
		}
	}
}
