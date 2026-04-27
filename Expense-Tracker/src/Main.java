import java.io.*;
import java.util.*;

class Expense {
    String date;
    String category;
    double amount;

    Expense(String date, String category, double amount) {
        this.date = date;
        this.category = category;
        this.amount = amount;
    }

    public String toFileString() {
        return date + "," + category + "," + amount;
    }
}

public class Main {

    static final String FILE = "data/expenses.txt";
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        while (true) {
            System.out.println("\n==== SMART EXPENSE TRACKER ====");
            System.out.println("1. Add Expense");
            System.out.println("2. View Expenses");
            System.out.println("3. Total Spending");
            System.out.println("4. Category Summary");
            System.out.println("5. Exit");

            System.out.print("Choose: ");

            int choice;
            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                System.out.println("Invalid input!");
                continue;
            }

            switch (choice) {
                case 1 -> addExpense();
                case 2 -> viewExpenses();
                case 3 -> totalSpending();
                case 4 -> categorySummary();
                case 5 -> {
                    System.out.println("Exiting...");
                    return;
                }
                default -> System.out.println("Invalid choice!");
            }
        }
    }

    static void addExpense() {
        try {
            System.out.print("Enter date (YYYY-MM-DD): ");
            String date = sc.nextLine();

            System.out.print("Enter category: ");
            String category = sc.nextLine();

            System.out.print("Enter amount: ");
            double amount = Double.parseDouble(sc.nextLine());

            FileWriter fw = new FileWriter(FILE, true);
            fw.write(date + "," + category + "," + amount + "\n");
            fw.close();

            System.out.println("Expense saved");

        } catch (Exception e) {
            System.out.println("Error: Invalid input");
        }
    }

    static void viewExpenses() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(FILE));
            String line;

            System.out.println("\n--- Expenses ---");

            while ((line = br.readLine()) != null) {
                String[] p = line.split(",");
                System.out.println("Date: " + p[0] + 
                                   " | Category: " + p[1] + 
                                   " | Amount: " + p[2]);
            }

            br.close();
        } catch (Exception e) {
            System.out.println("No expenses found.");
        }
    }

    static void totalSpending() {
        double total = 0;

        try {
            BufferedReader br = new BufferedReader(new FileReader(FILE));
            String line;

            while ((line = br.readLine()) != null) {
                String[] p = line.split(",");
                total += Double.parseDouble(p[2]);
            }

            br.close();
            System.out.println("Total Spending: " + total);

        } catch (Exception e) {
            System.out.println("Error reading file.");
        }
    }

    static void categorySummary() {
        Map<String, Double> map = new HashMap<>();

        try {
            BufferedReader br = new BufferedReader(new FileReader(FILE));
            String line;

            while ((line = br.readLine()) != null) {
                String[] p = line.split(",");
                String cat = p[1];
                double amt = Double.parseDouble(p[2]);

                map.put(cat, map.getOrDefault(cat, 0.0) + amt);
            }

            br.close();

            System.out.println("\n--- Category Summary ---");
            for (String key : map.keySet()) {
                System.out.println(key + " : " + map.get(key));
            }

        } catch (Exception e) {
            System.out.println("Error generating summary.");
        }
    }
}