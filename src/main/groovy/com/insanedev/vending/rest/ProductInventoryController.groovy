package com.insanedev.vending.rest

import com.insanedev.vending.product.ProductInventoryManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RestController

@RestController
class ProductInventoryController {

	@Autowired
	ProductInventoryManager inventoryManager

	public Map getInventory() {
		return inventoryManager.getInventory()
	}
}
