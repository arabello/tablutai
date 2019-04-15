package ai.tablut.state

trait Board extends GameRulesComplied {
	val cols: Int
	val rows: Int
	val grid: Seq[Seq[BoardCell]]

	def apply(x: Int)(y: Int): BoardCell = grid(x)(y)

	def applyAction(action: Action): Board
}
