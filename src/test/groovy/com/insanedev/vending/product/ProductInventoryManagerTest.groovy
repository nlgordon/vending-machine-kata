package com.insanedev.vending.product

import spock.lang.Specification

class ProductInventoryManagerTest extends Specification {

	ProductInventoryManager manager = null

	def setup() {
		manager = new ProductInventoryManager()
	}

	def "the product manager can return how many items are in the inventory for a specific product"() {
		setup:
		manager.addInventory("soda", 2)

		when:
		int count = manager.getInventoryCount("soda")

		then:
		count == 2
	}
}
