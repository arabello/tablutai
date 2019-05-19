package ai.tablut.adversarial.heuristic.test

import ai.tablut.adversarial.heuristic.{HeuristicFactory, KingPathStrategy, Phase}
import ai.tablut.state.Player
import ai.tablut.state.normal.NormalStateFactory
import org.scalatest.FlatSpec

class HeuristicFactoryTest extends FlatSpec{
	val gameContext = NormalStateFactory.context
	val phase = Phase.MID

	"HeuristicFactory" should "be dependent on depth" in{
		val c10 = new HeuristicFactory(10)
		val c20 = new HeuristicFactory(20)
		val c30 = new HeuristicFactory(30)
		val c40 = new HeuristicFactory(40)
		val c50 = new HeuristicFactory(50)
		val c60 = new HeuristicFactory(60)

		assert(!c10
			.createHeuristicFunction(gameContext, Player.WHITE, phase, 1)
			.strategies.exists(h => h._1.isInstanceOf[KingPathStrategy]))

		assert(c10
				.createHeuristicFunction(gameContext, Player.WHITE, phase, 3)
				.strategies.exists(h => h._1.isInstanceOf[KingPathStrategy]))

		assert(!c20
			.createHeuristicFunction(gameContext, Player.WHITE, phase, 3)
			.strategies.exists(h => h._1.isInstanceOf[KingPathStrategy]))

		assert(c20
			.createHeuristicFunction(gameContext, Player.WHITE, phase, 4)
			.strategies.exists(h => h._1.isInstanceOf[KingPathStrategy]))

		assert(!c30
			.createHeuristicFunction(gameContext, Player.WHITE, phase, 3)
			.strategies.exists(h => h._1.isInstanceOf[KingPathStrategy]))

		assert(c30
			.createHeuristicFunction(gameContext, Player.WHITE, phase, 4)
			.strategies.exists(h => h._1.isInstanceOf[KingPathStrategy]))

		assert(!c40
			.createHeuristicFunction(gameContext, Player.WHITE, phase, 4)
			.strategies.exists(h => h._1.isInstanceOf[KingPathStrategy]))

		assert(c40
			.createHeuristicFunction(gameContext, Player.WHITE, phase, 5)
			.strategies.exists(h => h._1.isInstanceOf[KingPathStrategy]))

		assert(!c50
			.createHeuristicFunction(gameContext, Player.WHITE, phase, 4)
			.strategies.exists(h => h._1.isInstanceOf[KingPathStrategy]))

		assert(c50
			.createHeuristicFunction(gameContext, Player.WHITE, phase, 5)
			.strategies.exists(h => h._1.isInstanceOf[KingPathStrategy]))

		assert(!c60
			.createHeuristicFunction(gameContext, Player.WHITE, phase, 4)
			.strategies.exists(h => h._1.isInstanceOf[KingPathStrategy]))

		assert(c60
			.createHeuristicFunction(gameContext, Player.WHITE, phase, 5)
			.strategies.exists(h => h._1.isInstanceOf[KingPathStrategy]))
	}
}
