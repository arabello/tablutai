package ai.tablut.state

trait State {
	val board: Board[BoardCell]
	val turn: Player.Value
}
