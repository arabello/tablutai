package ai.tablut.adversarial

import java.util

import ai.tablut.state.CellContent.{CellContent => _}
import ai.tablut.state._
import aima.core.search.adversarial.Game

import scala.collection.JavaConverters._

class TablutGame(val stateFactory: StateFactory, initialState: State) extends Game[State, Action, Turn.Value] {
	/**
	  * The initial state, which specifies how the game is set up at the start
	  */
	override def getInitialState: State = initialState

	/**
	  * The transition model, which defines the result of a move.
	  */
	override def getResult(state: State, action: Action): State = state.copy(board = state.board.apply(action))

	/**
	  * Defines which player has the move in a state.
	  */
	override def getPlayer(state: State): Turn.Value = state.turn

	override def getPlayers: Array[Turn.Value] = Turn.values.toArray

	override def getActions(state: State): util.List[Action] =
		state.board.grid.flatMap( row => row
				.filter(c => c.cellContent match {
					case CellContent.WHITE | CellContent.KING => state.turn == Turn.WHITE
					case CellContent.BLACK => state.turn == Turn.BLACK
					case _ => false
				}).flatMap(cell =>
					(for (x <- 0 until state.board.rows;
					      a = Action(state.turn, cell, state.board.grid(x)(cell.coords._2))
					      if a.validate(stateFactory.context, state.board))
						yield a)
						++
					(for (y <- 0 until state.board.cols;
					      a = Action(state.turn, cell, state.board.grid(cell.coords._1)(y))
					      if a.validate(stateFactory.context, state.board))
						yield a))
		).asJava

	/**
	  * A utility function (also called an objective function or
	  * payoff function), defines the final numeric value for a game that ends in
	  * terminal state s for a player p. In chess, the outcome is a win, loss, or
	  * draw, with values +1, 0, or 1/2 . Some games have a wider variety of possible
	  * outcomes; the payoffs in backgammon range from 0 to +192. A zero-sum game is
	  * (confusingly) defined as one where the total payoff to all players is the
	  * same for every instance of the game. Chess is zero-sum because every game has
	  * payoff of either 0 + 1, 1 + 0 or 1/2 + 1/2 . "Constant-sum" would have been a
	  * better term, but zero-sum is traditional and makes sense if you imagine each
	  * player is charged an entry fee of 1/2.
	  */
	override def getUtility(state: State, player: Turn.Value): Double = if (state.turn == Turn.DRAW) 0.5f else if (player == state.turn) 1f else 0f

	/**
	  * A terminal test (as goal test as in the informed), which is true when the game is over
	  * and false TERMINAL STATES otherwise.
	  * States where the game has ended are called terminal states
	  */
	override def isTerminal(state: State): Boolean =
		state.turn == Turn.DRAW || stateFactory.context.isWinner(state.copy(turn = Turn.WHITE)) || stateFactory.context.isWinner(state.copy(turn = Turn.BLACK))
}
