package com.infrastructure;

import com.entities.MailItem;

import java.util.*;

// TODO: Mock mail repo for testing
public class InMemoryMailRepository implements MailRepository {
    private final List<MailItem> storedMail = new ArrayList<>();

    @Override
    public void save(MailItem mailItem) {
        storedMail.add(mailItem);
    }

    @Override
    public List<MailItem> findByCustomerId(int customerId) {
        List<MailItem> result = new ArrayList<>();
        for (MailItem mail : storedMail) {
            if (mail.getCustomerId() == customerId) {
                result.add(mail);
            }
        }
        return result;
    }

    public List<MailItem> getAll() {
        return new ArrayList<>(storedMail);
    }
}
