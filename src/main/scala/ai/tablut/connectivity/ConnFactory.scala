package ai.tablut.connectivity

class ConnFactory(teamName: String, serverIp: String) {
	def createWhiteClient(port: Int): CompetitionClient = new WhiteClient(teamName, serverIp, port)
	def createBlackClient(port: Int): CompetitionClient = new BlackClient(teamName, serverIp, port)
}
