package Exceptions;


public class NoSuchAccountException extends Exception {

	
	public NoSuchAccountException(Long id) {
		this("No account found for ID: "+id);
		
	}
	private NoSuchAccountException(String str) {
		super(str);
		
	}
	

}