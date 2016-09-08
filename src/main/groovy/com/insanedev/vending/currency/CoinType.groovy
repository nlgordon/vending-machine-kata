package com.insanedev.vending.currency

enum CoinType {
	QUARTER(5.67, 24.26, 1.75, 0.25),
	DIME(2.268, 17.91, 1.35, 0.10),
	NICKEL(5.000, 21.21, 1.95, 0.05)

	final BigDecimal weight
	final BigDecimal diameter
	final BigDecimal thickness
	final BigDecimal value

	CoinType(BigDecimal weight, BigDecimal diameter, BigDecimal thickness, BigDecimal value) {
		this.weight = weight
		this.diameter = diameter
		this.thickness = thickness
		this.value = value
	}
}