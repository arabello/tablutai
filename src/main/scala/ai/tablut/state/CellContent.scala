package ai.tablut.state

object CellContent extends Enumeration {
	type CellContent = Value
	@transient val EMPTY, WHITE, BLACK, KING = Value
}
