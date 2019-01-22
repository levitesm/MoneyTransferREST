package DataModel;

import java.util.LinkedList;


import Types.TransType;

public class Transaction {
	
	private final String timeStamp;
	private final TransType type;
	private final LinkedList<Long> accountIds;
	private final String description;
	
	
	public Transaction(String timeStamp, TransType type, LinkedList<Long> accountIds, String description) {
		super();
		this.timeStamp = timeStamp;
		this.type = type;
		this.accountIds = accountIds;
		this.description = description;
	}
	
	public String getTimeStamp() {
		return timeStamp;
	}
	public TransType getType() {
		return type;
	}
	public LinkedList<Long> getUserIds() {
		return accountIds;
	}
	public String getDescription() {
		return description;
	}
	
	
	

}
