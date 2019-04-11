package ai.tablut.serialization

import java.lang.reflect.Type

import ai.tablut.state.Action
import com.google.gson._

class ActionJsonAdapter extends JsonSerializer[Action] with JsonDeserializer[Action]{
	override def serialize(src: Action, typeOfSrc: Type, context: JsonSerializationContext): JsonElement = ???

	override def deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): Action = ???
}

/*
    {
        "from":"e4",
        "to":"f4",
        "turn":"WHITE"
    }
 */