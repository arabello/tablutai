package ai.tablut.adversarial.test

import ai.tablut.adversarial.TablutGame
import ai.tablut.state.{Action, CellContent, Player, StateFacade}
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
				val state = initState.transform(Map(
					(4,4) -> CellContent.EMPTY,
					(4,3) -> CellContent.EMPTY,
					(4,2) -> CellContent.KING
				)).nextPlayer

				val blackWinner = state.applyAction(Action(Player.BLACK, state(8,3).get, state(4,3).get))
				assert(game.isTerminal(blackWinner))
				assert(game.getUtility(blackWinner, Player.WHITE) == 0f)
				assert(game.getUtility(blackWinner, Player.BLACK) == 1f)
			}

			"terminal state" in{
				val state = initState.transform(Map(
					(4,4) -> CellContent.EMPTY,
					(4,2) -> CellContent.KING
				))
				val whiteWin = state.applyAction(Action(Player.WHITE, state(4,2).get, state(8,2).get))
				assert(game.isTerminal(whiteWin))

				val state2 = initState.transform(Map(
					(4,4) -> CellContent.EMPTY,
					(4,2) -> CellContent.KING,
					(4,3) -> CellContent.EMPTY
				)).nextPlayer

				val blackWin = state2.applyAction(Action(Player.BLACK, state(8,3).get, state(4,3).get))
				assert(game.isTerminal(blackWin))
			}

			"no terminal state" in{
				assert(!game.isTerminal(initState))
			}
		}
	}
}
