package ai.tablut.state

case class Action(who: Player.Value, from: BoardCell, to: BoardCell) extends GameRulesComplied{
	/**
	  * @return True if it meets the game rules. False otherwise.
	  */
	override def isGameRulesComplied(gameRules: GameRules): Boolean = ???
}
