package com.ui;

import com.model.Customer;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class CustomerTableModel extends AbstractTableModel {
    private final List<Customer> customers;
    private static final String[] COLS = {"Name", "Email"};

    public CustomerTableModel(List<Customer> customers) {
        this.customers = customers;
    }

    @Override public int getRowCount() { return customers.size(); }
    @Override public int getColumnCount() { return COLS.length; }
    @Override public String getColumnName(int c) { return COLS[c]; }

    @Override
    public Object getValueAt(int row, int col) {
        Customer c = customers.get(row);
        return col == 0 ? c.getName() : c.getEmail();
    }

    public Customer getCustomer(int row) { return customers.get(row); }
}
