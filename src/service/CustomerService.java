package service;

import model.Customer;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * service to create and retrieve customers
 * @author Amy Lanclos
 */
public class CustomerService {
    private final Map<String, Customer> customers;
    private CustomerService() {
        customers = new HashMap<>();
    }
    private static final CustomerService instance = new CustomerService();

    /**
     *
     * @return the instance
     */
    public static CustomerService getInstance() {
        return instance;
    }

    /**
     *
     * @param email email
     * @param firstname name
     * @param lastName last name
     */
    public void addCustomer(String email, String firstname, String lastName) {
        Customer customer = new Customer(firstname,lastName,email);
        if(customers.containsKey(email)) {
            throw new IllegalArgumentException("Account for " + email +" already exists!");
        }
        customers.put(email, customer);
    }

    /**
     *
     * @param customerEmail email
     * @return this customer
     */
    public Customer getCustomer(String customerEmail) {
        return customers.getOrDefault(customerEmail, null);
    }

    /**
     *
     * @return all customers
     */
    public Collection<Customer> getAllCustomers() {
        return customers.values();
    }
}
