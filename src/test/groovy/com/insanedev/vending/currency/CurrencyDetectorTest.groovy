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
}
