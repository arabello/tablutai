package ai.tablut.state

import ai.tablut.state.Player.Player

object CellContent extends Enumeration {
	type CellContent = Value
	@transient val EMPTY, WHITE, BLACK, KING = Value

	implicit class Converter(value: Player){
		def toCellContent: CellContent = if (value == Player.WHITE) WHITE else BLACK
	}
}
