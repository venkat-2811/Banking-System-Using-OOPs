import java.util.ArrayList;
import java.util.Scanner;

// Bank class
class Bank {
    private String name;
    private ArrayList<Customer> customers;

    public Bank(String name) {
        this.name = name;
        this.customers = new ArrayList<>();
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public Customer getCustomer(String customerId) {
        for (Customer customer : customers) {
            if (customer.getId().equals(customerId)) {
                return customer;
            }
        }
        return null;
    }

    public void displayCustomers() {
        if (customers.isEmpty()) {
            System.out.println("No customers in the bank.");
        } else {
            for (Customer customer : customers) {
                System.out.println("Customer ID: " + customer.getId() + ", Name: " + customer.getName());
            }
        }
    }

    public ArrayList<Customer> getCustomerList() {
        return customers;
    }
}

// Customer class
class Customer {
    private String id;
    private String name;
    private ArrayList<Account> accounts;

    public Customer(String id, String name) {
        this.id = id;
        this.name = name;
        this.accounts = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void addAccount(Account account) {
        accounts.add(account);
    }

    public ArrayList<Account> getAccounts() {
        return accounts;
    }
}

// Abstract Account class
abstract class Account {
    protected String accountNumber;
    protected double balance;
    protected ArrayList<Transaction> transactions;

    public Account(String accountNumber, double initialBalance) {
        this.accountNumber = accountNumber;
        this.balance = initialBalance;
        this.transactions = new ArrayList<>();
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public abstract void deposit(double amount);

    public abstract void withdraw(double amount);

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    public void displayTransactionHistory() {
        if (transactions.isEmpty()) {
            System.out.println("No transactions found.");
        } else {
            for (Transaction transaction : transactions) {
                System.out.println(transaction);
            }
        }
    }

    public void sendMoney(Account recipient, double amount) {
        if (this.balance >= amount) {
            this.withdraw(amount);
            recipient.deposit(amount);
            System.out.println("Successfully sent " + amount + " to " + recipient.getAccountNumber());
            this.addTransaction(new Transaction("SEND MONEY", amount, this));
            recipient.addTransaction(new Transaction("RECEIVE MONEY", amount, recipient));
        } else {
            System.out.println("Insufficient balance to send money.");
        }
    }
}

// SavingAccount class
class SavingAccount extends Account {

    public SavingAccount(String accountNumber, double initialBalance) {
        super(accountNumber, initialBalance);
    }

    @Override
    public void deposit(double amount) {
        balance += amount;
        System.out.println("Deposited: " + amount);
        addTransaction(new Transaction("DEPOSIT", amount, this));
    }

    @Override
    public void withdraw(double amount) {
        if (balance >= amount) {
            balance -= amount;
            System.out.println("Withdrew: " + amount);
            addTransaction(new Transaction("WITHDRAW", amount, this));
        } else {
            System.out.println("Insufficient balance");
        }
    }
}

// LoanAccount class
class LoanAccount extends Account {

    public LoanAccount(String accountNumber, double initialLoanAmount) {
        super(accountNumber, -initialLoanAmount);
    }

    @Override
    public void deposit(double amount) {
        balance += amount;
        System.out.println("Loan repayment of: " + amount);
        addTransaction(new Transaction("LOAN REPAYMENT", amount, this));
    }

    @Override
    public void withdraw(double amount) {
        System.out.println("Cannot withdraw from a loan account");
    }
}

// Transaction class
class Transaction {
    private String transactionType;
    private double amount;
    private Account account;

    public Transaction(String transactionType, double amount, Account account) {
        this.transactionType = transactionType;
        this.amount = amount;
        this.account = account;
    }

    @Override
    public String toString() {
        return "Transaction Type: " + transactionType + ", Amount: " + amount + ", Account: " + account.getAccountNumber();
    }
}

// Main class
public class SimpleBankingSystem {
    private static Scanner scanner = new Scanner(System.in);
    private static Bank bank = new Bank("MyBank");

