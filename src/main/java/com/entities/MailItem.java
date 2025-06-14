package com.entities;

import java.io.File;
import java.time.LocalDate;
import java.util.UUID;

// TODO: For now, MailItem stores image as a File
public class MailItem {
    private final UUID id;
    private final int customerId;
    private final String sender;
    private final LocalDate receivedDate;
    private final String description;
    private final File imageFile;
    private final String type;

    public MailItem(UUID id, int customerId, String sender, LocalDate receivedDate, String description, File imageFile, String type) {
        this.id = id;
        this.customerId = customerId;
        this.sender = sender;
        this.receivedDate = receivedDate;
        this.description = description;
        this.imageFile = imageFile;
        this.type = type;
    }

    public UUID getId() {
        return id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public String getSender() {
        return sender;
    }

    public LocalDate getReceivedDate() {
        return receivedDate;
    }

    public String getDescription() {
        return description;
    }

    public String getImagePath() {
        return null;
    }

    public File getImageFile() {
        return imageFile;
    }

    public String getType() {
        return type;
    }
}
