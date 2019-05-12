package ai.tablut.connectivity

import ai.tablut.state.Player

private class BlackClient(override val teamName: String, override val serverIp: String, override val port: Int) extends Client(teamName, serverIp, port, Player.BLACK)
