package ai.tablut.adversarial.heuristic

import akka.actor.Actor

trait HeuristicActor extends Actor{
	val strategy: HeuristicStrategy

	override def receive: Receive = {
		case msg: Message => sender ! strategy.eval(msg.state, msg.player)
		case _ => System.err.println("Unexpected message")
	}
}
