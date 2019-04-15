package ai.tablut.state

trait State {
	val board: Board
	val turn: Player.Value
}