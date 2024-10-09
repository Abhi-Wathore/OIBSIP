import java.util.ArrayList;
import java.util.Scanner;

// Class for representing a Transaction

class Transaction {
    private String type;
    private double amount;
    private double remainingBalance;

    public Transaction(String type, double amount, double remainingBalance) {
        this.type = type;
        this.amount = amount;
        this.remainingBalance = remainingBalance;
    }

    @Override
    public String toString() {
        return "Transaction: " + type + " | Amount: " + amount + " | Remaining Balance : " + remainingBalance;
    }
}

// Class for managing Transaction History
class TransactionHistory {
    private ArrayList<Transaction> transactions;

    public TransactionHistory() {
        transactions = new ArrayList<>();
    }

    public void addTransaction(String type, double amount, double remainingBalance) {
        transactions.add(new Transaction(type, amount, remainingBalance));
    }

    public void showHistory() {
        if (transactions.isEmpty()) {
            System.out.println("No transactions made yet.");
        } else {
            for (Transaction transaction : transactions) {
                System.out.println(transaction);
            }
        }
    }
}

// Class representing a User Account
class Account {
    String userId;
    private String pin;
    private double balance;
    private TransactionHistory transactionHistory;

    public Account(String userId, String pin, double initialBalance) {
        this.userId = userId;
        this.pin = pin;
        this.balance = initialBalance;
        this.transactionHistory = new TransactionHistory();
    }

    public boolean authenticate(String enteredPin) {
        return this.pin.equals(enteredPin);
    }

    public double getBalance() {
        return balance;
    }

    public void withdraw(double amount) {
        if (amount <= balance) {
            balance -= amount;
            System.out.println("Successfully withdrew: " + amount);
            transactionHistory.addTransaction("Withdraw", amount, balance);
        } else {
            System.out.println("Insufficient balance.");
        }
    }

    public void deposit(double amount) {
        balance += amount;
        System.out.println("Successfully deposited: " + amount);
        transactionHistory.addTransaction("Deposit", amount, balance);
    }

    public void transfer(Account recipient, double amount) {
        if (amount <= balance) {
            balance -= amount;
            recipient.balance += amount;
            System.out.println("Successfully transferred: " + amount + " to " + recipient.userId);
            transactionHistory.addTransaction("Transfer", amount, balance);
        } else {
            System.out.println("Insufficient balance for transfer.");
        }
    }

    public void showTransactionHistory() {
        transactionHistory.showHistory();
    }
}

// Main ATM class handling user interaction
public class ATMSystem {
    private static Account[] accounts = new Account[2];
    private static Account loggedInAccount = null;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Predefined users for demo purposes
        accounts[0] = new Account("user1", "1234", 10000);
        accounts[1] = new Account("user2", "1234", 20000);

        System.out.println("Welcome to the ATM System!");

        // User authentication
        while (loggedInAccount == null) {
            System.out.print("Enter user ID: ");
            String userId = scanner.nextLine();
            System.out.print("Enter PIN: ");
            String pin = scanner.nextLine();
            
            loggedInAccount = login(userId, pin);
            if (loggedInAccount == null) {
                System.out.println("Invalid credentials, please try again.");
            }
        }

        System.out.println("Login successful!");

        // ATM functionalities
        boolean sessionActive = true;
        while (sessionActive) {
            System.out.println("\nATM Menu:");
            System.out.println("1. Transaction History");
            System.out.println("2. Withdraw");
            System.out.println("3. Deposit");
            System.out.println("4. Transfer");
            System.out.println("5. Quit");
            System.out.print("Choose an option: ");
            int option = scanner.nextInt();

            switch (option) {
                case 1:
                    loggedInAccount.showTransactionHistory();
                    break;
                case 2:
                    System.out.print("Enter amount to withdraw: ");
                    double withdrawAmount = scanner.nextDouble();
                    loggedInAccount.withdraw(withdrawAmount);
                    break;
                case 3:
                    System.out.print("Enter amount to deposit: ");
                    double depositAmount = scanner.nextDouble();
                    loggedInAccount.deposit(depositAmount);
                    break;
                case 4:
                    System.out.print("Enter recipient user ID: ");
                    scanner.nextLine(); // Consume newline
                    String recipientId = scanner.nextLine();
                    System.out.print("Enter amount to transfer: ");
                    double transferAmount = scanner.nextDouble();
                    Account recipient = findAccountById(recipientId);
                    if (recipient != null) {
                        loggedInAccount.transfer(recipient, transferAmount);
                    } else {
                        System.out.println("Recipient not found.");
                    }
                    break;
                case 5:
                    System.out.println("Exiting session. Thank you for using the ATM.");
                    sessionActive = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }

        scanner.close();
    }

    // Method to authenticate and return the logged-in account
    public static Account login(String userId, String pin) {
        for (Account account : accounts) {
            if (account != null && account.authenticate(pin) && account.getBalance() >= 0) {
                return account;
            }
        }
        return null;
    }

    // Method to find an account by user ID
    public static Account findAccountById(String userId) {
        for (Account account : accounts) {
            if (account != null && account.getBalance() >= 0 && account.userId.equals(userId)) {
                return account;
            }
        }
        return null;
    }
}
