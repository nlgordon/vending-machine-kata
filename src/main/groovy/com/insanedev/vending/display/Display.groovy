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
	List<CoinType> balanceCoins = []
	String display = null
	String nextDisplay = null
	List<CoinType> coinReturn = []
	List<Product> dispensedProducts = []

	void insertCoin(BigDecimal weight, BigDecimal diameter, BigDecimal thickness) {
		CoinType type = detector.analyzeCoin(weight, diameter, thickness)

		if (type) {
			balance += type.value
			balanceCoins << type
			display = balance.toString()
		} else {
			display = null
			sendToCoinReturn(null)
		}
	}

	void sendToCoinReturn(CoinType coin) {
		coinReturn << coin
	}

	void selectProduct(String button) {
		Product product = productMap[button]

		if (product.price > balance) {
			NumberFormat formatter = NumberFormat.currencyInstance
			display = "PRICE " + formatter.format(product.price)
		} else {
			balance -= product.price
			balanceCoins = []
			display = 'THANK YOU'
			dispenseProduct(product)
			makeChange()
		}
	}

	void dispenseProduct(Product product) {
		log.info("Dispensing ${product.name}")
		dispensedProducts << product
	}

	void makeChange() {
		if (balance == 0) {
			return
		}

		List<CoinType> sortedCoins = CoinType.collect { it }.sort { a, b -> a.value <=> b.value }.reverse()

		while (balance > 0) {
			CoinType change = sortedCoins.find { it.value <= balance }
			balance -= change.value
			coinReturn << change
		}
	}

	void addProduct(String button, Product product) {
		productMap[button] = product
	}

	String getDisplay() {
		String ret = display ? display : 'INSERT COIN'
		if (nextDisplay) {
			display = nextDisplay
			nextDisplay = null
		} else {
			if (balance) {
				display = balance.toString()
			} else {
				display = null
			}
		}
		return ret
	}

	void returnMoney() {
		coinReturn.addAll(balanceCoins)
		balanceCoins = []
		balance = 0
		display = null
	}
}
