package ai.tablut.connectivity

import java.io.FileInputStream

object ConnFactory {
	val configFile = new FileInputStream("config.properties")

	def createWhiteClient(): CompetitionClient = new WhiteClient(configFile)
	def createBlackClient(): CompetitionClient = new BlackClient(configFile)
}
