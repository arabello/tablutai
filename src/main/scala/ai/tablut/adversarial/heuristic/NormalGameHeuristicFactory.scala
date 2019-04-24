package ai.tablut.adversarial.heuristic

import ai.tablut.state.StateFacade

object NormalGameHeuristicFactory{
	private val stateFactory = StateFacade.normalStateFactory()
	private val context = stateFactory.context

	def random(): HeuristicFunction = new HFRandom()

	def createBlockEscapePoints(): HeuristicFunction = new HFBlockEscapePoints(context)

	def createPawsMajority(): HeuristicFunction = new HFPawsMajority(context)

	def createKingAssasination(): HeuristicFunction = new HFNearKing(stateFactory)
}