package ai.tablut.state

/**
  * Implemented or extending this interface means to be subordinated
  * to game rules which validation is specified by the implementation.
  */
trait GameRulesComplied {
	/**
	  * @return True if it meets the game rules. False otherwise.
	  */
	def isGameRulesComplied(gameRules: GameContext): Boolean
}