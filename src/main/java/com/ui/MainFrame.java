package com.ui;

import com.formdev.flatlaf.FlatIntelliJLaf;
import com.entities.Customer;
import com.infrastructure.EmailService;
import com.infrastructure.InMemoryCustomerRepository;
import com.infrastructure.InMemoryMailRepository;
import com.infrastructure.JavaMailEmailService;
import com.interfaceadapters.CustomerListPresenter;
import com.interfaceadapters.NotifyMailController;
import com.usecases.NotifyCustomerOfMailInteractor;
import com.usecases.NotifyCustomerOfMailUseCase;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class MainFrame extends JFrame {
    private CustomerListPanel   listPanel;
    private final CustomerDetailPanel detailPanel;
    private final NotifyMailController controller;
    private final CustomerListPresenter presenter;

    public MainFrame(NotifyMailController controller, CustomerListPresenter presenter) {
        super("Mailbox Admin Dashboard");
        this.controller = controller;
        this.presenter = presenter;

        try {
            UIManager.setLookAndFeel( new FlatIntelliJLaf());
        } catch( Exception ex ) {
            System.err.println( "Failed to initialize LaF" );
        }

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 500);

        // Dummy data – replace with DB call
        List<Customer> customers = presenter.getCustomerDisplayList();

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
        NotifyCustomerDialog dlg = new NotifyCustomerDialog(this, controller, presenter, c);
        dlg.setTitle("Notify " + c.getName());
        dlg.setVisible(true);
        // On success, you’ll probably fire off an email / API call here.
    }

    public static void main(String[] args) {
        // Set up interface adapters
        InMemoryMailRepository mailRepo = new InMemoryMailRepository();
        InMemoryCustomerRepository customerRepo = new InMemoryCustomerRepository();
        EmailService emailService = new JavaMailEmailService();

        // Application logic
        NotifyCustomerOfMailUseCase useCase = new NotifyCustomerOfMailInteractor(mailRepo, customerRepo, emailService);
        NotifyMailController controller = new NotifyMailController(useCase);
        CustomerListPresenter presenter = new CustomerListPresenter(customerRepo);

        SwingUtilities.invokeLater(() -> new MainFrame(controller, presenter).setVisible(true));
    }
}
