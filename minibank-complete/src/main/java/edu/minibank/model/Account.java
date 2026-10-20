package edu.minibank.model;

import edu.minibank.exception.InsufficientFundsException;
import edu.minibank.exception.InvalidAmountException;
import edu.minibank.model.annotation.Id;
import edu.minibank.model.annotation.Positive;
import java.io.Serializable;
import java.util.Objects;

public abstract class Account implements Transactable, InterestBearing, Comparable<Account>, Serializable {
    private static final long serialVersionUID = 1L;
    private static long accountCounter = 0;
    @Id
    private final String accountNumber;
    private final String ownerName;
    @Positive(message = "balance must not be negative")
    private long balance;
    private boolean active = true;

    private static synchronized String generateAccountNumber() {
        return String.format("AC%04d", ++accountCounter);
    }

    /** Keeps generated account numbers unique after persisted accounts are loaded. */
    public static synchronized void observeExistingAccountNumber(String number) {
        if (number != null && number.matches("AC[0-9]{4,}")) {
            try {
                accountCounter = Math.max(accountCounter, Long.parseLong(number.substring(2)));
            } catch (NumberFormatException ignored) {
                // Ignore malformed identifiers; normal account validation handles lookup/use.
            }
        }
    }

    protected Account(String ownerName, long openingBalance) {
        if (ownerName == null || ownerName.isBlank()) throw new IllegalArgumentException("Owner name is required");
        if (openingBalance < 0) throw new IllegalArgumentException("Opening balance cannot be negative");
        this.accountNumber = generateAccountNumber();
        this.ownerName = ownerName.trim();
        this.balance = openingBalance;
    }

    protected Account(String ownerName) { this(ownerName, 0); }

    @Override public final synchronized void deposit(long amount) throws InvalidAmountException {
        if (amount <= 0) throw new InvalidAmountException("Deposit amount must be greater than zero");
        balance = Math.addExact(balance, amount);
    }

    @Override public synchronized boolean withdraw(long amount)
            throws InsufficientFundsException, InvalidAmountException {
        if (amount <= 0) throw new InvalidAmountException("Withdrawal amount must be greater than zero");
        if (!canWithdraw(amount)) {
            long shortfall = Math.max(0, amount - balance);
            throw new InsufficientFundsException("Withdrawal is not permitted or funds are insufficient", shortfall);
        }
        balance -= amount;
        return true;
    }

    public abstract boolean canWithdraw(long amount);
    @Override public abstract double interestRate();
    public final String getAccountNumber() { return accountNumber; }
    public final String getOwnerName() { return ownerName; }
    @Override public final synchronized long getBalance() { return balance; }
    public final boolean isActive() { return active; }
    public final void closeAccount() { active = false; }

    @Override public int compareTo(Account other) {
        return this.accountNumber.compareTo(other.accountNumber);
    }

    @Override public String toString() {
        return accountNumber + " | " + ownerName + " | balance=₹" + getBalance()
                + " | " + getClass().getSimpleName() + (active ? " | ACTIVE" : " | CLOSED");
    }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Account account)) return false;
        return accountNumber.equals(account.accountNumber);
    }

    @Override public int hashCode() { return Objects.hash(accountNumber); }
}
