package com.usecases;

import com.entities.Customer;
import com.entities.MailItem;
import com.infrastructure.CustomerRepository;
import com.infrastructure.EmailService;
import com.infrastructure.MailRepository;
import jakarta.mail.MessagingException;

import java.io.IOException;

public class NotifyCustomerOfMailInteractor implements NotifyCustomerOfMailUseCase {
    private final MailRepository mailRepo;
    private final CustomerRepository customerRepo;
    private final EmailService emailService;

    public NotifyCustomerOfMailInteractor(MailRepository mailRepo, CustomerRepository customerRepo, EmailService emailService) {
        this.mailRepo = mailRepo;
        this.customerRepo = customerRepo;
        this.emailService = emailService;
    }

    @Override
    public void notifyCustomer(MailItem mailItem) throws MessagingException, IOException {
        Customer customer = customerRepo.findById(mailItem.getCustomerId());
        mailRepo.save(mailItem);
        emailService.sendNotification(customer, mailItem);
    }
}
