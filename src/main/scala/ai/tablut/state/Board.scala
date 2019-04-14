package ai.tablut.state

trait Board[T] extends GameRulesComplied {
	val rows: Int
	val cols: Int

	val grid: Array[Array[T]]

	def applyAction(action: Action): Board[T]
}
