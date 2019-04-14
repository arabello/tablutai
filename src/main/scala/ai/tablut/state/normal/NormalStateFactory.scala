package ai.tablut.state.normal

import ai.tablut.state.{GameContext, _}

object NormalStateFactory extends StateFactory{
	override val gameRules: GameContext = GameContext(9, 9,
		(4,4),  // Throne
		Set(    // camps
			(3,0), (4,0), (5,0), (4,1),
			(3,8), (4,8), (5,8), (4,7),
			(0,3), (0,4), (0,5), (1,4),
			(8,3), (8,4), (8,5), (8,4)
		),
		Set(    // camps
			(1,0), (2,0), (6,0), (7,0),
			(1,8), (2,8), (6,8), (7,8),
			(0,1), (0,2), (0,6), (0,7),
			(8,1), (8,2), (8,6), (8,7)
		)
	)

	override def createInitialState(): State = ???

	/**
	  * Create a new cell not bounded to a [[ai.tablut.state.Board]]
	  *
	  * @param coords Position of a cell. First value as column index, second value as the row index
	  * @param cellType
	  * @param cellContent
	  * @return A new instance
	  */
	override def createBoardCell(coords: (Int, Int), cellType: GameContext.CellType.Value, cellContent: GameContext.CellContent.Value): BoardCell = BoardCellImpl(coords, cellType, cellContent)
}
