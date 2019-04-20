package ai.tablut.adversarial.heuristic

import ai.tablut.state.GameContext

class HFFactory(context: GameContext) {
	def random: HeuristicFunction = HFRandom

	def createBlockEscapePoints: HeuristicFunction = new HFBlockEscapePoints(context)
}