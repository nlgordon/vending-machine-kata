# Vending Machine Kata

## General Architecture

The app is designed as a small set of loosely coupled modules to facilitate unit testing and future expansion.

* Modules
	* Physical display
	* Physical inventory
		* Coin inventory
		* Snack inventory
	* Business Logic
		* Coin detection
		* Handling transactions
		* Making change
		* Mapping requests to inventory
	* Core application
		* Wires together different physical aspects of machine with business logic
		* Provides hooks for future expansion - NFC, Rest API for mobile app, etc.
