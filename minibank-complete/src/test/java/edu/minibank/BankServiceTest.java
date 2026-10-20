package edu.minibank;

import edu.minibank.exception.BankException;
import edu.minibank.exception.InsufficientFundsException;
import edu.minibank.model.Account;
import edu.minibank.service.BankService;
import org.junit.jupiter.api.Test;
import java.nio.file.Files;
import java.nio.file.Path;
import static org.junit.jupiter.api.Assertions.*;

class BankServiceTest {
    @Test void depositIncreasesBalance() throws Exception {
        Path dir = Files.createTempDirectory("minibank-test");
        BankService service = new BankService(dir.resolve("transactions.log"));
        Account account = service.openAccount("Asha", "CURRENT", 100);
        service.deposit(account.getAccountNumber(), 50);
        assertEquals(150, account.getBalance());
    }

    @Test void withdrawalBeyondAvailableBalanceThrows() throws Exception {
        Path dir = Files.createTempDirectory("minibank-test");
        BankService service = new BankService(dir.resolve("transactions.log"));
        Account account = service.openAccount("Asha", "SAVINGS", 100);
        assertThrows(InsufficientFundsException.class,
                () -> service.withdraw(account.getAccountNumber(), 150));
        assertEquals(100, account.getBalance());
    }

    @Test void saveAndReloadPreservesAccountData() throws Exception {
        Path dir = Files.createTempDirectory("minibank-state-test");
        BankService service = new BankService(dir.resolve("transactions.log"));
        Account original = service.openAccount("Persisted User", "CURRENT", 250);
        Path state = dir.resolve("accounts.ser");
        service.saveState(state);

        BankService restored = new BankService(dir.resolve("restored.log"));
        restored.loadState(state);
        Account loaded = restored.require(original.getAccountNumber());

        assertEquals(original.getAccountNumber(), loaded.getAccountNumber());
        assertEquals(original.getOwnerName(), loaded.getOwnerName());
        assertEquals(original.getBalance(), loaded.getBalance());
    }

    @Test void concurrentDepositsKeepCorrectBalance() throws Exception {
        Path dir = Files.createTempDirectory("minibank-test");
        BankService service = new BankService(dir.resolve("transactions.log"));
        Account account = service.openAccount("Test", "CURRENT", 0);
        int threads = 8, deposits = 1000;
        Thread[] workers = new Thread[threads];
        for (int i = 0; i < threads; i++) {
            workers[i] = new Thread(() -> {
                for (int j = 0; j < deposits; j++) {
                    try { account.deposit(1); }
                    catch (Exception e) { throw new RuntimeException(e); }
                }
            });
            workers[i].start();
        }
        for (Thread worker : workers) worker.join();
        assertEquals((long) threads * deposits, account.getBalance());
    }
}
