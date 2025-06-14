package com.infrastructure;

import com.entities.Customer;
import com.entities.MailItem;

import java.util.*;

// TODO: For testing
public class FakeEmailService implements EmailService {

    public static class SentEmail {
        private final Customer customer;
        private final MailItem mailItem;

        public SentEmail(Customer customer, MailItem mailItem) {
            this.customer = customer;
            this.mailItem = mailItem;
        }

        public Customer getCustomer() {
            return customer;
        }

        public MailItem getMailItem() {
            return mailItem;
        }
    }

    private final List<SentEmail> sentEmails = new ArrayList<>();

    @Override
    public void sendNotification(Customer customer, MailItem mailItem) {
        System.out.println(customer.getId() + mailItem.getDescription() + mailItem.getSender());
        sentEmails.add(new SentEmail(customer, mailItem));
    }

    public List<SentEmail> getSentEmails() {
        return new ArrayList<>(sentEmails);
    }

    public int getEmailCount() {
        return sentEmails.size();
    }

    public void clear() {
        sentEmails.clear();
    }
}
