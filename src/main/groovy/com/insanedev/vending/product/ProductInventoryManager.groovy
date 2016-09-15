package com.insanedev.vending.product

import org.springframework.stereotype.Component

@Component
class ProductInventoryManager {

	Map<String, Integer> products = [:]

	void addInventory(String product, int count) {
		products.put(product, count)
	}

	int getInventoryCount(String product) {
		if (products.containsKey(product)) {
			return products[product]
		}

		return 0
	}

	public Map getInventory() {
		return products
	}
}
