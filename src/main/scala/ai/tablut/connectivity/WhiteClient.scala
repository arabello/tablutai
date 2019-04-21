package ai.tablut.connectivity

import java.util.Properties

import ai.tablut.state.Turn

private class WhiteClient(props: Properties) extends Client(props, Turn.WHITE)
