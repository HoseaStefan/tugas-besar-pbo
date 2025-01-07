package view;

import controller.UserController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.util.Random;

public class FormVerifikasiEmail {

    public FormVerifikasiEmail() {
        showVerifikasiEmail();
    }

    public void showVerifikasiEmail() {
        Font buttonFont = new Font("SansSerif", Font.BOLD, 18);

        JFrame frame = new JFrame("Email Verification");
        frame.setUndecorated(true);
        frame.setBounds(0, 0, 500, 400);
        frame.setShape(new RoundRectangle2D.Double(0, 0, 500, 400, 30, 30));
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.getHSBColor(0.6f, 0.7f, 0.9f));
        panel.setBounds(0, 0, 500, 400);

        JLabel title = new JLabel("Verify Your Email");
        title.setBounds(140, 50, 500, 50);
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        title.setForeground(Color.WHITE);
        panel.add(title);

        JLabel emailLabel = new JLabel("Enter your email:");
        emailLabel.setBounds(120, 120, 260, 30);
        emailLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        emailLabel.setForeground(Color.WHITE);
        panel.add(emailLabel);

        JTextField emailField = new JTextField();
        emailField.setBounds(120, 150, 260, 40);
        emailField.setFont(new Font("SansSerif", Font.PLAIN, 18));
        emailField.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        emailField.setHorizontalAlignment(JTextField.CENTER);
        panel.add(emailField);

        JButton verifyButton = new JButton("Verify Email");
        verifyButton.setBounds(120, 220, 260, 50);
        verifyButton.setFont(buttonFont);
        verifyButton.setBackground(new Color(3, 123, 252));
        verifyButton.setForeground(Color.WHITE);
        verifyButton.setFocusPainted(false);
        verifyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = emailField.getText();

                if (email.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please enter a valid email.");
                    return;
                }

                if (UserController.isEmailInDatabase(email)) {
                    String verificationCode = generateVerificationCode();
                    System.out.println("Verification Code: " + verificationCode);  
                    frame.dispose(); 
                    showVerificationCodeForm(email, verificationCode);
                } else {
                    JOptionPane.showMessageDialog(frame, "Email not found in the database.");
                }
            }
        });
        panel.add(verifyButton);

        JButton exitButton = new JButton("Cancel");
        exitButton.setBounds(120, 280, 260, 50);
        exitButton.setFont(buttonFont);
        exitButton.setBackground(new Color(255, 69, 58));
        exitButton.setForeground(Color.WHITE);
        exitButton.setFocusPainted(false);
        exitButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(null, "Email verification cancelled.");
            frame.dispose();
            new MainMenu(); 
        });
        panel.add(exitButton);

        frame.add(panel);
        frame.setVisible(true);
    }

    private String generateVerificationCode() {
        Random random = new Random();
        int code = 1000 + random.nextInt(9000);
        return String.valueOf(code);
    }

    private void showVerificationCodeForm(String email, String verificationCode) {
        Font buttonFont = new Font("SansSerif", Font.BOLD, 18);

        JFrame frame = new JFrame("Enter Verification Code");
        frame.setUndecorated(true);
        frame.setBounds(0, 0, 500, 400);
        frame.setShape(new RoundRectangle2D.Double(0, 0, 500, 400, 30, 30));
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.getHSBColor(0.6f, 0.7f, 0.9f));
        panel.setBounds(0, 0, 500, 400);

        JLabel title = new JLabel("Enter Verification Code");
        title.setBounds(120, 50, 500, 50);
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        title.setForeground(Color.WHITE);
        panel.add(title);

        JLabel codeLabel = new JLabel("Enter code sent to your email:");
        codeLabel.setBounds(120, 120, 260, 30);
        codeLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        codeLabel.setForeground(Color.WHITE);
        panel.add(codeLabel);

        JTextField codeField = new JTextField();
        codeField.setBounds(120, 150, 260, 40);
        codeField.setFont(new Font("SansSerif", Font.PLAIN, 18));
        codeField.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        codeField.setHorizontalAlignment(JTextField.CENTER);
        panel.add(codeField);

        JButton submitButton = new JButton("Submit Code");
        submitButton.setBounds(120, 220, 260, 50);
        submitButton.setFont(buttonFont);
        submitButton.setBackground(new Color(3, 123, 252));
        submitButton.setForeground(Color.WHITE);
        submitButton.setFocusPainted(false);
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String codeInput = codeField.getText();

                if (codeInput.equals(verificationCode)) {
                    JOptionPane.showMessageDialog(frame, "Email verified successfully!");
                    frame.dispose();
                    new FormNewPassword(email);  
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid verification code.");
                }
            }
        });
        panel.add(submitButton);

        JButton exitButton = new JButton("Cancel");
        exitButton.setBounds(120, 280, 260, 50);
        exitButton.setFont(buttonFont);
        exitButton.setBackground(new Color(255, 69, 58));
        exitButton.setForeground(Color.WHITE);
        exitButton.setFocusPainted(false);
        exitButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(null, "Verification cancelled.");
            frame.dispose();
            new MainMenu();
        });
        panel.add(exitButton);

        frame.add(panel);
        frame.setVisible(true);
    }
}
