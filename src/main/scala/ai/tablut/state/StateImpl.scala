package ai.tablut.state

import ai.tablut.state.Player.Player

private case class StateImpl(board: Board, turn: Player) extends State
