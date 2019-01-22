package Tests;


import Management.*;
import Types.*;
import Exceptions.*;

import DataModel.*;

public class SimpleClientTest {

	public static void main(String[] args) throws NoSuchAccountException, NotEnoughMoneyException, NoSuchCerrencyException, WrongAmountException, NoSuchUserException {
		
		
		
		Long u1 = UserManager.addNewUser("Mark");
		Long u2 = UserManager.addNewUser("Alex");
		Long u3 = UserManager.addNewUser("Olga");
		
		
		Long ac1 = AccountManager.addNewAccount(u1);
		Long ac12 = AccountManager.addNewAccount(u1);
		Long ac2 = AccountManager.addNewAccount(u2);
		Long ac3 = AccountManager.addNewAccount(u3);
		
		System.out.println("Accounts of user "+u1);
		for(Long a: UserManager.getUserById(u1).getAccounts())System.out.println(a);

		TransactionManager.putMoney(ac1, 1000, Currency.RUB);
		
		System.out.println(AccountManager.printAccountBalances(ac1));
		
		TransactionManager.transferMoney(ac1, ac3, 100,Currency.RUB, Currency.EUR);
		
		System.out.println(AccountManager.printAccountBalances(ac1));
		System.out.println(AccountManager.printAccountBalances(ac3));
		
		TransactionManager.transferMoney(ac1, 100,Currency.RUB, Currency.EUR);
		System.out.println(AccountManager.printAccountBalances(ac1));
		
		System.out.println("--------");
		for(Long a : AccountManager.getAllAccountIds())System.out.println(a);
		AccountManager.deleteAccountById(ac2);
		
		System.out.println("--------");
		for(Long a : AccountManager.getAllAccountIds())System.out.println(a);
		UserManager.deleteUserById(u1);
		
		System.out.println("--------");
		for(Long a : AccountManager.getAllAccountIds())System.out.println(a);
		
		
		for (Transaction t : TransactionManager.getTransactionList()) System.out.println(t.getDescription() + " at " + t.getTimeStamp());
	}

}
