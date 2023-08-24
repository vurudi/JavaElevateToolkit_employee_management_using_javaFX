package com.taskproject.task;

/**
 * The Customer class represents a customer with its associated properties.
 */
public class Customer {
    private int customerId;
    private String customerName;
    private String address;
    private String postalCode;
    private String phone;
    private String country;
    private String division;

    /**
     * Constructs a Customer object with the specified properties.
     *
     * @param customerId    the ID of the customer
     * @param customerName  the name of the customer
     * @param address       the address of the customer
     * @param postalCode    the postal code of the customer
     * @param phone         the phone number of the customer
     * @param country       the country of the customer
     * @param division      the division of the customer
     */
    public Customer(int customerId, String customerName, String address, String postalCode, String phone, String country, String division) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.country = country;
        this.division = division;
    }

    /**
     * Returns the ID of the customer.
     *
     * @return the ID of the customer
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * Returns the name of the customer.
     *
     * @return the name of the customer
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * Returns the address of the customer.
     *
     * @return the address of the customer
     */
    public String getAddress() {
        return address;
    }

    /**
     * Returns the postal code of the customer.
     *
     * @return the postal code of the customer
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * Returns the phone number of the customer.
     *
     * @return the phone number of the customer
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Returns the country of the customer.
     *
     * @return the country of the customer
     */
    public String getCountry() {
        return country;
    }

    /**
     * Returns the division of the customer.
     *
     * @return the division of the customer
     */
    public String getDivision() {
        return division;
    }
}
