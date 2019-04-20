package ai.tablut.adversarial.heuristic

import ai.tablut.state.StateFacade

object NormalGameHeuristicFactory{
	private val context = StateFacade.normalStateFactory().context

	def random(): HeuristicFunction = new HFRandom()

	def createBlockEscapePoints(): HeuristicFunction = new HFBlockEscapePoints(context)

	def createPawsMajority(): HeuristicFunction = new HFPawsMajority(context)
}