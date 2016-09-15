package com.insanedev.vending.rest

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import spock.lang.Specification
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@SpringBootTest
class ProductInventoryControllerMvcTest extends Specification {

	@Autowired
	ProductInventoryController controller

	@Autowired
	WebApplicationContext context

	MockMvc mockMvc

	def setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build()
	}

	def "the controller is autowired in to the application context"() {
		expect:
		controller != null
		controller instanceof ProductInventoryController
	}

	def "a request to the inventory api url returns the product inventory"() {
		when:
		ResultActions result = mockMvc.perform(get("/api/inventory").accept(MediaType.APPLICATION_JSON_UTF8))

		then:
		result.andExpect(status().isOk())
		result.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
		result.andExpect(content().json("{}"))
	}
}
