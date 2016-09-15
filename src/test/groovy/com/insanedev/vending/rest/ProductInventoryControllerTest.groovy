package com.insanedev.vending.rest

import com.insanedev.vending.product.ProductInventoryManager
import spock.lang.Specification

class ProductInventoryControllerTest extends Specification {

	ProductInventoryController controller

	def setup() {
		controller = new ProductInventoryController()
		controller.inventoryManager = Mock(ProductInventoryManager)
		controller.inventoryManager.getInventory() >> ["soda":1]
	}

	def "the product controller can return a map of the products and their count"() {

		when:
		Map inventory = controller.getInventory()

		then:
		inventory.size() == 1
	}
}
