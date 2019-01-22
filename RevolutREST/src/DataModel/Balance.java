package DataModel;

import Types.Currency;

public class Balance {
	
	private double balance;
	private final Currency currency;
	
	
	public Balance(double balance, Currency cur) {
		super();
		this.balance = balance;
		this.currency = cur;
	}
	
	public Balance(Currency cur) {
		
		this(0, cur);
	}
	
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	public Currency getCur() {
		return currency;
	}
	
}
