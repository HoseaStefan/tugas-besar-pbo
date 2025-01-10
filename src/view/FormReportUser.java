package view;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import model.Report;
import model.StatusReport;
import controller.ReportController;

public class FormReportUser {

    private JFrame frame;

    public FormReportUser() {
        int confirmation = JOptionPane.showConfirmDialog(null,
                "Are you sure you want to report?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (confirmation != JOptionPane.YES_OPTION) {
            new MainMenu();
            return;
        }
        showFormReportUser();
    }

    public void showFormReportUser() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();

        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;

        final int FRAME_WIDTH = 500;
        final int FRAME_HEIGHT = 700;

        int start_x = screenWidth / 2 - (FRAME_WIDTH / 2);
        int start_y = screenHeight / 2 - (FRAME_HEIGHT / 2);

        frame = new JFrame("Form Report");
        frame.setUndecorated(true);
        frame.setBounds(start_x, start_y, FRAME_WIDTH, FRAME_HEIGHT);
        frame.setShape(new RoundRectangle2D.Double(0, 0, FRAME_WIDTH, FRAME_HEIGHT, 30, 30));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.getHSBColor(0.6f, 0.7f, 0.9f));
        panel.setBounds(0, 0, FRAME_WIDTH, FRAME_HEIGHT);

        JLabel title = new JLabel("Form Report User!");
        title.setBounds(130, 60, 500, 50);
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        title.setForeground(Color.WHITE);
        panel.add(title);

        JLabel userNameLabel = new JLabel("Username:");
        userNameLabel.setBounds(120, 110, 300, 30);
        panel.add(userNameLabel);

        JTextField userNameField = new JTextField();
        userNameField.setBounds(120, 140, 260, 30);
        panel.add(userNameField);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(120, 180, 300, 30);
        panel.add(emailLabel);

        JFormattedTextField emailField = new JFormattedTextField();
        emailField.setBounds(120, 210, 260, 30);
        emailField.setColumns(10);
        panel.add(emailField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(120, 250, 300, 30);
        panel.add(passwordLabel);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(120, 280, 260, 30);
        panel.add(passwordField);

        JLabel messageLabel = new JLabel("Message:");
        messageLabel.setBounds(120, 320, 300, 30);
        panel.add(messageLabel);

        JFormattedTextField messageField = new JFormattedTextField();
        messageField.setBounds(120, 350, 260, 30);
        messageField.setColumns(10);
        panel.add(messageField);

        ((AbstractDocument) messageField.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                    throws BadLocationException {
                if (fb.getDocument().getLength() + text.length() - length <= 100) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }

            @Override
            public void insertString(FilterBypass fb, int offset, String text, AttributeSet attrs)
                    throws BadLocationException {
                if (fb.getDocument().getLength() + text.length() <= 100) {
                    super.insertString(fb, offset, text, attrs);
                }
            }
        });

        JButton reportButton = new JButton("Submit");
        Component.styleRoundedButton(reportButton, new Color(0, 102, 204), Color.WHITE);
        reportButton.setBounds(120, 500, 260, 50);
        Component.addHoverEffect(reportButton, new Color(0, 102, 204), new Color(0, 123, 180));
        panel.add(reportButton);

        reportButton.addActionListener(e -> {
            String username = userNameField.getText();
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());
            String message = messageField.getText().trim();

            if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please fill in all fields!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            } else {
                String isValid = ReportController.validateUserData(username, email, password);

                if (isValid == null) {
                    JOptionPane.showMessageDialog(frame, "Invalid data. Please try again.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Report report = new Report("", isValid, email, message, StatusReport.DIPROSES);

                ReportController controller = new ReportController();
                boolean isCreated = controller.createReport(report);

                if (isCreated) {
                    JOptionPane.showMessageDialog(frame,
                            "Report submitted successfully! Waiting for admin verification.", "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                    new MainMenu();
                }
            }
        });

        // Tombol Exit
        JButton backButton = new JButton("Back to Main Menu");
        Component.styleRoundedButton(backButton, new Color(255, 69, 58), Color.WHITE);
        backButton.setBounds(120, 600, 260, 50);
        Component.addHoverEffect(backButton, new Color(255, 69, 58), new Color(255, 82, 82));
        panel.add(backButton);

        backButton.addActionListener(e -> {
            frame.dispose();
            new MainMenu();
        });

        frame.add(panel);
        frame.setVisible(true);
    }

}