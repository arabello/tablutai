package ai.tablut.state.normal

import ai.tablut.state.{GameContext, State}

private object NormalGameContext extends GameContext{

	override val nRows: Int = 9

	override val nCols: Int = 9

	override val throne: (Int, Int) = (4, 4)

	override val camps: Set[(Int, Int)] = Set(
		(3,0), (4,0), (5,0), (4,1),
		(3,8), (4,8), (5,8), (4,7),
		(0,3), (0,4), (0,5), (1,4),
		(8,3), (8,4), (8,5), (8,4)
	)

	override val escapePoints: Set[(Int, Int)] = Set(
		(1,0), (2,0), (6,0), (7,0),
		(1,8), (2,8), (6,8), (7,8),
		(0,1), (0,2), (0,6), (0,7),
		(8,1), (8,2), (8,6), (8,7)
	)

	override def isWinner(state: State): Boolean = ???
}
