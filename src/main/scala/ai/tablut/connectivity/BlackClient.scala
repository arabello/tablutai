package ai.tablut.connectivity

import java.util.Properties

import ai.tablut.state.Turn

private class BlackClient(props: Properties) extends Client(props, Turn.BLACK)
