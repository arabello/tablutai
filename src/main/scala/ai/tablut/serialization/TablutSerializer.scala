package ai.tablut.serialization

import ai.tablut.state.{Action, State}
import play.api.libs.json.{JsObject, Json}

object TablutSerializer {

	def toJson(action: Action): String = Json.obj(
		"from" -> action.from.toHumanCoords,
		"to" -> action.to.toHumanCoords,
		"turn" -> action.who.toString
	).toString()

	def toJson(state: State): String = Json.obj(
		"board" -> Json.arr(

		),
		"turn" -> state.turn
	).toString()
}

/*
    {
        "from":"e4",
        "to":"f4",
        "turn":"WHITE"
    }
 */



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
