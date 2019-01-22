package Management;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Set;

import DataModel.*;
import Exceptions.NoSuchAccountException;
import Exceptions.NoSuchUserException;




public class UserManager{

	private static volatile HashMap<Long, User> users = new HashMap<>();
	 
	public static Set<Long> getAllUserIds(){
		return users.keySet();
	}
	
	
	public static User getUserById(Long id) throws NoSuchUserException
	{
		if(users.containsKey(id)) 
			return users.get(id);
		else
			throw new NoSuchUserException(id);
	}
	
	public static Long addNewUser(String fName, String lName, int age, long telNum, String address)
	{
		User u = new User(fName, lName, age, telNum, address);
		synchronized(users) {users.put(u.getID(), u);}
		return u.getID();
	}
	
		public static Long addNewUser(String fName)
	{
		return addNewUser(fName, "", 0, 0, "");
		
	}
	
	public static Long addNewUser()
	{
		return addNewUser("");
		
	}
	
	public static void deleteUserById(Long id) throws NoSuchUserException
	{
		if(users.containsKey(id)) 
		{
			LinkedList<Long> l =  (LinkedList<Long>) users.get(id).getAccounts().clone();
			for(Long a:l)
				try {
					AccountManager.deleteAccountById(a);
				} catch (NoSuchAccountException e) {
					System.err.println("UNEXPECTED ERROR IN USERS STRUCTURE!!!");
				}
			synchronized(users) {users.remove(id);}
		}
			
		else
			throw new NoSuchUserException(id);
	}
}
