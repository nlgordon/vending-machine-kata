package com.insanedev.vending.display

import com.insanedev.vending.currency.CoinType
import com.insanedev.vending.currency.CurrencyDetector
import com.insanedev.vending.currency.CurrencyInventoryManager
import com.insanedev.vending.product.Product
import com.insanedev.vending.product.ProductInventoryManager
import groovy.util.logging.Slf4j

import java.text.NumberFormat

@Slf4j
class Display {

	// References to other application modules
	CurrencyDetector detector = null
	ProductInventoryManager productInventory = null
	CurrencyInventoryManager currencyInventory = null

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

		if (!productInventory.getInventoryCount(product.name)) {
			display = 'SOLD OUT'
			return
		}

		if (product.price > balance) {
			NumberFormat formatter = NumberFormat.currencyInstance
			display = "PRICE " + formatter.format(product.price)
		} else {
			BigDecimal remainingBalance = balance - product.price
			Map<CoinType, Integer> change = getChange(remainingBalance)
			if (!change && remainingBalance) {
				display = 'EXACT CHANGE ONLY'
				return
			}

			dispenseProduct(product)

			balance = remainingBalance
			processChange(change)
			balanceCoins = []
			display = 'THANK YOU'
		}
	}

	void dispenseProduct(Product product) {
		log.info("Dispensing ${product.name}")
		dispensedProducts << product
	}

	void processChange(Map<CoinType, Integer> change) {
		change.each { CoinType coin, Integer count ->
			1..count.each {
				// Possibly wire this so that the inventory puts the coin into the return directly
				coinReturn << coin

				currencyInventory.decrement(coin)
				balance -= coin.value
			}
		}
	}

	Map<CoinType, Integer> getChange(BigDecimal amount) {

		if (amount == 0) {
			return null
		}

		Map<CoinType, Integer> ret = [:]

		List<CoinType> sortedCoins = CoinType.collect { it }.sort { a, b -> a.value <=> b.value }.reverse()

		BigDecimal remainingAmount = amount

		while (remainingAmount > 0) {
			CoinType change = sortedCoins.find { it.value <= remainingAmount }
			if (!ret[change]) {
				ret[change] = 1
			} else {
				ret[change]++
			}

			remainingAmount -= change.value
		}

		if (ret.findAll { CoinType coin, Integer count ->
			if (currencyInventory.getInventoryCount(coin) <= count) {
				return false
			}
			return true
		}.size() != ret.size()) {
			return null
		}

		return ret
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
