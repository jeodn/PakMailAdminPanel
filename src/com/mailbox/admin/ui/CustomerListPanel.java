package ui;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.List;
import model.Customer;

public class CustomerListPanel extends JPanel {
    private final JTable table;
    private final CustomerTableModel model;

    public CustomerListPanel(List<Customer> customers, ListSelectionListener onRowSelect) {
        super(new BorderLayout());
        model = new CustomerTableModel(customers);
        table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getSelectionModel().addListSelectionListener(onRowSelect);
        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    public Customer getSelectedCustomer() {
        int row = table.getSelectedRow();
        return row == -1 ? null : model.getCustomer(row);
    }
}
