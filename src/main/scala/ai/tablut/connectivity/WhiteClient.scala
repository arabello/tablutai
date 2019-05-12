package ai.tablut.connectivity

import ai.tablut.state.Player

private class WhiteClient(override val teamName: String, override val serverIp: String, override val port: Int) extends Client(teamName, serverIp, port, Player.WHITE)
