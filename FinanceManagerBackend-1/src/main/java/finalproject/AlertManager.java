package finalproject;


import java.util.*;


public class AlertManager {

    private User user;
    private List<String> alerts;

    public AlertManager(User user) {
        this.user = user;
        this.alerts = new ArrayList<>();
    }

   
    public void checkBudgetStatus(List<Transaction> transactions) {
        alerts.clear();
        double budget = user.getMonthlyBudget();

        
        List<Transaction> expenses = new ArrayList<>();
        for (Transaction t : transactions) {
            if ("Expense".equalsIgnoreCase(t.getType())) {
                expenses.add(t);
            }
        }

       
        double totalExpenses = 0;
        for (Transaction e : expenses) {
            totalExpenses += e.getAmount();
        }

        
        if (budget <= 0) {
            alerts.add("⚠ Warning: Monthly budget is not set!");
            return;
        }
        if (totalExpenses > budget) {
            alerts.add("⚠ You have exceeded your monthly budget of ₹" + budget);
        } else if ((budget - totalExpenses) <= budget * 0.1) {
            alerts.add("⚠ You are close to exceeding your monthly budget.");
        } else {
            alerts.add("✅ You're within your budget. Great job!");
        }

       
        if (!expenses.isEmpty()) {
            String topCategory = getHighestSpendingCategory(expenses);
            alerts.add("💡 Tip: You’re spending the most on **" + topCategory +
                       ". Consider cutting back in this area.");
        }
    }

    public List<String> getAlerts() {
        return new ArrayList<>(alerts);
    }

    
    public void clearAlerts() {
        alerts.clear();
    }

    
    private String getHighestSpendingCategory(List<Transaction> expenses) {
        Map<String, Double> sums = new HashMap<>();
        for (Transaction e : expenses) {
            String cat = e.getCategory();
            sums.put(cat, sums.getOrDefault(cat, 0.0) + e.getAmount());
        }
        return Collections.max(sums.entrySet(), Map.Entry.comparingByValue()).getKey();
    }
}