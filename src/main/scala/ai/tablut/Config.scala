package ai.tablut

import ai.tablut.state.Player._

case class Config (
  player: Option[Player] = None,
  teamName: String = "Pelle",
  serverIP: String = "127.0.0.1",
  whitePort: Int = 5800,
  blackPort: Int = 5801,
  maxCompTime: Int = 55,
  debug: Boolean = false,
  midPhaseTurn: Int = 3,
  endPhaseTurn: Int = 6
)
