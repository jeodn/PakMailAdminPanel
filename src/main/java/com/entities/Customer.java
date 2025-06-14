package com.entities;

import java.util.ArrayList;
import java.util.List;

public final class Customer {
    private final int id;
    private final String name;
    private final String email;
    private final List<MailItem> mailbox;

    public Customer(int id, String name, String email) {
        this.id    = id;
        this.name  = name;
        this.email = email;
        this.mailbox = new ArrayList<MailItem>();
    }

    public int    getId()    { return id; }
    public String getName()  { return name; }
    public String getEmail() { return email; }

    @Override public String toString() {
        return name + " <" + email + '>';
    }

    public List<MailItem> getMailbox() {
        return mailbox;
    }

    public void updateMailbox(MailItem newMail) {
        mailbox.add(newMail);
    }
}
