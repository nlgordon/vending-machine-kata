package com.insanedev.vending.display

import com.insanedev.vending.currency.CoinType
import com.insanedev.vending.currency.CurrencyDetector
import spock.lang.Specification

class DisplayTest extends Specification {

	Display display = null;

	def setup() {
		display = new Display()
		display.detector = Mock(CurrencyDetector)
	}

	def "insertCoin when a coin is detected, its value is added to the current balance"() {
		setup:
		display.detector.analyzeCoin(_, _, _) >> CoinType.QUARTER

		when:
		display.insertCoin(CoinType.QUARTER.weight, CoinType.QUARTER.diameter, CoinType.QUARTER.thickness)

		then:
		assert display.balance == CoinType.QUARTER.value
	}

	def "insertCoin when a coin is not detected, no value is added to the current balance"() {
		setup:
		display.detector.analyzeCoin(_, _, _) >>> [CoinType.QUARTER, null]

		when:
		display.insertCoin(CoinType.QUARTER.weight, CoinType.QUARTER.diameter, CoinType.QUARTER.thickness)
		display.insertCoin(0, 0, 0)

		then:
		assert display.balance == CoinType.QUARTER.value
	}
}
