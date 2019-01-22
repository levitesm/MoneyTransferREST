package DataModel;

import Types.Currency;

public class Balance {
	
	private double balance;
	private final Currency currency;
	
	
	public Balance(double balace, Currency cur) {
		super();
		this.balance = balace;
		this.currency = cur;
	}
	
	public Balance(Currency cur) {
		
		this(0, cur);
	}
	
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balace) {
		this.balance = balace;
	}
	public Currency getCur() {
		return currency;
	}
	
}
