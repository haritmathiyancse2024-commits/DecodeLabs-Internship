import java.util.Scanner;

// ============================================================
//  BankAccount Class — The Data Vault (Business Logic)
// ============================================================
class BankAccount {

    private String accountNumber;
    private String accountHolder;
    private double balance;

    // Constructor
    public BankAccount(String accountNumber, String accountHolder, double initialBalance) {
        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
        this.balance       = initialBalance;
    }

    // Deposit
    public boolean deposit(double amount) {
        if (amount <= 0) return false;
        balance += amount;
        return true;
    }

    // Withdraw
    public boolean withdraw(double amount) {
        if (amount <= 0)        return false;
        if (amount > balance)   return false;
        balance -= amount;
        return true;
    }

    // Getters
    public double getBalance()        { return balance; }
    public String getAccountNumber()  { return accountNumber; }
    public String getAccountHolder()  { return accountHolder; }
}


// ============================================================
//  ATM Class — The Customer Lobby (UI Logic)
// ============================================================
class ATM {

    private BankAccount account;
    private Scanner      scanner;

    public ATM(BankAccount account) {
        this.account = account;
        this.scanner = new Scanner(System.in);
    }

    // Display welcome banner
    private void showBanner() {
        System.out.println("╔══════════════════════════════════════════════╗");
        System.out.println("║       DECODELABS ATM INTERFACE  v3.0         ║");
        System.out.println("║       Powered by DecodeLabs | Batch 2026     ║");
        System.out.println("╚══════════════════════════════════════════════╝");
    }

    // Display main menu
    private void showMenu() {
        System.out.println("\n┌─────────────────────────────────┐");
        System.out.println("│           MAIN MENU             │");
        System.out.println("├─────────────────────────────────┤");
        System.out.println("│  1. Check Balance               │");
        System.out.println("│  2. Deposit                     │");
        System.out.println("│  3. Withdraw                    │");
        System.out.println("│  4. Exit                        │");
        System.out.println("└─────────────────────────────────┘");
        System.out.print("Enter your choice: ");
    }

    // Check balance
    private void checkBalance() {
        System.out.println("\n──────────────────────────────────");
        System.out.println("  Account  : " + account.getAccountNumber());
        System.out.println("  Holder   : " + account.getAccountHolder());
        System.out.printf ("  Balance  : Rs %.2f%n", account.getBalance());
        System.out.println("──────────────────────────────────");
    }

    // Deposit
    private void deposit() {
        System.out.print("\nEnter deposit amount: Rs ");
        double amount = getValidAmount();
        if (account.deposit(amount)) {
            System.out.printf("✅ Rs %.2f deposited successfully!%n", amount);
            System.out.printf("   New Balance: Rs %.2f%n", account.getBalance());
        } else {
            System.out.println("❌ Invalid amount. Must be greater than zero.");
        }
    }

    // Withdraw
    private void withdraw() {
        System.out.print("\nEnter withdrawal amount: Rs ");
        double amount = getValidAmount();
        if (account.withdraw(amount)) {
            System.out.printf("✅ Rs %.2f withdrawn successfully!%n", amount);
            System.out.printf("   Remaining Balance: Rs %.2f%n", account.getBalance());
        } else if (amount <= 0) {
            System.out.println("❌ Invalid amount. Must be greater than zero.");
        } else {
            System.out.println("❌ Insufficient funds!");
            System.out.printf ("   Available Balance: Rs %.2f%n", account.getBalance());
        }
    }

    // Safe input — never crashes on bad input
    private double getValidAmount() {
        while (!scanner.hasNextDouble()) {
            System.out.print("   ⚠ Invalid input. Enter a number: Rs ");
            scanner.next(); // clear bad token
        }
        return scanner.nextDouble();
    }

    // Safe menu choice input
    private int getMenuChoice() {
        while (!scanner.hasNextInt()) {
            System.out.print("   ⚠ Invalid input. Enter 1-4: ");
            scanner.next();
        }
        return scanner.nextInt();
    }

    // Main run loop
    public void run() {
        showBanner();
        System.out.println("\nWelcome, " + account.getAccountHolder() + "!");

        boolean running = true;
        while (running) {
            showMenu();
            int choice = getMenuChoice();
            switch (choice) {
                case 1: checkBalance(); break;
                case 2: deposit();      break;
                case 3: withdraw();     break;
                case 4:
                    System.out.println("\n👋 Thank you for using DecodeLabs ATM!");
                    System.out.println("   Please collect your card. Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("❌ Invalid choice. Please enter 1 to 4.");
            }
        }
        scanner.close();
    }
}


// ============================================================
//  Main Class — Entry Point
// ============================================================
public class ATMInterface {
    public static void main(String[] args) {

        // Create a bank account with starter balance
        BankAccount myAccount = new BankAccount("DL-2026-001", "Harit Mathiyan", 10000.00);

        // Create ATM and pass the account
        ATM atm = new ATM(myAccount);

        // Start the ATM
        atm.run();
    }
}