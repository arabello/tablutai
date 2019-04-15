package ai.tablut.connectivity

import ai.tablut.state.Action

trait CompetitionClient {
	def readState(): String
	def writeAction(action: Action)
	def writeTeamName()
}
