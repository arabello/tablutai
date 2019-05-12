package ai.tablut

object LogInterceptor {
	private val debug: Boolean = Config.DEBUG

	def apply(block: =>Unit): Unit = if (debug) block
}
