package ai.tablut.state

trait Board[T] extends GameRulesComplied {
	val rows: Int
	val cols: Int

	lazy val grid: Array[Array[T]] = Array.ofDim[T](rows, cols)

	def applyAction(action: Action): Board[T]
}
