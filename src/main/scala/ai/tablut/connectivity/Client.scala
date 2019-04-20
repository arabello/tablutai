package ai.tablut.connectivity
import java.io.{DataInputStream, DataOutputStream, FileInputStream}
import java.net.Socket
import java.util.Properties

import ai.tablut.serialization.TablutSerializer
import ai.tablut.state.Turn.Player
import ai.tablut.state.{Action, Turn}

private abstract class Client(configFile: FileInputStream, player: Player) extends CompetitionClient {
	lazy val props = {
		val conf = new Properties()
		conf.load(configFile)
		conf
	}
	val teamName = props.getProperty("TEAM_NAME")
	val serverIp = props.getProperty("SERVER_IP")
	val port = player match {
		case Turn.WHITE => props.getProperty("WHITE_PORT").toInt
		case Turn.BLACK => props.getProperty("BLACK_PORT").toInt
	}
	private val socket = new Socket(serverIp, port)

	val inStream = new DataInputStream(socket.getInputStream)
	val outStream = new DataOutputStream(socket.getOutputStream)

	override def readState(): String = StreamUtils.readString(inStream)

	override def writeAction(action: Action): Unit = StreamUtils.writeString(outStream, TablutSerializer.toJson(action))

	override def writeTeamName(): Unit = StreamUtils.writeString(outStream, teamName)
}
