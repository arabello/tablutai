package ai.tablut

import java.util.Properties

object LogInterceptor {
	private var debug: Boolean = false

	def init(properties: Properties): Unit = {
		debug = properties.getProperty("DEBUG","false").toBoolean
	}

	def apply(block: =>Unit): Unit = if (debug) block
}
