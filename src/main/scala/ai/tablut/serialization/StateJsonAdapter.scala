package ai.tablut.serialization

import java.lang.reflect.Type

import ai.tablut.state.State
import com.google.gson._

class StateJsonAdapter extends JsonSerializer[State] with JsonDeserializer[State]{
	override def serialize(src: State, typeOfSrc: Type, context: JsonSerializationContext): JsonElement = ???

	override def deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): State = ???
}

/*
{
    "board":[
        ["EMPTY","EMPTY","EMPTY","BLACK","BLACK","BLACK","EMPTY","EMPTY","EMPTY"],
        ["EMPTY","EMPTY","EMPTY","EMPTY","BLACK","EMPTY","EMPTY","EMPTY","EMPTY"],
        ["EMPTY","EMPTY","EMPTY","EMPTY","WHITE","EMPTY","EMPTY","EMPTY","EMPTY"],
        ["BLACK","EMPTY","EMPTY","EMPTY","WHITE","EMPTY","EMPTY","EMPTY","BLACK"],
        ["BLACK","BLACK","WHITE","WHITE","KING","WHITE","WHITE","BLACK","BLACK"],
        ["BLACK","EMPTY","EMPTY","EMPTY","WHITE","EMPTY","EMPTY","EMPTY","BLACK"],
        ["EMPTY","EMPTY","EMPTY","EMPTY","WHITE","EMPTY","EMPTY","EMPTY","EMPTY"],
        ["EMPTY","EMPTY","EMPTY","EMPTY","BLACK","EMPTY","EMPTY","EMPTY","EMPTY"],
        ["EMPTY","EMPTY","EMPTY","BLACK","BLACK","BLACK","EMPTY","EMPTY","EMPTY"]
        ],
    "turn":"WHITE"}

 */