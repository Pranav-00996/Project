package finalproject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class User {
	
	private String userID;
	private String userName;
	private String password;
	private double monthlyBudget;
	private List<Transaction> transactions;
	
	public User(String a, String b, String c, double d) {
		userID = a;
		userName = b;
		password = c;
		monthlyBudget = d;
	}
	
	public boolean authenticate(String a, String b) {
		return userName.equals(a) && password.equals(b);
		
	}
	
	public void setMonthlyBudget(double a) {
		monthlyBudget = a;
	}
	
	public double getMonthlyBudget() {
		return monthlyBudget;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public String getUserID() {
		return userID;
	}
	public List<Transaction> getTransactions() {
		return transactions;
	}
	public void setTransactions(List<Transaction> transactions) {
	    this.transactions = transactions;
	}
	public void addTransaction(Transaction transaction) {
	        transactions.add(transaction);
	    
	}

	public String getPassword() {
		return password;
	}
	

}