package ai.tablut.state

object Turn extends Enumeration {
	type Player = Value
	@transient val WHITE, BLACK, DRAW = Value
}
