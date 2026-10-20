package edu.minibank.util;

import edu.minibank.model.Account;

public final class StatementFormatter {
    private StatementFormatter() {}
    public static String buildStatement(Account account) {
        return new StringBuilder()
                .append("========== MINIBANK STATEMENT ==========\n")
                .append("Account Number : ").append(account.getAccountNumber()).append('\n')
                .append("Owner          : ").append(account.getOwnerName()).append('\n')
                .append("Account Type   : ").append(account.getClass().getSimpleName()).append('\n')
                .append("Balance        : ₹").append(account.getBalance()).append('\n')
                .append("Interest Rate  : ").append(account.interestRate()).append("% p.a.\n")
                .append("Yearly Estimate: ₹").append(String.format("%.2f", account.yearlyInterest())).append('\n')
                .append("Status         : ").append(account.isActive() ? "ACTIVE" : "CLOSED").append('\n')
                .append("========================================").toString();
    }
}
