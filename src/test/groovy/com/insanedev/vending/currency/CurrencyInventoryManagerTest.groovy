package com.insanedev.vending.currency

import spock.lang.Specification

class CurrencyInventoryManagerTest extends Specification {

	CurrencyInventoryManager manager = null

	def setup() {
		manager = new CurrencyInventoryManager()
	}

	def "the currency inventory defaults to zero if no inventory has been setup"() {
		when:
		int count = manager.getInventoryCount(CoinType.QUARTER)

		then:
		count == 0
	}

	def "the currency inventory returns the count of coins currently in the inventory"() {
		setup:
		manager.addInventory(CoinType.QUARTER, 1)

		when:
		int count = manager.getInventoryCount(CoinType.QUARTER)

		then:
		count == 1
	}
}
