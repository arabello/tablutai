package ai.tablut.adversarial.heuristic

private object Normalizer {
	def createNormalizer(min: Double, max: Double): Double => Double = (value: Double) => {
		val res = (value - min) / (max - min)
		if (res < 0) 0 else if (res > 1) 1 else res
	}
}
