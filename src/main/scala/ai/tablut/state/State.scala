package ai.tablut.state

import ai.tablut.state.Player.Player

case class State(board: Board, turn: Player){
	override def toString: String = s"$board\nturn: $turn"
}
