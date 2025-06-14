package com.infrastructure;

import com.entities.Customer;

import java.util.*;

// TODO: Mock customer repo for testing
public class InMemoryCustomerRepository implements CustomerRepository {
    private final Map<Integer, Customer> customers = new HashMap<>();

    public InMemoryCustomerRepository() {
        customers.put(34, new Customer(34, "Joshy Poo", "joshdrvillas@gmail.com"));
        customers.put(99, new Customer(99, "Acid Josh", "acidjoshton@gmail.com"));
    }

    @Override
    public Customer findById(int customerId) {
        if (!customers.containsKey(customerId)) {
            throw new IllegalArgumentException("Customer ID not found: " + customerId);
        }
        return customers.get(customerId);
    }

    @Override
    public List<Customer> getAll() {
        return new ArrayList<>(customers.values());
    }

    public void addCustomer(Customer customer) {
        customers.put(customer.getId(), customer);
    }
}
