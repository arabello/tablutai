package ai.tablut.state

import ai.tablut.state.Player.Player

/**
  * Data structure to handle a player game move
 *
  * @param who
  * @param from
  * @param to
  */
case class Action(who: Player, from: BoardCell, to: BoardCell) extends GameRulesComplied{
	/**
	  * @return True if it meets the game rules. False otherwise.
	  */
	override def isGameRulesComplied(gameRules: GameContext): Boolean = ???
}
