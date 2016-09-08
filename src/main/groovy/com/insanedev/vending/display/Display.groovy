package com.insanedev.vending.display

import com.insanedev.vending.currency.CoinType
import com.insanedev.vending.currency.CurrencyDetector

class Display {

	CurrencyDetector detector = null

	BigDecimal balance = 0;

	void insertCoin(BigDecimal weight, BigDecimal diameter, BigDecimal thickness) {
		CoinType type = detector.analyzeCoin(weight, diameter, thickness)

		if (type) {
			balance += type.value
		}
	}
}
