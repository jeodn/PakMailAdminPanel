package com.usecases;

import com.entities.MailItem;
import jakarta.mail.MessagingException;

import java.io.IOException;

public interface NotifyCustomerOfMailUseCase {
    void notifyCustomer(MailItem mailItem) throws MessagingException, IOException;
}
