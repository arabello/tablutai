package ai.tablut.serialization

import ai.tablut.state.{Action, State}
import com.google.gson.GsonBuilder

object TablutSerializer {
	val gson = {
		val builder = new GsonBuilder()
		builder.registerTypeAdapter(Action.getClass, new ActionJsonAdapter())
		builder.registerTypeAdapter(State.getClass, new StateJsonAdapter())
	}
}
