package ai.tablut.connectivity

import ai.tablut.state.Action
import ai.tablut.state.Player.Player

trait CompetitionClient {
	val player: Player
	def readState(): String
	def writeAction(action: Action)
	def writeTeamName()
}
