package finalproject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;



class DBHelper {
    private static final String DB_URL = "jdbc:sqlite:finance.db";

    // Establish a connection to SQLite DB
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    // Create the users table (if not exists)
    public static void createUserTable() {
        String sql = "CREATE TABLE IF NOT EXISTS users (" +
                     "userID TEXT PRIMARY KEY," +
                     "username TEXT UNIQUE NOT NULL," +
                     "password TEXT NOT NULL," +
                     "monthlyBudget REAL);";
        
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void createTransactionsTable() {
        String sql = "CREATE TABLE IF NOT EXISTS transactions (" +
                     "transactionID TEXT PRIMARY KEY," +  
                     "userID TEXT NOT NULL," +
                     "amount REAL NOT NULL," +
                     "date TEXT NOT NULL," +  
                     "category TEXT NOT NULL," +
                     "description TEXT NOT NULL," +
                     "type TEXT NOT NULL," +
                     "FOREIGN KEY (userID) REFERENCES users(userID));";
        
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void saveTransaction(Transaction transaction) {
	    String sql = "INSERT INTO transactions(transactionID, userID, amount, date, category, description, type) VALUES (?, ?, ?, ?, ?, ?, ?)";
	    
	    try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
	        stmt.setString(1, transaction.getTransactionID());
	        stmt.setString(2, transaction.getUserID());
	        stmt.setDouble(3, transaction.getAmount());
	        stmt.setString(4, transaction.getDate().toString());  // Save the LocalDate as a String
	        stmt.setString(5, transaction.getCategory());
	        stmt.setString(6, transaction.getDescription());
	        stmt.setString(7, transaction.getType());
	        
	        stmt.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
    public static List<Transaction> getTransactionsForUser(String userID) {
        String sql = "SELECT * FROM transactions WHERE userID = ?";
        List<Transaction> transactions = new ArrayList<>();
        
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, userID);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                String transactionID = rs.getString("transactionID");
                double amount = rs.getDouble("amount");
                LocalDate date = LocalDate.parse(rs.getString("date"));  // Convert the string to LocalDate
                String category = rs.getString("category");
                String description = rs.getString("description");
                String type = rs.getString("type");
                
          
                transactions.add(new Transaction(transactionID, amount, date, category, description, type,userID));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactions;
    }


    public static void createDatabaseTables() {
        createUserTable();  // Create users table
        createTransactionsTable();  // Create transactions table
    }
    public static void saveUser(User user) {
        String sql = "INSERT INTO users(userID, username, password, monthlyBudget) VALUES (?, ?, ?, ?)";

        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getUserID());
            stmt.setString(2, user.getUserName());
            stmt.setString(3, user.getPassword());  // You'll need to add getPassword() to User
            stmt.setDouble(4, user.getMonthlyBudget());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static User getUserByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String userID = rs.getString("userID");
                String password = rs.getString("password");
                double budget = rs.getDouble("monthlyBudget");

                User user = new User(userID, username, password, budget);

             
                List<Transaction> transactions = getTransactionsForUser(userID);
                user.setTransactions(transactions);

                return user;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
 




}