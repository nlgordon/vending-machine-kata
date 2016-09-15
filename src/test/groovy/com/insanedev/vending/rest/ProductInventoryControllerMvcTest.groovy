package com.insanedev.vending.rest

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class ProductInventoryControllerMvcTest extends Specification {

	@Autowired
	ProductInventoryController controller

	def "the controller is autowired in to the application context"() {
		expect:
		controller != null
		controller instanceof ProductInventoryController
	}
}
