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
}
