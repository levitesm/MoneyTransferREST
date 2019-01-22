package DataModel;

import java.util.*;

import Exceptions.*;
import Types.*;
import Types.Currency;

public class Account {
	
	private final Long ID;
	
	private final User user;
	
	private volatile LinkedList<Balance> balances;
	
	private static volatile Long lastId=new Long(200000000);

	
	public Account(User user) {
		super();
		this.user = user;
		
		synchronized (lastId) {ID=++lastId;}
		synchronized (user) {user.getAccounts().add(ID);}
		
		balances=new LinkedList<Balance>();
	}

	public Long getID() {
		return ID;
	}

	public User getUser() {
		return user;
	}

	public List<Balance> getBalances() {
		return balances; 
	}
	
	
	public synchronized void addMoney(double sum, Currency cur) throws WrongAmountException
	{
		if(sum<=0) throw new WrongAmountException("Money amount should be positive.");
		
		for(Balance b:balances)
		{
			if(b.getCur()==cur)
			{
				b.setBalace(b.getBalace()+sum);
				return;
			}
		}
		balances.add(new Balance(sum, cur));
	}
	
	

	public synchronized void getMoney(double sum, Currency cur) throws NotEnoughMoneyException, NoSuchCerrencyException, WrongAmountException
	{
		if(sum<=0) throw new WrongAmountException("Money amount should be positive.");
		
		for(Balance b:balances)
		{
			if(b.getCur()==cur)
			{
				if(b.getBalace()>=sum)
				{
					b.setBalace(b.getBalace()-sum);
					return;
				}
					
				else throw new NotEnoughMoneyException(b.getBalace(), cur);
			}
		}
		
		throw new NoSuchCerrencyException(cur);
	}
}
