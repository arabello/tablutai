package ai.tablut.state

trait BoardCell {
	val coords: (Int, Int)
	val cellType: GameRules.CellType.Value
	val cellContent: GameRules.CellContent.Value
}
