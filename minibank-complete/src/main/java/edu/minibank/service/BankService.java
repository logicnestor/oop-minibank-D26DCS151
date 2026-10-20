package edu.minibank.service;

import edu.minibank.exception.*;
import edu.minibank.model.*;
import edu.minibank.model.annotation.Audit;
import edu.minibank.util.StatementFormatter;
import edu.minibank.util.Validator;
import java.io.IOException;
import java.nio.file.Path;
import java.time.Instant;
import java.util.List;

public class BankService {
    private final AccountStore store = new AccountStore();
    private final Path logFile;
    private final Repository<Account> repository = new Repository<>();

    public BankService(Path logFile) { this.logFile = logFile; }

    public synchronized Account openAccount(String owner, String type, long openingBalance)
            throws BankException {
        if (owner == null || owner.isBlank()) throw new BankException("Owner name is required");
        if (openingBalance < 0) throw new InvalidAmountException("Opening balance cannot be negative");
        Account account;
        try {
            account = switch (type.toUpperCase()) {
                case "SAVINGS" -> new SavingsAccount(owner, openingBalance, 0);
                case "CURRENT" -> new CurrentAccount(owner, openingBalance, 1000);
                case "FD", "FIXED_DEPOSIT" -> new FixedDepositAccount(owner, openingBalance);
                default -> throw new IllegalArgumentException("Unknown account type");
            };
        } catch (IllegalArgumentException ex) {
            throw new BankException(ex.getMessage());
        }
        store.add(account);
        repository.save(account);
        log("OPEN", 0, account.getAccountNumber());
        return account;
    }

    public synchronized void deposit(String id, long amount) throws BankException {
        Account account = require(id);
        account.deposit(amount);
        store.record("DEPOSIT " + id + " " + amount);
        log("DEPOSIT", amount, id);
    }

    public synchronized void withdraw(String id, long amount) throws BankException {
        Account account = require(id);
        account.withdraw(amount);
        store.record("WITHDRAW " + id + " " + amount);
        log("WITHDRAW", amount, id);
    }

    public synchronized void transfer(String fromId, String toId, long amount) throws BankException {
        if (amount <= 0) throw new InvalidAmountException("Transfer amount must be positive");
        Account from = require(fromId), to = require(toId);
        if (from.equals(to)) throw new BankException("Source and destination must differ");
        Account first = from.getAccountNumber().compareTo(to.getAccountNumber()) < 0 ? from : to;
        Account second = first == from ? to : from;
        synchronized (first) {
            synchronized (second) {
                from.withdraw(amount);
                try {
                    to.deposit(amount);
                } catch (BankException ex) {
                    // Roll back source if destination deposit fails.
                    try { from.deposit(amount); } catch (BankException rollback) { ex.addSuppressed(rollback); }
                    throw ex;
                } finally {
                    // Transaction boundary hook; the log below records only completed transfers.
                }
            }
        }
        store.record("TRANSFER " + fromId + " " + amount + " TO " + toId);
        log("TRANSFER", amount, fromId + "->" + toId);
    }

    public synchronized Account require(String id) throws AccountNotFoundException {
        Account account = store.get(id);
        if (account == null) throw new AccountNotFoundException(id);
        return account;
    }

    @Audit
    public synchronized String statement(String id) throws BankException {
        return StatementFormatter.buildStatement(require(id));
    }
    public synchronized List<Account> listAccounts() { return store.sortedByBalance(); }

    public synchronized void saveState(Path file) throws IOException {
        StatePersister.save(store.getAll().toArray(Account[]::new), file);
    }

    public synchronized void loadState(Path file) throws IOException {
        for (Account account : StatePersister.load(file)) {
            try { store.add(account); repository.save(account); }
            catch (IllegalArgumentException ignored) { /* skip duplicate persisted account */ }
        }
    }

    public String[] validate(Object obj) { return edu.minibank.util.AnnotationValidator.validate(obj); }

    private void log(String type, long amount, String id) throws BankException {
        try {
            TransactionLog.append(logFile, Instant.now() + "|" + type + "|" + amount + "|" + id);
        } catch (IOException e) {
            throw new BankException("Transaction completed but log write failed: " + e.getMessage());
        }
    }
}
