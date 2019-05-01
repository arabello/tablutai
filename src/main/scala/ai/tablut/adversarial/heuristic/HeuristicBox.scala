package ai.tablut.adversarial.heuristic

import akka.actor.ActorRef

case class HeuristicBox(strategy: HeuristicStrategy, actor: ActorRef, weight: Int)
