package DataModel;

import Types.Currency;

public class Balance {
	
	private double balace;
	private final Currency currency;
	
	
	public Balance(double balace, Currency cur) {
		super();
		this.balace = balace;
		this.currency = cur;
	}
	
	public Balance(Currency cur) {
		
		this(0, cur);
	}
	
	public double getBalace() {
		return balace;
	}
	public void setBalace(double balace) {
		this.balace = balace;
	}
	public Currency getCur() {
		return currency;
	}
	
}
