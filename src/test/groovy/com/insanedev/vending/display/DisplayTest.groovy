package com.insanedev.vending.display

import com.insanedev.vending.currency.CoinType
import com.insanedev.vending.currency.CurrencyDetector
import com.insanedev.vending.product.Product
import spock.lang.Specification

class DisplayTest extends Specification {

	Display display = null;

	def setup() {
		display = new Display()
		display.detector = Mock(CurrencyDetector)
		display.detector.analyzeCoin(_, _, _) >> CoinType.QUARTER
		display.addProduct("A", new Product(name: "Cola", price: 1.00))
	}

	def "insertCoin when a coin is detected, its value is added to the current balance"() {
		when:
		display.insertCoin(CoinType.QUARTER.weight, CoinType.QUARTER.diameter, CoinType.QUARTER.thickness)

		then:
		assert display.balance == CoinType.QUARTER.value
		assert display.display == "0.25"
		assert display.balance == 0.25
		assert display.coinReturnCount == 0
	}

	def "insertCoin when a coin is not detected, no value is added to the current balance"() {
		when:
		display.insertCoin(CoinType.QUARTER.weight, CoinType.QUARTER.diameter, CoinType.QUARTER.thickness)
		display.insertCoin(0, 0, 0)

		then:
		display.detector.analyzeCoin(_, _, _) >>> [CoinType.QUARTER, null]
		assert display.balance == CoinType.QUARTER.value
		assert display.display == 'INSERT COIN'
		assert display.display == '0.25'
		assert display.coinReturnCount == 1
	}

	def "display defaults to 'INSERT COIN' on the display"() {
		when:
		display

		then:
		assert display.display == 'INSERT COIN'
	}

	def "display shows price of cola when selected and no money has been inserted"() {
		when:
		display.selectProduct("A")

		then:
		display.display == 'PRICE $1.00'
		display.display == 'INSERT COIN'
	}

	def "if the price of the product selected equals the balance, dispense the product"() {
		setup:
		addQuarter()
		addQuarter()
		addQuarter()
		addQuarter()

		when:
		display.selectProduct("A")

		then:
		display.display == 'THANK YOU'
		display.display == 'INSERT COIN'
		display.balance == 0
		display.dispensedProducts.size == 1
		display.dispensedProducts[0].name == 'Cola'
	}

	void addQuarter() {
		display.insertCoin(CoinType.QUARTER.weight, CoinType.QUARTER.diameter, CoinType.QUARTER.thickness)
	}
}
