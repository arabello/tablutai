package ai.tablut.state

import ai.tablut.state.normal.NormalStateFactory

/**
  * Entry point for the state package
  */
object StateFacade {
	def normalStateFactory(): StateFactory = NormalStateFactory

	def classicStateFactory(): StateFactory = ???

	def bradubhStateFactory(): StateFactory = ???

	def modernStateFactory(): StateFactory = ???
}
