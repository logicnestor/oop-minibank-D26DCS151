package edu.minibank.util;

import edu.minibank.model.Command;
import edu.minibank.model.TransactionType;

public final class CommandParser {
    private CommandParser() {}

    public static Command parse(String line) {
        if (line == null) throw new IllegalArgumentException("Command cannot be null");
        String[] parts = line.trim().split("\\s+");
        if (parts.length != 3) throw new IllegalArgumentException("Expected: TYPE ACCOUNT_NUMBER AMOUNT");
        final TransactionType type;
        final long amount;
        try {
            type = TransactionType.valueOf(parts[0].toUpperCase());
            amount = Long.parseLong(parts[2]);
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Invalid command type or amount", ex);
        }
        if (type == TransactionType.TRANSFER) {
            throw new IllegalArgumentException("TRANSFER requires a destination account; use the menu");
        }
        if (amount <= 0) throw new IllegalArgumentException("Amount must be positive");
        return new Command(type, parts[1].toUpperCase(), amount);
    }
}
