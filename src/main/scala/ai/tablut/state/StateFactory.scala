package ai.tablut.state

/**
  * Factory to create game state parts within game rules
  * depending by the implementation of this interface
  */
trait StateFactory {
	val gameRules: GameRules
	def createInitialState(): State
}
