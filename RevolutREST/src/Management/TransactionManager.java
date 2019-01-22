package Management;

import java.util.Arrays;
import java.util.Date;
import java.util.InputMismatchException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;

import DataModel.*;
import Exceptions.NoSuchAccountException;
import Exceptions.NoSuchCerrencyException;
import Exceptions.NotEnoughMoneyException;
import Exceptions.WrongAmountException;

import Types.Currency;
import Types.TransType;

public class TransactionManager {
	
	private static volatile LinkedList<Transaction> transactions = new LinkedList<>();
	
public static LinkedList<Transaction> getTransactionList()
{
	return transactions;
}

public static LinkedList<Transaction> getTransactionList(Long acc) throws NoSuchAccountException
{
	AccountManager.getAccountById(acc);
	LinkedList<Transaction> l = new LinkedList<Transaction>();
	for(Transaction t : transactions)
	{
		if(t.getUserIds().contains(acc)) l.add(t);
	}
	return l;
}

public static LinkedList<Transaction> getTransactionList(TransType type)
{
	LinkedList<Transaction> l = new LinkedList<Transaction>();
	for(Transaction t : transactions)
	{
		if(t.getType()==type) l.add(t);
	}
	return l;
}

public static LinkedList<Transaction> getTransactionList(Long acc, TransType type) throws NoSuchAccountException
{
	AccountManager.getAccountById(acc);
	LinkedList<Transaction> l = new LinkedList<Transaction>();
	for(Transaction t : transactions)
	{
		if(t.getType()==type && t.getUserIds().contains(acc)) l.add(t);
	}
	return l;
}


public static void putMoney(Long to, double sum, Currency toCur) throws NoSuchAccountException, WrongAmountException
{
	
	Account a=AccountManager.getAccountById(to);
	
	
	a.addMoney(sum, toCur);
	
	
	synchronized(transactions) {transactions.add(new Transaction(new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new Date()), TransType.PUT, new LinkedList<Long>(Arrays.asList(to)), "Money input to AccountId " + to +" : "+sum+" ("+toCur+")"));}
}

public static void withdrawMoney(Long from, double sum, Currency fromCur) throws NoSuchAccountException, NotEnoughMoneyException, NoSuchCerrencyException, WrongAmountException
{
	
	Account a=AccountManager.getAccountById(from);
	
	
	a.getMoney(sum, fromCur);
	
	
	synchronized(transactions) {transactions.add(new Transaction(new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new Date()), TransType.WITHDRAW, new LinkedList<Long>(Arrays.asList(from)), "Money withdraw from AccountId " + from +" : "+sum+" ("+fromCur+")"));}
}

public static void transferMoney(Long from, Long to, double sum, Currency fromCur, Currency toCur) throws NoSuchAccountException, NotEnoughMoneyException, NoSuchCerrencyException, WrongAmountException
{
	if(from.equals(to) && fromCur.equals(toCur)) throw new WrongAmountException("Meaningless money transfer!");
	Account a1=AccountManager.getAccountById(from);
	Account a2=AccountManager.getAccountById(to);
	
	a1.getMoney(sum, fromCur);
	a2.addMoney(sum*fromCur.getRate()/toCur.getRate(), toCur);
	
	synchronized(transactions) {transactions.add(new Transaction(new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new Date()), TransType.TRANSFER, new LinkedList<Long>(Arrays.asList(from,to)), "Money transfer from AccountId " + from + " to AccountId "+ to +" : "+sum+" ("+fromCur+"->"+toCur+")"));}
}

public static void transferMoney(Long from, Long to, double sum, Currency fromCur) throws NoSuchAccountException, NotEnoughMoneyException, NoSuchCerrencyException, WrongAmountException
{
	transferMoney(from, to, sum, fromCur, fromCur);
}

public static void transferMoney(Long from, double sum, Currency fromCur, Currency toCur) throws NoSuchAccountException, NotEnoughMoneyException, NoSuchCerrencyException, WrongAmountException
{
	transferMoney(from, from, sum, fromCur, toCur);
}


}
