package com.insanedev.vending.currency

class CurrencyDetector {

	BigDecimal tolerance = 0.05

	CoinType analyzeCoin(BigDecimal weight, BigDecimal diameter, BigDecimal thickness) {
		CoinType ret = CoinType.find {
			withinTolerance(it.weight, weight) &&
				withinTolerance(it.diameter, diameter) &&
				withinTolerance(it.thickness, thickness)
		}
		return ret
	}

	boolean withinTolerance(BigDecimal desired, BigDecimal measured) {
		return ((desired * (1 - tolerance) <= measured) && (desired * (1 + tolerance) >= measured))
	}
}
