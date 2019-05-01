package ai.tablut.adversarial.heuristic

import akka.actor.Props

object HeuristicActorImpl{
	def props(strategy: HeuristicStrategy) = Props(new HeuristicActorImpl(strategy))
}

class HeuristicActorImpl(val strategy: HeuristicStrategy) extends HeuristicActor{
	override def receive: Receive = {
		case msg: Message => sender ! strategy.eval(msg.state, msg.player)
		case _ => System.err.println("Unexpected message")
	}
}
