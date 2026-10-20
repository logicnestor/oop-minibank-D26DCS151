public class MiniBank {

    public static void main(String[] args) {

        Customer.Address address =
                new Customer.Address(
                        "12 MG Road",
                        "Ahmedabad",
                        "380001");

        Customer customer =
                new Customer(
                        "Rahul",
                        "rahul@gmail.com",
                        "9876543210",
                        address);

        Customer copiedCustomer = customer.clone();

        Account account1 =
                new Account(customer.getName(), 5000);

        Account account2 =
                new Account(customer.getName(), 5000);

        System.out.println("----- Accounts -----");

        System.out.println(account1);
        System.out.println(account2);

        System.out.println();

        System.out.println("Are accounts equal?");
        System.out.println(account1.equals(account2));

        System.out.println();

        Object obj = account1;

        if (obj instanceof Account) {
            System.out.println("obj is an Account object.");
        }

        System.out.println();

        System.out.println("Customer ID : " + customer.getCustomerId());
        System.out.println("Customer Name : " + customer.getName());
        System.out.println("Address : " + customer.getAddress());

        System.out.println();

        System.out.println("Cloned Customer");
        System.out.println(copiedCustomer.getName());
        System.out.println(copiedCustomer.getAddress());
    }
}