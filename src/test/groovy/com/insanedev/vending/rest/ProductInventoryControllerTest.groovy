package com.insanedev.vending.rest

import com.insanedev.vending.product.ProductInventoryManager
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

class ProductInventoryControllerTest extends Specification {

	MockMvc mockMvc
	ProductInventoryController controller

	def setup() {
		controller = new ProductInventoryController()
		controller.inventoryManager = Mock(ProductInventoryManager)
		controller.inventoryManager.getInventory() >> ["soda":1]
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build()
	}

	def "the product controller can return a map of the products and their count"() {
		when:
		Map inventory = controller.getInventory()

		then:
		inventory.size() == 1
	}

	def "When the api is called, the inventory is returned"() {
		when:

		ResultActions result = mockMvc.perform(get("/api/inventory").accept(MediaType.APPLICATION_JSON_UTF8))

		then:
		result.andExpect(status().isOk())
		result.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
		result.andExpect(content().json('{"soda":1}'))
	}
}
