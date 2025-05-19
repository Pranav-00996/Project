package finalproject;

import java.time.LocalDate;

public class Transaction {
	private String transactionID;
	private double amount;
	private LocalDate date;
	private String category;
	private String description;
	private String type;
	private String userID;
	public double getAmount() {
		return amount;
	}
	public LocalDate getDate() {
		return date;
	}
	public String getCategory() {
		return category;
	}
	public String getDescription() {
		return description;
	}
	public String getType() {
		return type;
	}
	public Transaction(String ID,double amt, LocalDate dt,String cat,String desc,String typ,String userID) {
		transactionID=ID;
		amount=amt;
		date=dt;
		category=cat;
		description=desc;
		type=typ;
		this.userID=userID;
	}
	public Transaction() {
		transactionID="1";
		amount=0;
		date=LocalDate.now();
		category="Food";
		description="Hotel";
		type="Expense";
		userID="1";
		
	}
	public String getTransactionID() {
		return transactionID;
	}
	public void display() {
		System.out.println("Transaction ID: "+ transactionID);
		System.out.println("Amount = "+amount);
		System.out.println("Date: "+date.getDayOfMonth()+"-"+date.getMonthValue()+date.getYear());
		System.out.println("Category: "+category);
		System.out.println("Description: "+description);
		System.out.println("Type = "+type);
		
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	
}