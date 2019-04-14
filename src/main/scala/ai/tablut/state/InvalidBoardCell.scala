package ai.tablut.state

import ai.tablut.state.CellContent.CellContent
import ai.tablut.state.CellType.CellType

/**
  * Data structure to maintain a requested board cell creation that does not
  * comply to the game rules. It can be invalid either for coords outbounds or
  * illegal type-content tuple
 *
  * @param coords
  * @param cellType
  * @param cellContent
  */
private case class InvalidBoardCell(coords: (Int, Int),
                            cellType: CellType,
                            cellContent: CellContent) extends BoardCell {

	override def toHumanCoords: String = "invalid board cell"

	override def isGameRulesComplied(gameRules: GameContext): Boolean = false
}
