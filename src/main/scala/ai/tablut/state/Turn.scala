package ai.tablut.state

object Turn extends Enumeration {
	type Turn = Value
	@transient val WHITE, BLACK, DRAW = Value
}
