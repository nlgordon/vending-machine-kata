package com.insanedev.vending.currency

class CurrencyDetector {

	CoinType analyzeCoin(BigDecimal weight, BigDecimal diameter, BigDecimal thickness) {
		CoinType ret = CoinType.find {it.weight == weight && it.diameter == diameter && it.thickness == thickness}
		return ret
	}
}
