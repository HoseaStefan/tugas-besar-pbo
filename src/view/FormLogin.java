package view;

import javax.swing.*;

import controller.UserController;
import model.User;
import model.UserType;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
public class FormLogin {

    private JFrame frame;

    public FormLogin() {
        showLogin();
    }

    public void showLogin() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();

        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;

        final int FRAME_WIDTH = 500;
        final int FRAME_HEIGHT = 700;

        int start_x = screenWidth / 2 - (FRAME_WIDTH / 2);
        int start_y = screenHeight / 2 - (FRAME_HEIGHT / 2);

        Font buttonFont = new Font("SansSerif", Font.BOLD, 18);

        frame = new JFrame("Login");
        frame.setUndecorated(true);
        frame.setBounds(start_x, start_y, FRAME_WIDTH, FRAME_HEIGHT);
        frame.setShape(new RoundRectangle2D.Double(0, 0, FRAME_WIDTH, FRAME_HEIGHT, 30, 30));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.getHSBColor(0.6f, 0.7f, 0.9f));
        panel.setBounds(0, 0, FRAME_WIDTH, FRAME_HEIGHT);

        JLabel title = new JLabel("LOG-IN");
        title.setBounds(200, 150, 500, 50);
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        title.setForeground(Color.WHITE);
        panel.add(title);

        JLabel usernameLabel = new JLabel("Input Username/Email : ");
        usernameLabel.setBounds(120, 200, 500, 50);
        panel.add(usernameLabel);

        JTextField inputUsername = new JTextField(16);
        inputUsername.setHorizontalAlignment(JTextField.CENTER);
        inputUsername.setBorder(BorderFactory.createEmptyBorder());
        inputUsername.setBounds(120, 240, 260, 50);
        panel.add(inputUsername);

        JLabel passLabel = new JLabel("Input Password : ");
        passLabel.setBounds(120, 280, 500, 50);
        panel.add(passLabel);

        JPasswordField inputPassword = new JPasswordField(16);
        inputPassword.setHorizontalAlignment(JTextField.CENTER);
        inputPassword.setBorder(BorderFactory.createEmptyBorder());
        inputPassword.setBounds(120, 320, 260, 50);
        panel.add(inputPassword);

        JButton loginButton = new JButton("LOGIN");
        loginButton.setBounds(120, 400, 260, 50);
        Component.styleButton(loginButton, new Color(3, 123, 252), buttonFont);
        panel.add(loginButton);
        loginButton.addActionListener(e -> {
            String username = inputUsername.getText();
            String password = new String(inputPassword.getPassword());

            if (!username.isEmpty() && !password.isEmpty()) {
                User verifying = UserController.verifyUser(username, password);
                if (verifying.getUserType() == UserType.NASABAH) {
                    // belom edit
                    System.out.println("nasabah");
                    frame.dispose();
                    new MainMenu();
                } else if (verifying.getUserType() == UserType.ADMIN) {
                    // belom edit
                    System.out.println("admin");
                    frame.dispose();
                    new MenuAdmin();
                } else {
                    JOptionPane.showMessageDialog(frame, "Salah username/password!");
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