package com.insanedev.vending.display

import com.insanedev.vending.currency.CoinType
import com.insanedev.vending.currency.CurrencyDetector
import com.insanedev.vending.product.Product
import com.insanedev.vending.product.ProductInventoryManager
import spock.lang.Specification

class DisplayProductTest extends Specification {

	Display display = null;

	def setup() {
		display = new Display()
		display.detector = Mock(CurrencyDetector)
		display.detector.analyzeCoin(_, _, _) >> CoinType.QUARTER

		display.inventoryManager = new ProductInventoryManager()

		display.addProduct("A", new Product(name: "Cola", price: 1.00))
		display.addProduct("B", new Product(name: "Chips", price: 0.5))
		display.addProduct("C", new Product(name: "Candy", price: 0.65))
	}

	def "when the customer requests a product that is out of stock, the display will say 'SOLD OUT'"() {
		when:
		display.selectProduct("A")

		then:
		display.display == 'SOLD OUT'
		display.display == 'INSERT COIN'
	}
}
