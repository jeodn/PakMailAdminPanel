package com.ui;

import com.model.Customer;

import javax.swing.*;
import java.awt.*;

public class CustomerDetailPanel extends JPanel {
    private final JLabel nameLabel = new JLabel();
    private final JLabel emailLabel = new JLabel();
    private final JButton notifyBtn = new JButton("Notify Customer");

    public CustomerDetailPanel(Runnable onNotifyClicked) {
        super(new GridBagLayout());
        notifyBtn.addActionListener(e -> onNotifyClicked.run());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.WEST;
        add(new JLabel("Name: "), gbc);
        gbc.gridx = 1; add(nameLabel, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        add(new JLabel("Email: "), gbc);
        gbc.gridx = 1; add(emailLabel, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2; gbc.insets.top = 10;
        gbc.anchor = GridBagConstraints.CENTER;
        add(notifyBtn, gbc);
    }

    public void showCustomer(Customer c) {
        if (c == null) {
            nameLabel.setText("-");
            emailLabel.setText("-");
            notifyBtn.setEnabled(false);
        } else {
            nameLabel.setText(c.getName());
            emailLabel.setText(c.getEmail());
            notifyBtn.setEnabled(true);
        }
    }
}
