package view;

import controller.AdminController;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;

public class FormNewPromoCode {

    public FormNewPromoCode() {
        showNewPromoCodeForm();
    }

    public void showNewPromoCodeForm() {
        Font buttonFont = new Font("SansSerif", Font.BOLD, 18);

        JFrame frame = new JFrame("Create New Promo Code");
        frame.setUndecorated(true);
        frame.setBounds(0, 0, 500, 600);
        frame.setShape(new RoundRectangle2D.Double(0, 0, 500, 600, 30, 30));
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.getHSBColor(0.6f, 0.7f, 0.9f));
        panel.setBounds(0, 0, 500, 500);

        JLabel title = new JLabel("Create New Promo Code");
        title.setBounds(120, 50, 500, 50);
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        title.setForeground(Color.WHITE);
        panel.add(title);

        JLabel promoCodeLabel = new JLabel("Promo Code:");
        promoCodeLabel.setBounds(120, 120, 260, 30);
        promoCodeLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        promoCodeLabel.setForeground(Color.WHITE);
        panel.add(promoCodeLabel);

        JTextField promoCodeField = new JTextField();
        promoCodeField.setBounds(120, 150, 260, 40);
        promoCodeField.setFont(new Font("SansSerif", Font.PLAIN, 18));
        promoCodeField.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        promoCodeField.setHorizontalAlignment(JTextField.CENTER);
        panel.add(promoCodeField);

        JLabel promoTypeLabel = new JLabel("Promo Type:");
        promoTypeLabel.setBounds(120, 210, 260, 30);
        promoTypeLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        promoTypeLabel.setForeground(Color.WHITE);
        panel.add(promoTypeLabel);

        JComboBox<String> promoTypeCombo = new JComboBox<>(new String[] {"TOPUP", "TRANSFER", "SETOR"});
        promoTypeCombo.setBounds(120, 240, 260, 40);
        promoTypeCombo.setFont(new Font("SansSerif", Font.PLAIN, 18));
        panel.add(promoTypeCombo);

        JLabel discountLabel = new JLabel("Discount Value:");
        discountLabel.setBounds(120, 300, 260, 30);
        discountLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        discountLabel.setForeground(Color.WHITE);
        panel.add(discountLabel);

        JTextField discountField = new JTextField();
        discountField.setBounds(120, 330, 260, 40);
        discountField.setFont(new Font("SansSerif", Font.PLAIN, 18));
        discountField.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        discountField.setHorizontalAlignment(JTextField.CENTER);
        discountField.setEditable(false); // Disable manual editing
        panel.add(discountField);

        // Add listener for promoTypeCombo to autofill discount based on selection
        promoTypeCombo.addActionListener(e -> {
            String selectedPromoType = (String) promoTypeCombo.getSelectedItem();
            switch (selectedPromoType) {
                case "TRANSFER":
                case "TOPUP":
                    discountField.setText("2500");
                    break;
                case "SETOR":
                    discountField.setText("5000");
                    break;
                default:
                    discountField.setText(""); // In case no promo type is selected
                    break;
            }
        });

        // Default value setting
        promoTypeCombo.setSelectedIndex(0); // Default to TOPUP
        discountField.setText("2500"); // Default discount value for TOPUP or TRANSFER

        JButton submitButton = new JButton("Submit");
        submitButton.setBounds(120, 400, 260, 50);
        submitButton.setFont(buttonFont);
        submitButton.setBackground(new Color(3, 123, 252));
        submitButton.setForeground(Color.WHITE);
        submitButton.setFocusPainted(false);
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String promoCode = promoCodeField.getText();
                String promoType = (String) promoTypeCombo.getSelectedItem();
                String discountValue = discountField.getText();

                if (promoCode.isEmpty() || discountValue.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please fill in all fields.");
                    return;
                }

                boolean promoExists = AdminController.checkPromoCodeExists(promoCode);

                if (promoExists) {
                    JOptionPane.showMessageDialog(frame, "Promo code already exists.");
                    return;
                }

                boolean isInserted = AdminController.insertPromoCode(promoCode, promoType, Double.parseDouble(discountValue));
                if (isInserted) {
                    JOptionPane.showMessageDialog(frame, "Promo code created successfully!");
                    frame.dispose();
                    new MenuAdmin();
                } else {
                    JOptionPane.showMessageDialog(frame, "Failed to create promo code.");
                }
            }
        });
        panel.add(submitButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setBounds(120, 460, 260, 50);
        cancelButton.setFont(buttonFont);
        cancelButton.setBackground(new Color(255, 69, 58));
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setFocusPainted(false);
        cancelButton.addActionListener(e -> {
            frame.dispose();
            new MenuAdmin();
        });
        panel.add(cancelButton);

        frame.add(panel);
        frame.setVisible(true);
    }
}
