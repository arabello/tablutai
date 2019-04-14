package ai.tablut.state

object Player extends Enumeration {
	type Player = Value
	@transient val WHITE, BLACK = Value
}
