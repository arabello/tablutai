package ai.tablut.state

import ai.tablut.state.Turn.Turn

object CellContent extends Enumeration {
	type CellContent = Value
	@transient val EMPTY, WHITE, BLACK, KING, THRONE = Value

	implicit class Converter(value: Turn){
		def toCellContent: CellContent = if (value == Turn.WHITE) WHITE else BLACK
	}
}
