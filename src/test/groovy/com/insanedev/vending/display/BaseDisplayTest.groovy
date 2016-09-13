package com.insanedev.vending.display

import com.insanedev.vending.currency.CoinType
import com.insanedev.vending.currency.CurrencyDetector
import com.insanedev.vending.currency.CurrencyInventoryManager
import com.insanedev.vending.product.Product
import com.insanedev.vending.product.ProductInventoryManager
import spock.lang.Specification

class BaseDisplayTest extends Specification {

	Display display = null;

	def setup() {
		display = new Display()
		display.detector = Mock(CurrencyDetector)
		display.detector.analyzeCoin(_, _, _) >> CoinType.QUARTER
		display.productInventory = Mock(ProductInventoryManager)
		display.productInventory.getInventoryCount(_) >> 1
		display.currencyInventory = Mock(CurrencyInventoryManager)
		display.currencyInventory.getInventoryCount(_) >> 5

		display.addProduct("A", new Product(name: "Cola", price: 1.00))
		display.addProduct("B", new Product(name: "Chips", price: 0.5))
		display.addProduct("C", new Product(name: "Candy", price: 0.65))
	}
}
