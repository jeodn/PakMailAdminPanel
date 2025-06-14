package com.infrastructure;

import com.entities.MailItem;

import java.util.List;

public interface MailRepository {
    void save(MailItem item);
    List<MailItem> findByCustomerId(int id);
}
