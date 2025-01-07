package view;

import javax.swing.*;

import controller.UserController;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;

public class FormNewPassword {

    public FormNewPassword(String email) {
        showNewPassword(email);
    }

    public void showNewPassword(String email) {
        Font buttonFont = new Font("SansSerif", Font.BOLD, 18);

        JFrame frame = new JFrame("Change Password");
        frame.setUndecorated(true);
        frame.setBounds(0, 0, 500, 500);
        frame.setShape(new RoundRectangle2D.Double(0, 0, 500, 500, 30, 30));
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.getHSBColor(0.6f, 0.7f, 0.9f));
        panel.setBounds(0, 0, 500, 700);

        JLabel title = new JLabel("Change Password");
        title.setBounds(140, 50, 500, 50);
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        title.setForeground(Color.WHITE);
        panel.add(title);

        JLabel newPasswordLabel = new JLabel("Enter New Password:");
        newPasswordLabel.setBounds(120, 120, 260, 30);
        newPasswordLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        newPasswordLabel.setForeground(Color.WHITE);
        panel.add(newPasswordLabel);

        JPasswordField newPasswordField = new JPasswordField();
        newPasswordField.setBounds(120, 150, 260, 40);
        newPasswordField.setFont(new Font("SansSerif", Font.PLAIN, 18));
        newPasswordField.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        newPasswordField.setHorizontalAlignment(JTextField.CENTER);
        panel.add(newPasswordField);

        JLabel confirmPasswordLabel = new JLabel("Confirm Password:");
        confirmPasswordLabel.setBounds(120, 210, 260, 30);
        confirmPasswordLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        confirmPasswordLabel.setForeground(Color.WHITE);
        panel.add(confirmPasswordLabel);

        JPasswordField confirmPasswordField = new JPasswordField();
        confirmPasswordField.setBounds(120, 240, 260, 40);
        confirmPasswordField.setFont(new Font("SansSerif", Font.PLAIN, 18));
        confirmPasswordField.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        confirmPasswordField.setHorizontalAlignment(JTextField.CENTER);
        panel.add(confirmPasswordField);

        JButton submitButton = new JButton("Submit");
        submitButton.setBounds(120, 310, 260, 50);
        submitButton.setFont(buttonFont);
        submitButton.setBackground(new Color(3, 123, 252));
        submitButton.setForeground(Color.WHITE);
        submitButton.setFocusPainted(false);
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newPassword = new String(newPasswordField.getPassword());
                String confirmPassword = new String(confirmPasswordField.getPassword());

                if (newPassword.isEmpty() || confirmPassword.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please fill in both password fields.");
                    return;
                }

                if (!newPassword.equals(confirmPassword)) {
                    JOptionPane.showMessageDialog(frame, "Passwords do not match.");
                    return;
                }

                boolean isUpdated = UserController.changePassword(email, newPassword);
                
                if (isUpdated) {
                    JOptionPane.showMessageDialog(frame, "Password updated successfully!");
                    frame.dispose();
                    new MainMenu(); 
                } else {
                    JOptionPane.showMessageDialog(frame, "Password update failed. Please try again.");
                }
            }
        });
        panel.add(submitButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setBounds(120, 380, 260, 50);
        cancelButton.setFont(buttonFont);
        cancelButton.setBackground(new Color(255, 69, 58));
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setFocusPainted(false);
        cancelButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(null, "Password change cancelled.");
            frame.dispose();
            new MainMenu(); 
        });
        panel.add(cancelButton);

        frame.add(panel);
        frame.setVisible(true);
    }
}
