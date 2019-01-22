package DataModel;

import java.util.LinkedList;


public class User {

	private final Long ID;
	private String fName;
	private String lName;
	private int age;
	private long telNum;
	private String address;
	
	private volatile LinkedList<Long> accounts;
	
	private static volatile Long lastId=new Long(1000000000);
	
	
	public User(String fName, String lName, int age, long telNum, String address) {
		super();
		synchronized (lastId) {this.ID=++lastId;}
		
		this.fName = fName;
		this.lName = lName;
		this.age = age;
		this.telNum = telNum;
		this.address = address;
		
		this.accounts = new LinkedList<>();
	}
	
	public Long getID() {
		return ID;
	}

	public String getfName() {
		return fName;
	}
	public void setfName(String fName) {
		this.fName = fName;
	}
	public String getlName() {
		return lName;
	}
	public void setlName(String lName) {
		this.lName = lName;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public long getTelNum() {
		return telNum;
	}
	public void setTelNum(long telNum) {
		this.telNum = telNum;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}

	public LinkedList<Long> getAccounts() {
		return accounts;
	}
	

	
	
}
