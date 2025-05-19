package finalproject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BudgetManager {
	private User user;
	List<Transaction> transactions;
	public BudgetManager(User u) {
		user=u;
		transactions=u.getTransactions();
	}
	public void addTransaction(Transaction tr){
		transactions.add(tr);	
	}
	public void displayTransactions() {
		for (Transaction t : transactions) {
			t.display();
		}
	}
	public List<Transaction> getAllTransactions() {
		return transactions;
	}
	public double getTotalExpenses() {
		double total =0;
		for (Transaction t : transactions) {
			if ((t.getType().toLowerCase()).equals("expense")) {
				total = total+t.getAmount();
			}
			
		}
		return total;
	

	}
	public double getTotalIncome() {
		double total =0;
		for (Transaction t : transactions) {
			if ((t.getType().toLowerCase()).equals("income")) {
				total = total+t.getAmount();
			}

		}
		return total;
	

	}
	public boolean isBudgetExceeded() {
		double budget=user.getMonthlyBudget();
		if (getTotalExpenses()>budget) {
			return true;
		}
		else {
			return false;
		}
		
	}
	public Map<String, List<Transaction>> getAlExpensesGroupedByCategory() {
	    Map<String, List<Transaction>> grouped = new HashMap<>();
	    for (Transaction t : transactions) {
	    	if ((t.getType().toLowerCase()).equals("expense")) {
	    		String category = t.getCategory();
		        grouped.putIfAbsent(category, new ArrayList<>());
		        grouped.get(category).add(t);
			}
	        
	    }
	    return grouped;
	}

}