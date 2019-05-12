package ai.tablut.connectivity
import java.io.{DataInputStream, DataOutputStream}
import java.net.Socket

import ai.tablut.serialization.TablutSerializer
import ai.tablut.state.Action
import ai.tablut.state.Player.Player

private abstract class Client(val teamName: String, val serverIp: String, val port: Int, override val player: Player) extends CompetitionClient {
	private val socket = new Socket(serverIp, port)

	val inStream = new DataInputStream(socket.getInputStream)
	val outStream = new DataOutputStream(socket.getOutputStream)

	override def readState(): String = StreamUtils.readString(inStream)

	override def writeAction(action: Action): Unit = StreamUtils.writeString(outStream, TablutSerializer.toJson(action))

	override def writeTeamName(): Unit = StreamUtils.writeString(outStream, teamName)
}
