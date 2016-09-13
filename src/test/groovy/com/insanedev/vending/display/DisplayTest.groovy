package com.insanedev.vending.display

import com.insanedev.vending.currency.CoinType
import com.insanedev.vending.currency.CurrencyDetector
import com.insanedev.vending.product.Product
import com.insanedev.vending.product.ProductInventoryManager
import spock.lang.Specification

class DisplayTest extends BaseDisplayTest {


	def setup() {
	}

	def "insertCoin when a coin is detected, its value is added to the current balance"() {
		when:
		display.insertCoin(CoinType.QUARTER.weight, CoinType.QUARTER.diameter, CoinType.QUARTER.thickness)

		then:
		display.balance == CoinType.QUARTER.value
		display.display == "0.25"
		display.balance == 0.25
		display.balanceCoins.size == 1
		display.balanceCoins[0] == CoinType.QUARTER
		display.coinReturn.size == 0
	}

	def "insertCoin when a coin is not detected, no value is added to the current balance"() {
		when:
		display.insertCoin(CoinType.QUARTER.weight, CoinType.QUARTER.diameter, CoinType.QUARTER.thickness)
		display.insertCoin(0, 0, 0)

		then:
		display.detector.analyzeCoin(_, _, _) >>> [CoinType.QUARTER, null]
		display.balance == CoinType.QUARTER.value
		display.display == 'INSERT COIN'
		display.display == '0.25'
		display.coinReturn.size == 1
		display.coinReturn[0] == null
	}

	def "display defaults to 'INSERT COIN' on the display"() {
		when:
		display

		then:
		display.display == 'INSERT COIN'
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
		display.balance = 1.0

		when:
		display.selectProduct("A")

		then:
		display.display == 'THANK YOU'
		display.display == 'INSERT COIN'
		display.balance == 0
		display.balanceCoins.size == 0
		display.dispensedProducts.size == 1
		display.dispensedProducts[0].name == 'Cola'
	}

	def "after a product is dispensed that cost less than the balance, make change into the coin return"() {
		setup:
		display.balance = 1.05

		when:
		display.selectProduct("C")

		then:
		display.balance == 0
		display.balanceCoins.size == 0
		display.coinReturn.size == 3
		display.coinReturn[0] == CoinType.QUARTER
		display.coinReturn[1] == CoinType.DIME
		display.coinReturn[2] == CoinType.NICKEL
		1 * display.currencyInventory.decrement(CoinType.QUARTER)
		1 * display.currencyInventory.decrement(CoinType.DIME)
		1 * display.currencyInventory.decrement(CoinType.NICKEL)
	}

	def "when the customer requests his money be returned, the exact coins put in are returned"() {
		when:
		display.insertCoin(0, 0, 0)
		display.insertCoin(0, 0, 0)
		display.insertCoin(0, 0, 0)
		display.returnMoney()

		then:
		display.detector.analyzeCoin(_, _, _) >>> [CoinType.QUARTER, CoinType.DIME, CoinType.NICKEL]
		display.balance == 0
		display.display == 'INSERT COIN'
		display.balanceCoins.size == 0
		display.coinReturn.size == 3
		display.coinReturn[0] == CoinType.QUARTER
		display.coinReturn[1] == CoinType.DIME
		display.coinReturn[2] == CoinType.NICKEL
	}
}
