package com.insanedev.vending.currency

enum CoinType {
	QUARTER(5.67, 24.26, 1.75),
	DIME(2.268, 17.91, 1.35),
	NICKEL(5.000, 21.21, 1.95)

	final BigDecimal weight
	final BigDecimal diameter
	final BigDecimal thickness

	CoinType(BigDecimal weight, BigDecimal diameter, BigDecimal thickness) {
		this.weight = weight
		this.diameter = diameter
		this.thickness = thickness
	}
}