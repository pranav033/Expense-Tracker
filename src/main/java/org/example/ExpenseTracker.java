package org.example;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class ExpenseTracker {
    private List<Transaction> transactions = new ArrayList<>();
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final String DEFAULT_FILE = "transactions.csv";

    public void addTransaction(Scanner sc) {
        System.out.print("Enter date (yyyy-MM-dd): ");
        LocalDate date = LocalDate.parse(sc.nextLine(), formatter);

        System.out.print("Type (INCOME/EXPENSE): ");
        String type = sc.nextLine().toUpperCase();

        System.out.print("Enter category (e.g., Salary/Business for INCOME, Food/Rent/Travel for EXPENSE): ");
        String category = sc.nextLine();

        System.out.print("Enter amount: ");
        double amount = Double.parseDouble(sc.nextLine());

        System.out.print("Enter description: ");
        String description = sc.nextLine();

        Transaction t = new Transaction(date, type, category, amount, description);
        transactions.add(t);
        System.out.println("Transaction added successfully!");

        saveToFile(DEFAULT_FILE);
    }

    public void loadFromFile(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                LocalDate date = LocalDate.parse(parts[0], formatter);
                String type = parts[1];
                String category = parts[2];
                double amount = Double.parseDouble(parts[3]);
                String description = parts[4];

                transactions.add(new Transaction(date, type, category, amount, description));
            }
            System.out.println("Data loaded successfully from file.\n");
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    public void saveToFile(String fileName) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            writer.println("DATE,TYPE,CATEGORY,AMOUNT,DESCRIPTION");
            for (Transaction t : transactions) {
                writer.println(t.date + "," + t.type + "," + t.category + "," + t.amount + "," + t.description);
            }
            System.out.println("Data saved to file: " + fileName + "\n");
        } catch (IOException e) {
            System.out.println("Error saving to file: " + e.getMessage());
        }
    }


    public void viewMonthlySummary(int year, int month) {
        double incomeTotal = 0, expenseTotal = 0;
        System.out.println("\nMonthly Summary for " + year + "-" + String.format("%02d", month));
        for (Transaction t : transactions) {
            if (t.date.getYear() == year && t.date.getMonthValue() == month) {
                System.out.println(t);
                if (t.type.equals("INCOME")) incomeTotal += t.amount;
                else if (t.type.equals("EXPENSE")) expenseTotal += t.amount;
            }
        }
        System.out.println("Total Income: ₹" + incomeTotal);
        System.out.println("Total Expense: ₹" + expenseTotal);
        System.out.println("Net Savings: ₹" + (incomeTotal - expenseTotal) + "\n");
    }
}
