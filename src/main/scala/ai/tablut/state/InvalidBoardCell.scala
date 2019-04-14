package ai.tablut.state

/**
  * Data structure to maintain a requested board cell creation that does not
  * comply to the game rules. It can be invalid either for coords outbounds or
  * illegal type-content tuple
  * @param coords
  * @param cellType
  * @param cellContent
  */
private case class InvalidBoardCell(coords: (Int, Int),
                            cellType: CellType.Value,
                            cellContent: CellContent.Value) extends BoardCell {

	override def getHumanCoords: String = "invalid board cell"

	override def isGameRulesComplied(gameRules: GameContext): Boolean = false
}
