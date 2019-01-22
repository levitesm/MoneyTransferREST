package Exceptions;

import Types.Currency;

public class NotEnoughMoneyException extends Exception {

	
	public NotEnoughMoneyException(double bal,Currency cur) {
		this("User only has "+bal+" "+cur);
		
	}
	private NotEnoughMoneyException(String str) {
		super(str);
		
	}
	

}
