package com.insanedev.vending.currency

class CurrencyInventoryManager {
	Map<CoinType, Integer> inventory = [:]

	int getInventoryCount(CoinType coin) {
		if (!inventory.containsKey(coin)) {
			return 0
		}

		return inventory[coin]
	}

}
