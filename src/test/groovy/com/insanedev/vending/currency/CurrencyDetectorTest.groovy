package com.insanedev.vending.currency

import spock.lang.Specification

class CurrencyDetectorTest extends Specification {

	CurrencyDetector detector = null;

	def setup() {
		detector = new CurrencyDetector();
	}

	def "analyzeCoin When a Quarter Is Inserted It Is Recognized"() {
		setup:

		when:
		CoinType type = detector.analyzeCoin(5.67, 24.26, 1.75)

		then:
		assert type == CoinType.QUARTER
	}

	def "analyzeCoin when a dime is inserted it is recognized"() {
		setup:

		when:
		CoinType type = detector.analyzeCoin(2.268, 17.91, 1.35)

		then:
		assert type == CoinType.DIME
	}

	def "analyzeCoin when a nickel is inserted it is recognized"() {
		when:
		CoinType type = detector.analyzeCoin(5, 21.21, 1.95)

		then:
		assert type == CoinType.NICKEL
	}

	def "analyzeCoin when a penny is inserted it is not recognized"() {
		when:
		CoinType type = detector.analyzeCoin(2.500, 19.05, 1.52)

		then:
		assert type == null
	}

	def "analyzeCoin has a default 5% tolerance on the specifications of quarters"() {
		when:
		CoinType closeType = detector.analyzeCoin(CoinType.QUARTER.weight * 0.95, CoinType.QUARTER.diameter * 0.95, CoinType.QUARTER.thickness * 0.95)
		CoinType tooFarType = detector.analyzeCoin(CoinType.QUARTER.weight * 0.94, CoinType.QUARTER.diameter * 0.94, CoinType.QUARTER.thickness * 0.94)

		then:
		assert closeType == CoinType.QUARTER
		assert tooFarType == null
	}

	def "analyzeCoin can be configured to have a 0% tolerance on the specifications of quarters"() {
		setup:
		detector.setTolerance(0)

		when:
		CoinType closeType = detector.analyzeCoin(CoinType.QUARTER.weight, CoinType.QUARTER.diameter, CoinType.QUARTER.thickness)
		CoinType tooFarType = detector.analyzeCoin(CoinType.QUARTER.weight * 0.99, CoinType.QUARTER.diameter * 0.99, CoinType.QUARTER.thickness * 0.99)

		then:
		assert closeType == CoinType.QUARTER
		assert tooFarType == null
	}
}
