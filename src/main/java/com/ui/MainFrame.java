package com.ui;

import com.model.Customer;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class MainFrame extends JFrame {
    private CustomerListPanel   listPanel;
    private final CustomerDetailPanel detailPanel;

    public MainFrame() {
        super("Mailbox Admin Dashboard");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 500);

        // Dummy data – replace with DB call
        List<Customer> customers = Arrays.asList(
                new Customer(1, "Alice Smith",  "alice@example.com"),
                new Customer(2, "Bob Johnson", "bob@example.com"),
                new Customer(3, "Carol Lin",   "carol@example.com")
        );

        detailPanel = new CustomerDetailPanel(() -> openNotifyDialog());
        listPanel   = new CustomerListPanel(customers, e -> {
            if (!e.getValueIsAdjusting())
                detailPanel.showCustomer(listPanel.getSelectedCustomer());
        });

        JSplitPane split =
                new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, listPanel, detailPanel);
        split.setDividerLocation(300);
        add(split, BorderLayout.CENTER);
    }

    private void openNotifyDialog() {
        Customer c = listPanel.getSelectedCustomer();
        if (c == null) return;
        NotifyCustomerDialog dlg = new NotifyCustomerDialog(this);
        dlg.setTitle("Notify " + c.getName());
        dlg.setVisible(true);
        // On success, you’ll probably fire off an email / API call here.
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true));
    }
}
