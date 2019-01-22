package Exceptions;


public class NoSuchUserException extends Exception {

	
	public NoSuchUserException(Long id) {
		this("No user found for ID: "+id);
		
	}
	private NoSuchUserException(String str) {
		super(str);
		
	}
	

}