    public static void main(String[] args) {
        while (true) {
            System.out.println("\nWelcome to MyBank!");
            System.out.println("1. Create Bank Account");
            System.out.println("2. Existing User");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    createBankAccount();
                    break;
                case 2:
                    existingUser();
                    break;
                case 3:
                    System.out.println("Thank you for using MyBank!");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void createBankAccount() {
        System.out.print("Enter Customer ID: ");
        String customerId = scanner.nextLine();
        System.out.print("Enter Customer Name: ");
        String customerName = scanner.nextLine();

        Customer customer = new Customer(customerId, customerName);
        bank.addCustomer(customer);

        System.out.println("Choose Account Type:");
        System.out.println("1. Saving Account");
        System.out.println("2. Loan Account");
        System.out.print("Choose an option: ");
        int accountType = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        System.out.print("Enter Initial Deposit/Loan Amount: ");
        double initialAmount = scanner.nextDouble();

        Account account = null;
        if (accountType == 1) {
            account = new SavingAccount("SA" + customerId, initialAmount);
        } else if (accountType == 2) {
            account = new LoanAccount("LA" + customerId, initialAmount);
        } else {
            System.out.println("Invalid account type.");
            return;
        }

        customer.addAccount(account);
        System.out.println("Bank account created successfully!");
    }

    private static void existingUser() {
        System.out.print("Enter Customer ID: ");
        String customerId = scanner.nextLine();

        Customer customer = bank.getCustomer(customerId);
        if (customer == null) {
            System.out.println("Customer not found.");
            return;
        }

        System.out.println("Welcome, " + customer.getName() + "!");
        System.out.println("Your Accounts:");
        ArrayList<Account> accounts = customer.getAccounts();
        for (int i = 0; i < accounts.size(); i++) {
            Account account = accounts.get(i);
            System.out.println((i + 1) + ". Account Number: " + account.getAccountNumber() + ", Balance: " + account.getBalance());
        }

        System.out.print("Choose an account to manage: ");
        int accountChoice = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        if (accountChoice < 1 || accountChoice > accounts.size()) {
            System.out.println("Invalid account selection.");
            return;
        }

        Account selectedAccount = accounts.get(accountChoice - 1);
        manageAccount(selectedAccount);
    }

    private static void manageAccount(Account account) {
        while (true) {
            System.out.println("\nManaging Account: " + account.getAccountNumber());
            System.out.println("1. Deposit");
            System.out.println("2. Withdraw");
            System.out.println("3. View Transaction History");
            System.out.println("4. Send Money to Another Account");
            System.out.println("5. Back");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    handleDeposit(account);
                    break;
                case 2:
                    handleWithdraw(account);
                    break;
                case 3:
                    account.displayTransactionHistory();
                    break;
                case 4:
                    handleSendMoney(account);
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void handleDeposit(Account account) {
        System.out.print("Enter amount to deposit: ");
        double amount = scanner.nextDouble();
        scanner.nextLine();  // Consume newline
        account.deposit(amount);
    }

    private static void handleWithdraw(Account account) {
        System.out.print("Enter amount to withdraw: ");
        double amount = scanner.nextDouble();
        scanner.nextLine();  // Consume newline
        account.withdraw(amount);
    }

    private static void handleSendMoney(Account senderAccount) {
        System.out.print("Enter recipient account number: ");
        String recipientAccountNumber = scanner.nextLine();

        Account recipientAccount = findAccountByNumber(recipientAccountNumber);
        if (recipientAccount == null) {
            System.out.println("Recipient account not found.");
            return;
        }

        System.out.print("Enter amount to send: ");
        double amount = scanner.nextDouble();
        scanner.nextLine();  // Consume newline

        senderAccount.sendMoney(recipientAccount, amount);
    }

    private static Account findAccountByNumber(String accountNumber) {
        for (Customer customer : bank.getCustomerList()) {
            for (Account account : customer.getAccounts()) {
                if (account.getAccountNumber().equals(accountNumber)) {
                    return account;
                }
            }
        }
        return null;
    }
}
