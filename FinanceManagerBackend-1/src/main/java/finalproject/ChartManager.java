package finalproject;


import java.util.List;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;



public class ChartManager {

    
    public void showExpensePieChart(List<Transaction> transactions) {
        DefaultPieDataset dataset = new DefaultPieDataset();
        Map<String, Double> categoryMap = new HashMap<>();

        for (Transaction t : transactions) {
            if (t.getType().equalsIgnoreCase("Expense")) {
                categoryMap.put(t.getCategory(), categoryMap.getOrDefault(t.getCategory(), 0.0) + t.getAmount());
            }
        }

        for (Map.Entry<String, Double> entry : categoryMap.entrySet()) {
            dataset.setValue(entry.getKey(), entry.getValue());
        }

        JFreeChart chart = ChartFactory.createPieChart("Expense Breakdown", dataset, true, true, false);
        displayChart(chart, "Expense Pie Chart");
    }

    // Show Income vs Expense Bar Chart
    public void showIncomeVsExpenseChart(List<Transaction> transactions) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        double totalIncome = 0;
        double totalExpense = 0;

        for (Transaction t : transactions) {
            if (t.getType().equalsIgnoreCase("Income")) {
                totalIncome += t.getAmount();
            } else if (t.getType().equalsIgnoreCase("Expense")) {
                totalExpense += t.getAmount();
            }
        }

        dataset.addValue(totalIncome, "Income", "Total");
        dataset.addValue(totalExpense, "Expense", "Total");

        JFreeChart chart = ChartFactory.createBarChart("Income vs Expense", "Category", "Amount", dataset);
        displayChart(chart, "Income vs Expense Chart");
    }

    // Show Budget Usage Bar Chart
    public void showBudgetUsageChart(double spent, double budget) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(spent, "Spent", "Budget");
        dataset.addValue(budget, "Budget", "Budget");

        JFreeChart chart = ChartFactory.createBarChart("Budget Usage", "Category", "Amount", dataset);
        displayChart(chart, "Budget Usage");
    }

    // Reusable Chart Display Method
    private void displayChart(JFreeChart chart, String windowTitle) {
        ChartPanel chartPanel = new ChartPanel(chart);
        JFrame frame = new JFrame(windowTitle);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // only close this chart
        frame.getContentPane().add(chartPanel);
        frame.pack();
        frame.setLocationRelativeTo(null); // center on screen
        frame.setVisible(true);
    }
}