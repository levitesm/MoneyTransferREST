package Exceptions;

import Types.Currency;

public class NoSuchCerrencyException extends Exception {

	
	public NoSuchCerrencyException(Currency cur) {
		this("User does not have Currecny: "+cur);
		
	}
	private NoSuchCerrencyException(String str) {
		super(str);
		
	}
	

}
