package com.infrastructure;

import com.entities.Customer;
import com.entities.MailItem;
import jakarta.mail.MessagingException;

import java.io.IOException;

public interface EmailService {
    void sendNotification(Customer customer, MailItem item) throws MessagingException, IOException;
}