package com.insanedev.vending.display

import com.insanedev.vending.currency.CoinType
import com.insanedev.vending.currency.CurrencyDetector

class Display {

	// References to other application modules
	CurrencyDetector detector = null

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
}
