package com.insanedev.vending.rest

import com.insanedev.vending.product.ProductInventoryManager

class ProductInventoryController {

	ProductInventoryManager inventoryManager

	public Map getInventory() {
		return inventoryManager.getInventory()
	}
}
