package ai.tablut.connectivity

import java.util.Properties

import ai.tablut.state.Player

private class WhiteClient(props: Properties) extends Client(props, Player.WHITE)
