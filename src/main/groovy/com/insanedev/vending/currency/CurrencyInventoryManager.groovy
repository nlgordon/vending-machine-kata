package com.insanedev.vending.currency

class CurrencyInventoryManager {
	Map<CoinType, Integer> inventory = [:]

	int getInventoryCount(CoinType coin) {
		if (!inventory.containsKey(coin)) {
			return 0
		}

		return inventory[coin]
	}

	void addInventory(CoinType type, Integer count) {
		inventory[type] = count
	}

	void decrement(CoinType coin) {
		if (!inventory[coin]) {
			throw new IllegalArgumentException("Cannot remove a ${coin} from inventory that has none left")
		}
		inventory[coin]--
	}
}
