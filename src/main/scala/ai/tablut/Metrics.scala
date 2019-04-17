package ai.tablut

object Metrics {
	def printMillis(msg: String)(block: => Any): Any = {
		val current = System.currentTimeMillis()
		val res = block
		println(s"[metrics : printMillis] $msg ${System.currentTimeMillis() - current}")
		res
	}
}
