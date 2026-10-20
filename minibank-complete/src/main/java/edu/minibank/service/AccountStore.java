package edu.minibank.service;

import edu.minibank.model.Account;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class AccountStore {
    private final ConcurrentMap<String, Account> accounts = new ConcurrentHashMap<>();
    private final List<String> transactionHistory = java.util.Collections.synchronizedList(new ArrayList<>());

    public void add(Account account) {
        Account.observeExistingAccountNumber(account.getAccountNumber());
        if (accounts.putIfAbsent(account.getAccountNumber(), account) != null)
            throw new IllegalArgumentException("Duplicate account number");
    }
    public Account get(String accountNumber) { return accounts.get(accountNumber); }
    public ArrayList<Account> getAll() { return new ArrayList<>(accounts.values()); }
    public ArrayList<Account> sortedByBalance() {
        ArrayList<Account> result = getAll();
        result.sort(Comparator.comparingLong(Account::getBalance).reversed()
                .thenComparing(Account::getAccountNumber));
        return result;
    }
    public void record(String line) { transactionHistory.add(line); }
    public List<String> history() {
        synchronized (transactionHistory) { return new ArrayList<>(transactionHistory); }
    }
}
