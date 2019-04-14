package ai.tablut.state

object CellType extends Enumeration {
	type CellType = Value
	@transient val NOTHING, CAMP, CASTLE, ESCAPE_POINT = Value
}
