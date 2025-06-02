package ui;

import javax.swing.*;
import java.awt.*;

public class NotifyCustomerDialog extends JDialog {
    private final JComboBox<String> typeBox =
            new JComboBox<>(new String[]{"Envelope", "Parcel", "Other"});
    private final JTextField weightField = new JTextField(8);
    private final JLabel imgPreview = new JLabel();
    private final JButton chooseImgBtn = new JButton("Choose Image…");
    private final JButton sendBtn = new JButton("Send");

    public NotifyCustomerDialog(Frame owner) {
        super(owner, "Notify Customer", true);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4,4,4,4);
        gbc.anchor = GridBagConstraints.WEST;

        // Package type
        gbc.gridx = 0; gbc.gridy = 0; add(new JLabel("Type:"), gbc);
        gbc.gridx = 1; add(typeBox, gbc);

        // Weight
        gbc.gridx = 0; gbc.gridy = 1; add(new JLabel("Weight (g):"), gbc);
        gbc.gridx = 1; add(weightField, gbc);

        // Image
        gbc.gridx = 0; gbc.gridy = 2; add(new JLabel("Image:"), gbc);
        gbc.gridx = 1; add(chooseImgBtn, gbc);

        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
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
    }

    // TODO: implement chooseImgBtn & sendBtn actions
}
