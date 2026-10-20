package edu.minibank;

import edu.minibank.exception.BankException;
import edu.minibank.model.*;
import edu.minibank.service.*;
import edu.minibank.util.CommandParser;
import edu.minibank.util.Validator;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Scanner;

public class MiniBank {
    private record BankInfo(String name, String branch) {}
    private enum MenuOption { OPEN_ACCOUNT, DEPOSIT, WITHDRAW, TRANSFER, STATEMENT, LIST, REPORT, CONCURRENCY, EXIT }

    public static void main(String[] args) {
        Path state = Path.of("data", "accounts.ser");
        Path logs = Path.of("logs", "transactions.log");
        BankService bank = new BankService(logs);
        try { bank.loadState(state); }
        catch (IOException e) { System.out.println("Could not load saved state: " + e.getMessage()); }

        System.out.println(new BankInfo("MiniBank", "Vadodara Main Branch"));
        try (Scanner scanner = new Scanner(System.in)) {
            boolean running = true;
            while (running) {
                printMenu();
                System.out.print("Choose: ");
                String choice = scanner.nextLine().trim();
                try {
                    MenuOption option = switch (choice) {
                        case "1" -> MenuOption.OPEN_ACCOUNT;
                        case "2" -> MenuOption.DEPOSIT;
                        case "3" -> MenuOption.WITHDRAW;
                        case "4" -> MenuOption.TRANSFER;
                        case "5" -> MenuOption.STATEMENT;
                        case "6" -> MenuOption.LIST;
                        case "7" -> MenuOption.REPORT;
                        case "8" -> MenuOption.CONCURRENCY;
                        case "9" -> MenuOption.EXIT;
                        default -> null;
                    };
                    if (option == null) { System.out.println("Invalid menu option."); continue; }
                    switch (option) {
                        case OPEN_ACCOUNT -> {
                            System.out.print("Owner name: "); String owner = scanner.nextLine();
                            System.out.print("Type (SAVINGS/CURRENT/FD): "); String type = scanner.nextLine().trim();
                            long opening = readLong(scanner, "Opening balance (₹): ");
                            Account a = bank.openAccount(owner, type, opening);
                            System.out.println("Opened: " + a);
                        }
                        case DEPOSIT -> {
                            System.out.print("Account number: "); String id = scanner.nextLine().trim();
                            long amount = readLong(scanner, "Amount (₹): ");
                            bank.deposit(id, amount); System.out.println("Deposit successful.");
                        }
                        case WITHDRAW -> {
                            System.out.print("Account number: "); String id = scanner.nextLine().trim();
                            long amount = readLong(scanner, "Amount (₹): ");
                            bank.withdraw(id, amount); System.out.println("Withdrawal successful.");
                        }
                        case TRANSFER -> {
                            System.out.print("From account: "); String from = scanner.nextLine().trim();
                            System.out.print("To account: "); String to = scanner.nextLine().trim();
                            long amount = readLong(scanner, "Amount (₹): ");
                            bank.transfer(from, to, amount); System.out.println("Transfer successful.");
                        }
                        case STATEMENT -> {
                            System.out.print("Account number: ");
                            System.out.println(bank.statement(scanner.nextLine().trim()));
                        }
                        case LIST -> bank.listAccounts().forEach(System.out::println);
                        case REPORT -> {
                            String report = new ReportGenerator().generate(Path.of("logs"), Path.of("reports", "end-of-day.txt"));
                            System.out.println(report);
                        }
                        case CONCURRENCY -> runConcurrentDemo(bank);
                        case EXIT -> {
                            bank.saveState(state);
                            System.out.println("State saved. Goodbye!");
                            running = false;
                        }
                    }
                } catch (BankException | IllegalArgumentException | IOException e) {
                    System.out.println("Operation failed: " + e.getMessage());
                }
            }
        }
    }

    private static long readLong(Scanner scanner, String prompt) {
        System.out.print(prompt);
        try { return Long.parseLong(scanner.nextLine().trim()); }
        catch (NumberFormatException e) { throw new IllegalArgumentException("Enter a whole-number amount."); }
    }

    private static void printMenu() {
        System.out.println("\n===== MINIBANK =====");
        System.out.println("1. Open account");
        System.out.println("2. Deposit");
        System.out.println("3. Withdraw");
        System.out.println("4. Transfer");
        System.out.println("5. Account statement");
        System.out.println("6. List accounts (sorted by balance)");
        System.out.println("7. End-of-day report");
        System.out.println("8. Run sample concurrent deposits");
        System.out.println("9. Exit");
    }

    private static void runConcurrentDemo(BankService bank) throws BankException {
        Account a = bank.openAccount("Concurrency Demo", "CURRENT", 0);
        int threads = 10, times = 1000;
        try (TransactionProcessor processor = new TransactionProcessor()) {
            for (int i = 0; i < threads; i++) {
                processor.submit(() -> {
                    for (int j = 0; j < times; j++) {
                        try { a.deposit(1); }
                        catch (Exception e) { throw new RuntimeException(e); }
                    }
                });
            }
        }
        System.out.println("Concurrent deposit result: " + a.getBalance()
                + " (expected " + (threads * times) + ")");
    }
}
