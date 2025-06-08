package com.util;

import java.io.IOException;
import java.util.Properties;
import java.io.File;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.activation.DataHandler;
import jakarta.activation.DataSource;
import jakarta.activation.FileDataSource;
import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Multipart;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;

/**
 * Emailer.java
 *
 * 1. Loads configuration from a .env file (using java-dotenv).
 * 2. Lists objects in a Supabase Storage bucket (“mail-photos”).
 * 3. Downloads the first file from that bucket.
 * 4. Sends an email (via SMTP SSL) with the downloaded file attached.
 *
 * Required environment variables (in a .env file at the project root):
 *   SUPABASE_URL       (e.g. https://your-project.supabase.co)
 *   SUPABASE_API_KEY   (anon or service-role key)
 *   EMAIL_ADDRESS      (SMTP “from” address, e.g. your Gmail)
 *   EMAIL_PASSWORD     (app-specific password for SMTP)
 *
 * Compile & Run:
 *   (Make sure your pom.xml includes: java-dotenv,
 *   jakarta.mail-api, org.eclipse.angus:angus-mail, jakarta.activation, org.json)
 */
public class Emailer {

    // ─────────────────────────────────────────────────────────────────
    // Class-level constants (loaded once via Dotenv)
    // ─────────────────────────────────────────────────────────────────
    private static final String SUPABASE_URL;
    private static final String SUPABASE_API_KEY;
    private static final String EMAIL_ADDRESS;
    private static final String EMAIL_PASSWORD;
    private static final String RECIPIENT_EMAIL = "joshdrvillas@gmail.com"; // you can change this or load from .env

    static {
        // Load environment variables from a .env file in the project root
        Dotenv dotenv = Dotenv.configure()
                .directory(System.getProperty("user.dir"))
                .ignoreIfMalformed()
                .ignoreIfMissing()
                .load();

        SUPABASE_URL     = dotenv.get("SUPABASE_URL");
        SUPABASE_API_KEY = dotenv.get("SUPABASE_API_KEY");
        EMAIL_ADDRESS    = dotenv.get("EMAIL_ADDRESS");
        EMAIL_PASSWORD   = dotenv.get("EMAIL_PASSWORD");
    }

    // ─────────────────────────────────────────────────────────────────
    // Validate that all required environment variables were loaded
    // ─────────────────────────────────────────────────────────────────
    private static void validateEnv() {
        if (SUPABASE_URL      == null || SUPABASE_URL.isBlank() ||
                SUPABASE_API_KEY  == null || SUPABASE_API_KEY.isBlank() ||
                EMAIL_ADDRESS     == null || EMAIL_ADDRESS.isBlank() ||
                EMAIL_PASSWORD    == null || EMAIL_PASSWORD.isBlank()) {
            throw new RuntimeException(
                    "Missing required environment variables. " +
                            "Please create a .env file with SUPABASE_URL, SUPABASE_API_KEY, " +
                            "EMAIL_ADDRESS, and EMAIL_PASSWORD."
            );
        }
    }

    // ─────────────────────────────────────────────────────────────────
    // Send an email with the specified attachment using Jakarta Mail
    // ─────────────────────────────────────────────────────────────────
    public static void sendEmailWithAttachment(File attachmentFile, String originalFileName, String recipientEmail)
            throws MessagingException, IOException
    {
        // TODO: Perhaps add try-catch statement to handle error? Or should it be handled externally?
        // I think client should handle email sending errors with windows.

        // Validate environment variables
        validateEnv();

        // 1) Configure SMTP properties (using Gmail’s SMTP SSL)
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.ssl.enable", "true");

        // 2) Create a Session object with authentication
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EMAIL_ADDRESS, EMAIL_PASSWORD);
            }
        });

        // 3) Build the email message
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(EMAIL_ADDRESS));
        message.setRecipients(
                Message.RecipientType.TO,
                InternetAddress.parse(recipientEmail, false)
        );
        message.setSubject("📬 Your Mail Photo");

        // 4) Create the text body part
        MimeBodyPart textPart = new MimeBodyPart();
        StringBuilder bodyBuilder = new StringBuilder()
                .append("Hello,\n\n")
                .append("A new package has arrived at your mailbox.\n\n")
                //.append("Filename: ").append(originalFileName).append("\n\n")
                .append("Best,\n")
                .append("Your Virtual Mailbox System");
        textPart.setText(bodyBuilder.toString());

        // 5) Create the attachment part
        MimeBodyPart attachmentPart = new MimeBodyPart();
        DataSource source = new FileDataSource(attachmentFile); // switched parameter to take File
        attachmentPart.setDataHandler(new DataHandler(source));
        attachmentPart.setFileName(originalFileName);

        // 6) Combine text + attachment into a multipart
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(textPart);
        multipart.addBodyPart(attachmentPart);

        // 7) Set the multipart as the email’s content
        message.setContent(multipart);

        // 8) Send the message
        Transport.send(message);

        // Log success
        System.out.println("I think it sent successfully.");
    }
}
