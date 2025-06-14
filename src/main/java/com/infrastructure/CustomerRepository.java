package com.infrastructure;

import com.entities.Customer;

import java.util.List;

public interface CustomerRepository {
    Customer findById(int id);
    List<Customer> getAll();
}
