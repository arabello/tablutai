package ai.tablut.connectivity

import java.io.FileInputStream

import ai.tablut.state.Turn

private class BlackClient(configFile: FileInputStream) extends Client(configFile, Turn.BLACK)
