public class MiniBank {

    public static void main(String[] args) {

        // Customers
        Customer c1 = new Customer("Rahul", "rahul@gmail.com", "9876543210");
        Customer c2 = new Customer("Priya", "priya@gmail.com", "9876500000");
        Customer c3 = new Customer("Amit", "amit@gmail.com", "9876511111");

        // Accounts
        Account[] accounts = new Account[3];

        accounts[0] = new Account(c1.getName(), 5000);
        accounts[1] = new Account(c2.getName(), 3000);
        accounts[2] = new Account(c3.getName());

        // Transactions
        accounts[0].deposit(1000);
        accounts[0].withdraw(2000);

        accounts[1].deposit(500);
        accounts[1].withdraw(1000);

        accounts[2].deposit(2000);
        accounts[2].withdraw(500);

        // Display account details
        System.out.println("========== Account Details ==========\n");

        for (int i = 0; i < accounts.length; i++) {

            System.out.println("Account Number : " + accounts[i].getAccountNumber());
            System.out.println("Owner Name     : " + accounts[i].getOwnerName());
            System.out.println("Balance        : Rs. " + accounts[i].getBalance());
            System.out.println("Account Active : " + accounts[i].isActive());

            System.out.println("-----------------------------------");
        }
    }
}