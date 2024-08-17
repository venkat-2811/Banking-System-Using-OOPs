# Banking System Using OOPs

## Overview

The **Simple Banking System** is a Java-based console application that simulates basic banking operations. The system allows users to create bank accounts, manage existing accounts, perform deposits, withdrawals, and transfers between accounts, and view transaction histories. The application supports two types of accounts: Savings Accounts and Loan Accounts.

## Features

- **Bank and Customer Management**:
  - Add new customers to the bank.
  - View a list of all customers.

- **Account Management**:
  - Create new bank accounts for customers (Savings or Loan accounts).
  - Deposit and withdraw funds from accounts.
  - View transaction history for accounts.
  - Transfer money between accounts.

- **Transaction Management**:
  - Record transactions (Deposits, Withdrawals, Loan Repayments, Money Transfers).
  - View detailed transaction history for each account.

## Classes and Their Responsibilities

1. **Bank**:
   - Manages the bank's name and the list of customers.
   - Allows adding customers and retrieving customer information.

2. **Customer**:
   - Represents a bank customer with a unique ID, name, and a list of accounts.
   - Allows adding and managing multiple accounts for the customer.

3. **Account (Abstract Class)**:
   - Represents a general bank account with an account number, balance, and transaction history.
   - Defines abstract methods for depositing and withdrawing funds.
   - Manages money transfers between accounts.

4. **SavingAccount (Subclass of Account)**:
   - Implements deposit and withdrawal functionalities specific to a savings account.
   
5. **LoanAccount (Subclass of Account)**:
   - Implements deposit functionality for loan repayment.
   - Disables withdrawal functionality, as withdrawals are not allowed from loan accounts.

6. **Transaction**:
   - Represents a financial transaction with details such as type (e.g., Deposit, Withdrawal), amount, and the associated account.

7. **SimpleBankingSystem (Main Class)**:
   - Contains the main logic for interacting with users through a console-based interface.
   - Manages the overall flow of the application, including account creation, managing existing users, handling deposits/withdrawals, viewing transaction histories, and transferring money between accounts.

## How to Run

1. **Compile the Java Code**:
   ```
   javac SimpleBankingSystem.java
   ```

2. **Run the Program**:
   ```
   java SimpleBankingSystem
   ```

3. Follow the on-screen instructions to interact with the banking system.

## Example Use Cases

- **Creating a New Account**: Create a savings or loan account for a new customer with an initial deposit or loan amount.
- **Managing Existing Accounts**: Deposit or withdraw money from an existing account and view the transaction history.
- **Transferring Money**: Send money from one account to another within the same bank.

## Future Enhancements

- Add support for additional account types such as Checking Accounts.
- Implement more complex banking features such as interest calculation, overdrafts, and account closures.
- Enhance the user interface with a GUI-based application.
