package com.insanedev.vending.display

import com.insanedev.vending.currency.CoinType
import com.insanedev.vending.currency.CurrencyDetector
import com.insanedev.vending.product.Product
import groovy.util.logging.Slf4j

import java.text.NumberFormat

@Slf4j
class Display {

	// References to other application modules
	CurrencyDetector detector = null

	// Configuration of this display
	Map<String, Product> productMap = [:]

	// State variables
	BigDecimal balance = 0
	String display = "INSERT COIN"
	String nextDisplay = null
	Integer coinReturnCount = 0
	List<Product> dispensedProducts = []

	void insertCoin(BigDecimal weight, BigDecimal diameter, BigDecimal thickness) {
		CoinType type = detector.analyzeCoin(weight, diameter, thickness)

		if (type) {
			balance += type.value
			display = balance.toString()
		} else {
			display = "INSERT COIN"
			sendToCoinReturn()
		}
	}

	void sendToCoinReturn() {
		coinReturnCount++
	}

	void selectProduct(String button) {
		Product product = productMap[button]

		if (product.price > balance) {
			NumberFormat formatter = NumberFormat.currencyInstance
			display = "PRICE " + formatter.format(product.price)
		} else {
			balance -= product.price
			display = 'THANK YOU'
			dispenseProduct(product)
		}
	}

	void dispenseProduct(Product product) {
		log.info("Dispensing ${product.name}")
		dispensedProducts << product
	}

	void addProduct(String button, Product product) {
		productMap[button] = product
	}

	String getDisplay() {
		String ret = display
		if (nextDisplay) {
			display = nextDisplay
			nextDisplay = null
		} else {
			if (balance) {
				display = balance.toString()
			} else {
				display = 'INSERT COIN'
			}
		}
		return ret
	}
}
