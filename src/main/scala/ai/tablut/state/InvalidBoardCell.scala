package ai.tablut.state

private case class InvalidBoardCell(coords: (Int, Int),
                            cellType: CellType.Value,
                            cellContent: CellContent.Value) extends BoardCell {

	override def getHumanCoords: String = "invalid type-content"

	override def isGameRulesComplied(gameRules: GameContext): Boolean = false
}
