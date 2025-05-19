package finalproject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;



public class AuthManager {
	

	    private static final String DB_URL = "jdbc:sqlite:finance.db";
	    private User loggedInUser;

	    
	    public boolean registerUser(User user) {
	        String checkSql = "SELECT username FROM users WHERE username = ?";
	        String insertSql = "INSERT INTO users(userID, username, password, monthlyBudget) VALUES (?, ?, ?, ?)";

	        try (Connection conn = DriverManager.getConnection(DB_URL);
	             PreparedStatement checkStmt = conn.prepareStatement(checkSql);
	             PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {

	            
	            checkStmt.setString(1, user.getUserName());
	            ResultSet rs = checkStmt.executeQuery();

	            if (rs.next()) {
	                System.out.println("❌ Username already exists.");
	                return false;
	            }

	            
	            insertStmt.setString(1, user.getUserID());
	            insertStmt.setString(2, user.getUserName());
	            insertStmt.setString(3, user.getPassword());
	            insertStmt.setDouble(4, user.getMonthlyBudget());

	            insertStmt.executeUpdate();
	            System.out.println("✅ Registration successful.");
	            return true;

	        } catch (SQLException e) {
	            System.out.println("❌ Registration failed: " + e.getMessage());
	            return false;
	        }
	    }

	    
	    public boolean login(String username, String password) {
	        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";

	        try (Connection conn = DriverManager.getConnection(DB_URL);
	             PreparedStatement stmt = conn.prepareStatement(sql)) {

	            stmt.setString(1, username);
	            stmt.setString(2, password);
	            ResultSet rs = stmt.executeQuery();

	            if (rs.next()) {
	               
	                String userID = rs.getString("userID");
	                double budget = rs.getDouble("monthlyBudget");

	                loggedInUser = new User(userID, username, password, budget);
	                System.out.println("Login successful. Welcome, " + username + "!");
	                return true;
	            } else {
	                System.out.println("Invalid username or password.");
	                return false;
	            }

	        } catch (SQLException e) {
	            System.out.println("❌ Login failed: " + e.getMessage());
	            return false;
	        }
	    }

	    
	    public void logout() {
	        if (loggedInUser != null) {
	            System.out.println("Logged out: " + loggedInUser.getUserName());
	            loggedInUser = null;
	        }
	    }

	    
	    public User getLoggedInUser() {
	        return loggedInUser;
	    }
	}