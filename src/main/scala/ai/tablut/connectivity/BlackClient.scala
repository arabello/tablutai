package ai.tablut.connectivity

import java.util.Properties

import ai.tablut.state.Player

private class BlackClient(props: Properties) extends Client(props, Player.BLACK)
