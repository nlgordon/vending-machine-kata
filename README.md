# Vending Machine Kata

## General Architecture

The app is designed as a small set of loosely coupled modules to facilitate unit testing and future expansion.

* Modules
	* Physical display
	* Physical inventory
		* Currency inventory
		* Snack inventory
	* Business Logic
		* Currency detection
		* Handling transactions
		* Making change
		* Mapping requests to inventory
	* Core application
		* Wires together different physical aspects of machine with business logic
		* Provides hooks for future expansion - NFC, Rest API for mobile app, etc.

## Currency Specs
* From: https://www.usmint.gov/about_the_mint/?action=coin_specifications
Coin    | Weight    | Diameter | Thickness
------- | --------- | -------- | ---------
Penny   | 2.500g    | 19.05mm  | 1.52mm
Nickel  | 5.000g    | 21.21mm  | 1.95mm
Dime    | 2.268g    | 17.91mm  | 1.35mm
Quarter | 5.670g    | 24.26mm  | 1.75mm

