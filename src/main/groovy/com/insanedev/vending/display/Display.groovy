package com.insanedev.vending.display

import com.insanedev.vending.currency.CoinType
import com.insanedev.vending.currency.CurrencyDetector
import com.insanedev.vending.product.Product

import java.text.NumberFormat

class Display {

	// References to other application modules
	CurrencyDetector detector = null

	// Configuration of this display
	Map<String, Product> productMap = [:]

	// State variables
	BigDecimal balance = 0
	String display = "INSERT COIN"
	Integer coinReturnCount = 0

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
		}
	}

	void addProduct(String button, Product product) {
		productMap[button] = product
	}
}
