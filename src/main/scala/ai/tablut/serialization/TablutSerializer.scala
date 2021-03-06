package ai.tablut.serialization

import ai.tablut.state.CellContent.CellContent
import ai.tablut.state._
import play.api.libs.json.Json

object TablutSerializer {

	def toJson(action: Action): String = Json.obj(
		"from" -> action.from.toHumanCoords,
		"to" -> action.to.toHumanCoords,
		"turn" -> action.who.toString
	).toString()

	def toJson(state: State): String = Json.obj(
			"board" -> state.board.map(row => row.map(c => c.cellContent.toString)),
			"turn" -> state.turn
		).toString()

	def fromJson(json: String, factory: StateFactory): State = {
		val obj = Json.parse(json)
		val contentRows: Seq[Seq[CellContent]] = obj("board").as[Seq[Seq[String]]].map(row => row.map(value => CellContent.withName(value)))

		try {
			val player = Player.withName(obj("turn").as[String])
			factory.createState(contentRows, player)
		}catch {
			case _: Throwable =>
				val ending = Some(Ending.withName(obj("turn").as[String]))
				factory.createState(contentRows, Player.WHITE, ending)
		}
	}
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
