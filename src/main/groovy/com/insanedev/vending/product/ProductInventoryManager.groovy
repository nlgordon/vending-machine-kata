package com.insanedev.vending.product

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
}
