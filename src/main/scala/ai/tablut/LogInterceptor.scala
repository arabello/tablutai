package ai.tablut

object LogInterceptor {
	var debug: Boolean = false

	def apply(block: =>Unit): Unit = if (debug) block
}
