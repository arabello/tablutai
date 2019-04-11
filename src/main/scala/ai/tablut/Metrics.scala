package ai.tablut

object Metrics {
	def printMillis(msg: String, block: () => Unit): Unit = {
		val current = System.currentTimeMillis()
		block()
		println(s"[metrics : printMillis] $msg ${System.currentTimeMillis() - current}")
	}
}
