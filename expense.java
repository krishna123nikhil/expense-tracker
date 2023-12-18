package p;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class expense {

    private static final List<Expense> expenses = new ArrayList<>();
    private static final Map<String, Double> categoryTotals = new HashMap<>();

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Expense Tracker");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 300);

            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

            JButton addExpenseButton = new JButton("Add Expense");
            JButton viewExpensesButton = new JButton("View Expenses");
            JButton viewSummariesButton = new JButton("View Summaries");
            JButton exitButton = new JButton("Exit");

            addExpenseButton.addActionListener(e -> addExpense());
            viewExpensesButton.addActionListener(e -> viewExpenses());
            viewSummariesButton.addActionListener(e -> viewExpenseSummaries());
            exitButton.addActionListener(e -> {
                System.out.println("Exiting Expense Tracker. Goodbye!");
                System.exit(0);
            });

            mainPanel.add(addExpenseButton);
            mainPanel.add(viewExpensesButton);
            mainPanel.add(viewSummariesButton);
            mainPanel.add(exitButton);

            frame.getContentPane().add(BorderLayout.CENTER, mainPanel);
            frame.setVisible(true);
        });
    }

    private static void addExpense() {
        String description = JOptionPane.showInputDialog("Enter expense description:");
        String amountStr = JOptionPane.showInputDialog("Enter expense amount:");
        String category = JOptionPane.showInputDialog("Enter expense category:");

        try {
            double amount = Double.parseDouble(amountStr);
            if (amount > 0) {
                Expense newExpense = new Expense(description, amount, category);
                expenses.add(newExpense);
                categoryTotals.put(category, categoryTotals.getOrDefault(category, 0.0) + amount);
                JOptionPane.showMessageDialog(null, "Expense added successfully!");
            } else {
                JOptionPane.showMessageDialog(null, "Invalid amount. Please enter a positive value.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid amount. Please enter a valid number.");
        }
    }

    private static void viewExpenses() {
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        if (expenses.isEmpty()) {
            textArea.append("No expenses recorded yet.");
        } else {
            for (Expense expense : expenses) {
                textArea.append(expense.toString() + "\n");
            }
        }
        JOptionPane.showMessageDialog(null, new JScrollPane(textArea), "Expense List", JOptionPane.PLAIN_MESSAGE);
    }

    private static void viewExpenseSummaries() {
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        if (categoryTotals.isEmpty()) {
            textArea.append("No expenses recorded yet.");
        } else {
            for (Map.Entry<String, Double> entry : categoryTotals.entrySet()) {
                textArea.append("Category: " + entry.getKey() + ", Total: " + entry.getValue() + "\n");
            }
        }
        JOptionPane.showMessageDialog(null, new JScrollPane(textArea), "Expense Summaries", JOptionPane.PLAIN_MESSAGE);
    }

    private static class Expense {
        private final String description;
        private final double amount;
        private final String category;

        public Expense(String description, double amount, String category) {
            this.description = description;
            this.amount = amount;
            this.category = category;
        }

        @Override
        public String toString() {
            return "Description: " + description + ", Amount: " + amount + ", Category: " + category;
        }
    }
}
