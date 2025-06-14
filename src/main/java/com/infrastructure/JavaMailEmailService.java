package com.infrastructure;

import com.entities.Customer;
import com.entities.MailItem;
import com.util.Emailer;
import jakarta.mail.MessagingException;

import java.io.IOException;

public class JavaMailEmailService implements EmailService {
    @Override
    public void sendNotification(Customer customer, MailItem item) throws MessagingException, IOException {
        // TODO: Create class to retrieve image file by URL
        Emailer.sendEmailWithAttachment(item.getImageFile(), customer.getEmail());
    }
}
