package com.insanedev.vending

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import spock.lang.Specification

@SpringBootTest
class ApplicationTest extends Specification {

	@Autowired
	ApplicationContext context

	def "application context loads correctly"() {
		expect:
		context != null

	}
}
