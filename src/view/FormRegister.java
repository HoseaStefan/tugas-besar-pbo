package view;

import javax.swing.*;

import controller.UserController;
import java.awt.geom.RoundRectangle2D;
import java.awt.*;

public class FormRegister {

    private JFrame frame;

    public FormRegister() {
        showRegister();
    }

    public void showRegister() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();

        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;

        final int FRAME_WIDTH = 500;
        final int FRAME_HEIGHT = 700;

        int start_x = screenWidth / 2 - (FRAME_WIDTH / 2);
        int start_y = screenHeight / 2 - (FRAME_HEIGHT / 2);

        Font buttonFont = new Font("SansSerif", Font.BOLD, 18);

        frame = new JFrame("Register");
        frame.setUndecorated(true);
        frame.setBounds(start_x, start_y, FRAME_WIDTH, FRAME_HEIGHT);
        frame.setShape(new RoundRectangle2D.Double(0, 0, FRAME_WIDTH, FRAME_HEIGHT, 30, 30));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.getHSBColor(0.6f, 0.7f, 0.9f));
        panel.setBounds(0, 0, FRAME_WIDTH, FRAME_HEIGHT);

        JLabel title = new JLabel("REGISTER");
        title.setBounds(190, 125, 500, 50);
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        title.setForeground(Color.WHITE);
        panel.add(title);

        JLabel usernameLabel = new JLabel("Input Username : ");
        usernameLabel.setBounds(120, 200, 500, 50);
        panel.add(usernameLabel);

        JTextField inputUsername = new JTextField(16);
        inputUsername.setHorizontalAlignment(JTextField.CENTER);
        inputUsername.setBounds(120, 240, 260, 50);
        inputUsername.setBorder(BorderFactory.createEmptyBorder());
        panel.add(inputUsername);

        JLabel emailLabel = new JLabel("Input Email : ");
        emailLabel.setBounds(120, 280, 500, 50);
        panel.add(emailLabel);

        JTextField inputEmail = new JTextField(16);
        inputEmail.setHorizontalAlignment(JTextField.CENTER);
        inputEmail.setBounds(120, 320, 260, 50);
        inputEmail.setBorder(BorderFactory.createEmptyBorder());
        panel.add(inputEmail);

        JLabel passLabel = new JLabel("Input Password : ");
        passLabel.setBounds(120, 360, 500, 50);
        panel.add(passLabel);

        JPasswordField inputPassword = new JPasswordField(16);
        inputPassword.setHorizontalAlignment(JTextField.CENTER);
        inputPassword.setBounds(120, 400, 260, 50);
        inputPassword.setBorder(BorderFactory.createEmptyBorder());
        panel.add(inputPassword);

        JButton regisButton = new JButton("REGISTER");
        regisButton.setBounds(120, 470, 260, 50);
        Component.styleButton(regisButton, new Color(3, 123, 252), buttonFont);
        panel.add(regisButton);

        regisButton.addActionListener(e -> {
            String username = inputUsername.getText();
            String email = inputEmail.getText();
            String password = new String(inputPassword.getPassword());

            if (!username.isEmpty() && !password.isEmpty() && !email.isEmpty()) {
                boolean verify = UserController.verifyRegister(username, email, password);
                if (verify) {
                    frame.dispose();
                    new MainMenu();
                } else {
                    JOptionPane.showMessageDialog(frame, "username/email sudah terdaftar!");
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Isi terlebih dahulu kawan!");
            }
        });

        JButton exitButton = new JButton("Back to Main Menu");
        exitButton.setBounds(120, 600, 260, 50);
        Component.styleButton(exitButton, new Color(255, 69, 58), buttonFont);
        panel.add(exitButton);

        exitButton.addActionListener(e -> {
            frame.dispose();
            new MainMenu();
        });
        frame.add(panel);
        frame.setVisible(true);

    }

}