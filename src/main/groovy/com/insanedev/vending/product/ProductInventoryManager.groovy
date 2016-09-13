package com.insanedev.vending.product

class ProductInventoryManager {

	Map<String, Integer> products = [:]

	void addInventory(String product, int count) {
		products.put(product, count)
	}

	int getInventoryCount(String product) {
		return products[product]
	}
}
