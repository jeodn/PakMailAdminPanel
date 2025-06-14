package com.ui;

import com.entities.Customer;
import com.interfaceadapters.CustomerListPresenter;
import com.interfaceadapters.NotifyMailController;
import com.util.ImageUtils;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;

public class NotifyCustomerDialog extends JDialog {
    private final NotifyMailController controller;
    private final CustomerListPresenter presenter;
    private final JComboBox<String> typeBox =
            new JComboBox<>(new String[]{"Envelope", "Parcel", "Other"});
    private final JTextField senderField = new JTextField(8);
    private final JTextField descriptionField = new JTextField(8);
    private final JLabel imgPreview = new JLabel();
    private final JButton chooseImgBtn = new JButton("Choose Image…");
    private final JButton sendBtn = new JButton("Send");
    private File          selectedImage;           // keep for “Send”
    private Customer selectedCustomer;     // the customer

    public NotifyCustomerDialog(Frame owner, NotifyMailController controller, CustomerListPresenter presenter, Customer selectedCustomer) {
        super(owner, "Notify Customer", true);
        this.controller = controller;
        this.presenter = presenter;

        this.selectedCustomer = selectedCustomer;

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4,4,4,4);
        gbc.anchor = GridBagConstraints.WEST;

        // Package type
        gbc.gridx = 0; gbc.gridy = 0; add(new JLabel("Type:"), gbc);
        gbc.gridx = 1; add(typeBox, gbc);

        // Sender
        gbc.gridx = 0; gbc.gridy = 1; add(new JLabel("Sender name:"), gbc);
        gbc.gridx = 1; add(senderField, gbc);

        // Description
        gbc.gridx = 0; gbc.gridy = 2; add(new JLabel("Description:"), gbc);
        gbc.gridx = 1; add(descriptionField, gbc);

        // Image
        gbc.gridx = 0; gbc.gridy = 3; add(new JLabel("Image:"), gbc);
        gbc.gridx = 1; add(chooseImgBtn, gbc);

        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        imgPreview.setPreferredSize(new Dimension(150, 100));
        imgPreview.setBorder(BorderFactory.createEtchedBorder());
        add(imgPreview, gbc);

        // Buttons
        JPanel btnBar = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnBar.add(sendBtn);
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        add(btnBar, gbc);

        pack();
        setLocationRelativeTo(owner);

        //
        hookUpImageChooser();
        hookUpEmailSender();
    }

    // TODO: implement chooseImgBtn & sendBtn actions
    /** Opens a JFileChooser, validates the file, and shows a thumbnail. */
    private void hookUpImageChooser() {
        chooseImgBtn.addActionListener(e -> {
            JFileChooser fc = new JFileChooser();
            fc.setFileFilter(new FileNameExtensionFilter(
                    "Images (jpg, png, gif, bmp)", "jpg", "jpeg", "png", "gif", "bmp"));

            if (fc.showOpenDialog(this) != JFileChooser.APPROVE_OPTION) return;

            File file = fc.getSelectedFile();
            try {
                ImageIcon thumb = ImageUtils.createThumbnail(
                        file, imgPreview.getWidth(), imgPreview.getHeight());
                imgPreview.setIcon(thumb);
                selectedImage = file;                // stash for later
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "Failed to load image:\n" + ex.getMessage(),
                        "Image Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    /* Sends email*/
    private void hookUpEmailSender() {
        sendBtn.addActionListener(e -> {
            try {
                System.out.println("made it here");

                // Emailer
                // Emailer.sendEmailWithAttachment(selectedImage, selectedImage.getName(), selectedCustomer.getEmail());
                controller.onNotifyButtonPressed(selectedCustomer.getId(), senderField.getText(), descriptionField.getText(), selectedImage, "temporary type");

                JOptionPane.showMessageDialog(this, "Email sent successfully!");
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this,
                        "Failed to send email:\n" + ex.getMessage(),
                        "Email Error", JOptionPane.ERROR_MESSAGE);
            }
        });


    }
}
