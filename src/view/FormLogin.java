package view;

import javax.swing.*;

import controller.UserController;
import model.UserType;

import java.awt.*;

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

        final int FRAME_WIDTH = 980;
        final int FRAME_HEIGHT = 500;

        int start_x = screenWidth / 2 - (FRAME_WIDTH / 2);
        int start_y = screenHeight / 2 - (FRAME_HEIGHT / 2);

        frame = new JFrame("Login");
        frame.setBounds(start_x, start_y, FRAME_WIDTH, FRAME_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(0, 0, FRAME_WIDTH, FRAME_HEIGHT);

        JLabel title = new JLabel("===== !!LOG-IN!! =====");
        title.setBounds(350, 10, 500, 50);
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        panel.add(title);

        JLabel usernameLabel = new JLabel("Input Username/Email : ");
        usernameLabel.setBounds(350, 50, 500, 50);
        panel.add(usernameLabel);

        JTextField inputUsername = new JTextField(16);
        inputUsername.setHorizontalAlignment(JTextField.CENTER);
        inputUsername.setBounds(350, 90, 260, 50);
        panel.add(inputUsername);

        JLabel passLabel = new JLabel("Input Password : ");
        passLabel.setBounds(350, 130, 500, 50);
        panel.add(passLabel);

        JPasswordField inputPassword = new JPasswordField(16);
        inputPassword.setHorizontalAlignment(JTextField.CENTER);
        inputPassword.setBounds(350, 170, 260, 50);
        panel.add(inputPassword);

        JButton loginButton = new JButton("LOGIN");
        loginButton.setBounds(350, 240, 260, 50);
        panel.add(loginButton);

        loginButton.addActionListener(e -> {
            String username = inputUsername.getText();
            String password = new String(inputPassword.getPassword());
        
            // Validate input
            if (!username.isEmpty() && !password.isEmpty()) {
                UserType verifying = UserController.verifyUser(username, password);
            } else {
                JOptionPane.showMessageDialog(frame, "Isi terlebih dahulu kawan!");
            }
        });
        

        JButton exitButton = new JButton("Back to Main Menu");
        exitButton.setBounds(350, 310, 260, 50);
        panel.add(exitButton);

        exitButton.addActionListener(e -> {
            frame.dispose();
            new MainMenu();
        });

        frame.add(panel);
        frame.setVisible(true);

    }

}