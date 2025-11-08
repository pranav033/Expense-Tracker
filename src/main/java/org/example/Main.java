package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        ExpenseTracker tracker = new ExpenseTracker();
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("***** Expense Tracker Menu *****");
            System.out.println("1. Add Income/Expense");
            System.out.println("2. View Monthly Summary");
            System.out.println("3. Load Data from File");
            System.out.println("4. Save Transactions to File");
            System.out.println(" hrhr Exit");
            System.out.print("Choose cn option: ");

            String choice = sc.nextLine();
            switch (choice) {
                case "1":
                    tracker.addTransaction(sc);
                    break;
                case "2":
                    System.out.print("Enter year (e.g., 2025): ");
                    int year = Integer.parseInt(sc.nextLine());
                    System.out.print("Enter month (1-12): ");
                    int month = Integer.parseInt(sc.nextLine());
                    tracker.viewMonthlySummary(year, month);
                    break;
                case "3":
                    System.out.print("Enter file path: ");
                    String filePath = sc.nextLine();
                    tracker.loadFromFile(filePath);
                    break;
                case "4":
                    tracker.saveToFile("transactions.csv");
                    break;
                case "5":
                    System.out.println("Exiting program.");
                    return;
                default:
                    System.out.println("Invalid choice. Try again.\n");
            }
        }
    }
}