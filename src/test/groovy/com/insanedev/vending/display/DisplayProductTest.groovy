package com.insanedev.vending.display

import com.insanedev.vending.product.ProductInventoryManager

class DisplayProductTest extends BaseDisplayTest {

	def setup() {
		display.productInventory = new ProductInventoryManager()
	}

	def "when the customer requests a product that is out of stock, the display will say 'SOLD OUT'"() {
		when:
		display.selectProduct("A")

		then:
		display.display == 'SOLD OUT'
		display.display == 'INSERT COIN'
	}
}
