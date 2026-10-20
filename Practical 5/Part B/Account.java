import java.util.Objects;

public abstract class Account {
    private final String accountNumber;
    private String ownerName;
    private long balance;
    private boolean active;

    private static int accountCounter = 1;

    private static String generateAccountNumber() {
        return String.format("AC%04d", accountCounter++);
    }

    // Constructor
    public Account(String ownerName, long openingBalance) {

        this.accountNumber = generateAccountNumber();
        this.ownerName = ownerName;
        this.balance = openingBalance;
        this.active = true;
    }

    // Constructor with zero opening balance
    public Account(String ownerName) {
        this(ownerName, 0);
    }

    // Deposit
    public void deposit(long amount) {

        if (amount > 0) {
            balance += amount;
        }
    }

    // Withdraw
    public boolean withdraw(long amount) {

        if (amount <= 0) {
            return false;
        }

        if (canWithdraw(amount)) {
            balance -= amount;
            return true;
        }

        return false;
    }

    // Abstract methods
    public abstract double interestRate();

    public abstract boolean canWithdraw(long amount);

    // Getters
    public String getAccountNumber() {
        return accountNumber;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public long getBalance() {
        return balance;
    }

    public boolean isActive() {
        return active;
    }

    @Override
    public String toString() {
        return "Account Number: " + accountNumber
                + ", Owner: " + ownerName
                + ", Balance: Rs." + balance;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Account account = (Account) o;

        return accountNumber.equals(account.accountNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountNumber);
    }
}