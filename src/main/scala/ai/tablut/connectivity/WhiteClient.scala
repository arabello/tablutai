package ai.tablut.connectivity

import java.io.FileInputStream

import ai.tablut.state.Player

private class WhiteClient(configFile: FileInputStream) extends Client(configFile, Player.WHITE)
