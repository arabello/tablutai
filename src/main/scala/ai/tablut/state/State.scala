package ai.tablut.state

import ai.tablut.state.Turn.Player

case class State(board: Board, turn: Player, isDraw: Boolean = false){
	override def toString: String = s"$board\nturn: $turn"
}
