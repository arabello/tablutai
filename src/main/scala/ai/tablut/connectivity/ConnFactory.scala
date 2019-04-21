package ai.tablut.connectivity

import java.util.Properties

class ConnFactory(props: Properties) {
	def createWhiteClient(): CompetitionClient = new WhiteClient(props)
	def createBlackClient(): CompetitionClient = new BlackClient(props)
}
