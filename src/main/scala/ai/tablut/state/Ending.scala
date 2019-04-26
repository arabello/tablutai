package ai.tablut.state

object Ending extends Enumeration {
	type Ending = Value
	@transient val DRAW, WHITEWIN, BLACKWIN = Value
}
