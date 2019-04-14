package ai.tablut.state.normal

import ai.tablut.state.{GameRules, State, StateFactory}

object NormalStateFactory extends StateFactory{
	override val gameRules: GameRules = GameRules(9, 9, ???, ???)

	override def createInitialState(): State = ???
}
