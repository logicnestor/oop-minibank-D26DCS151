public class MiniBank {

    public static void main(String[] args) {

        Account[] accounts = {

            new SavingsAccount(
                "Rahul",
                10000,
                2000
            ),

            new CurrentAccount(
                "Priya",
                5000,
                10000
            ),

            new FixedDepositAccount(
                "Amit",
                20000
            )
        };

        System.out.println("===== MINI BANK ACCOUNTS =====");

        for (Account account : accounts) {

            System.out.println();

            System.out.println(account);

            System.out.println(
                "Interest Rate: "
                + account.interestRate()
                + "%"
            );

            System.out.println(
                "Can withdraw Rs. 3000: "
                + account.canWithdraw(3000)
            );

            // Pattern instanceof
            if (account instanceof SavingsAccount savings) {

                System.out.println(
                    "Type: Savings Account"
                );

                System.out.println(
                    "Minimum Balance: Rs. "
                    + savings.getMinBalance()
                );

            } else if (account instanceof CurrentAccount current) {

                System.out.println(
                    "Type: Current Account"
                );

                System.out.println(
                    "Overdraft Limit: Rs. "
                    + current.getOverdraftLimit()
                );

            } else if (account instanceof FixedDepositAccount) {

                System.out.println(
                    "Type: Fixed Deposit Account"
                );
            }
        }

        System.out.println("\n===== WITHDRAWAL TEST =====");

        Account savings = accounts[0];
        Account current = accounts[1];
        Account fixedDeposit = accounts[2];

        System.out.println(
            "Savings withdraw 9000: "
            + savings.withdraw(9000)
        );

        System.out.println(
            "Savings balance: Rs. "
            + savings.getBalance()
        );

        System.out.println(
            "Current withdraw 14000: "
            + current.withdraw(14000)
        );

        System.out.println(
            "Current balance: Rs. "
            + current.getBalance()
        );

        System.out.println(
            "Fixed Deposit withdraw 5000: "
            + fixedDeposit.withdraw(5000)
        );

        System.out.println(
            "Fixed Deposit balance: Rs. "
            + fixedDeposit.getBalance()
        );
    }
}