package ai.tablut.connectivity

import java.io.FileInputStream

import ai.tablut.state.Player

private class BlackClient(configFile: FileInputStream) extends Client(configFile, Player.BLACK)
