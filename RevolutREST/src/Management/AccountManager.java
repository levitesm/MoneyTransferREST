package Management;

import java.util.*;

import DataModel.*;
import Exceptions.NoSuchAccountException;
import Exceptions.NoSuchUserException;

public class AccountManager {

	private static volatile HashMap<Long, Account> accounts = new HashMap<>();
	 
	public static Set<Long> getAllAccountIds(){
		return accounts.keySet();
	}
	
	public static Account getAccountById(Long id) throws NoSuchAccountException
	{
		if(accounts.containsKey(id)) 
			return accounts.get(id);
		else
			throw new NoSuchAccountException(id);
	}
	
	public static Long addNewAccount(Long userId) throws NoSuchUserException
	{
		User user = UserManager.getUserById(userId);
		Account a = new Account(user);
		synchronized(accounts) {accounts.put(a.getID(), a);}
		return a.getID();
	}
	
	public static void deleteAccountById(Long id) throws NoSuchAccountException
	{
		if(accounts.containsKey(id)) 
			{
			synchronized(accounts) {
				Account a = accounts.get(id);
				synchronized(a) {a.getUser().getAccounts().remove(id);}
				accounts.remove(id);
			}
			}
		else
			throw new NoSuchAccountException(id);
	}
	
	/**
	 * NOT USED FOR REST
	 * @param id
	 * @return String for printing of all Balances for the Account
	 * @throws NoSuchAccountException
	 */
	public static String printAccountBalances(Long id) throws NoSuchAccountException
	{
		StringBuffer str = new StringBuffer("");
		if(accounts.containsKey(id)) {System.out.println("Account "+id);
			for(Balance b :accounts.get(id).getBalances()) str.append(b.getCur() + " : " + b.getBalace() + "\n");
		}else
			throw new NoSuchAccountException(id);
		
		return str.toString();
	}
	
}
