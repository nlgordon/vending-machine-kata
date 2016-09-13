package com.insanedev.vending.display

import com.insanedev.vending.currency.CurrencyInventoryManager
import spock.lang.Specification

class DisplayCurrencyInventoryTest extends BaseDisplayTest {

	def setup() {
		display.currencyInventory = new CurrencyInventoryManager()
	}

	def "if the user selects a product and we can't make change, then don't dispense the product"() {
		when:
		// Mocked for quarters
		display.insertCoin(0, 0, 0)
		display.insertCoin(0, 0, 0)
		display.insertCoin(0, 0, 0)
		display.insertCoin(0, 0, 0)
		display.insertCoin(0, 0, 0)
		display.selectProduct("A")

		then:
		display.display == 'EXACT CHANGE ONLY'
	}
}
