package ai.tablut.state

/**
  * Data structure to handle a player game move
  * @param who
  * @param from
  * @param to
  */
case class Action(who: Player.Value, from: BoardCell, to: BoardCell) extends GameRulesComplied{
	/**
	  * @return True if it meets the game rules. False otherwise.
	  */
	override def isGameRulesComplied(gameRules: GameContext): Boolean = ???
}
