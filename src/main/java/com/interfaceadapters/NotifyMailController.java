package com.interfaceadapters;

import com.entities.MailItem;
import com.usecases.NotifyCustomerOfMailUseCase;
import jakarta.mail.MessagingException;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.UUID;

public class NotifyMailController {

    private final NotifyCustomerOfMailUseCase useCase;

    public NotifyMailController(NotifyCustomerOfMailUseCase useCase) {
        this.useCase = useCase;
    }

    public void onNotifyButtonPressed(int customerId, String sender, String description, File image, String type) throws MessagingException, IOException {
        MailItem mail = new MailItem(
                UUID.randomUUID(),
                customerId,
                sender,
                LocalDate.now(),
                description,
                image,
                type
        );
        useCase.notifyCustomer(mail);
    }
}
