package com.insanedev.vending.rest

import com.insanedev.vending.product.ProductInventoryManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
class ProductInventoryController {

	@Autowired
	ProductInventoryManager inventoryManager

	@RequestMapping(path="/api/inventory", method=RequestMethod.GET)
	public Map getInventory() {
		return inventoryManager.getInventory()
	}
}
