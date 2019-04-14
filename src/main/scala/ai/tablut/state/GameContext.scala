package ai.tablut.state

case class GameContext(
	                    nRows: Int,
	                    nCols: Int,
	                    throne: (Int, Int),
	                    camps: Set[(Int, Int)],
	                    escapePoints: Set[(Int, Int)]){

	def isWinner(state: State): Boolean = ???
}

object GameContext{
	object CellType extends Enumeration {
		val NOTHING, CAMP, CASTLE, ESCAPE_POINT = Value
	}

	object CellContent extends Enumeration {
		val EMPTY, WHITE, BLACK, KING = Value
	}
}
