package com.interfaceadapters;

import com.entities.Customer;
import com.infrastructure.CustomerRepository;

import java.util.ArrayList;
import java.util.List;

public class CustomerListPresenter {

    private final CustomerRepository customerRepo;

    public CustomerListPresenter(CustomerRepository customerRepo) {
        this.customerRepo = customerRepo;
    }

    public List<Customer> getCustomerDisplayList() {
        List<Customer> displayList = new ArrayList<>();
        for (Customer customer : customerRepo.getAll()) {
            //displayList.add(customer.getId() + " — " + customer.getName() + " <" + customer.getEmail() + ">");
            displayList.add(customer);
        }
        return displayList;
    }
}
