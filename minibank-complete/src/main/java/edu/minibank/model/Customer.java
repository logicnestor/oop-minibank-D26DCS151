package edu.minibank.model;

import java.io.Serializable;

public class Customer implements Cloneable, Serializable {
    private static final long serialVersionUID = 1L;
    private static long customerCounter = 100;
    private final String customerId;
    private String name;
    private String email;
    private String mobile;
    private Address address;

    private static synchronized String generateCustomerId() {
        return "CUST" + (++customerCounter);
    }

    public Customer(String name, String email, String mobile) {
        this(name, email, mobile, null);
    }

    public Customer(String name, String email, String mobile, Address address) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Name is required");
        this.customerId = generateCustomerId();
        this.name = name.trim();
        this.email = email;
        this.mobile = mobile;
        this.address = address;
    }

    public String getCustomerId() { return customerId; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getMobile() { return mobile; }
    public Address getAddress() { return address; }

    @Override public Customer clone() {
        try {
            Customer copy = (Customer) super.clone();
            if (address != null) copy.address = address.clone();
            return copy;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }

    public static class Address implements Cloneable, Serializable {
        private static final long serialVersionUID = 1L;
        private String line;
        private String city;
        private String pincode;

        public Address(String line, String city, String pincode) {
            this.line = line; this.city = city; this.pincode = pincode;
        }
        public String getLine() { return line; }
        public String getCity() { return city; }
        public String getPincode() { return pincode; }
        @Override public Address clone() {
            try { return (Address) super.clone(); }
            catch (CloneNotSupportedException e) { throw new AssertionError(e); }
        }
    }
}
