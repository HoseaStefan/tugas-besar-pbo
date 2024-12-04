package view;

import javax.swing.*;

import controller.UserController;
import model.UserType;

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

        final int FRAME_WIDTH = 980;
        final int FRAME_HEIGHT = 500;

        int start_x = screenWidth / 2 - (FRAME_WIDTH / 2);
        int start_y = screenHeight / 2 - (FRAME_HEIGHT / 2);

        frame = new JFrame("Register");
        frame.setBounds(start_x, start_y, FRAME_WIDTH, FRAME_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(0, 0, FRAME_WIDTH, FRAME_HEIGHT);

        JLabel title = new JLabel("==== !!REGISTER!! ====");
        title.setBounds(350, 10, 500, 50);
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        panel.add(title);

        JLabel usernameLabel = new JLabel("Input Username : ");
        usernameLabel.setBounds(350, 50, 500, 50);
        panel.add(usernameLabel);

        JTextField inputUsername = new JTextField(16);
        inputUsername.setHorizontalAlignment(JTextField.CENTER);
        inputUsername.setBounds(350, 90, 260, 50);
        panel.add(inputUsername);

        JLabel emailLabel = new JLabel("Input Email : ");
        emailLabel.setBounds(350, 130, 500, 50);
        panel.add(emailLabel);

        JTextField inputEmail = new JTextField(16);
        inputEmail.setHorizontalAlignment(JTextField.CENTER);
        inputEmail.setBounds(350, 170, 260, 50);
        panel.add(inputEmail);

        JLabel passLabel = new JLabel("Input Password : ");
        passLabel.setBounds(350, 210, 500, 50);
        panel.add(passLabel);

        JPasswordField inputPassword = new JPasswordField(16);
        inputPassword.setHorizontalAlignment(JTextField.CENTER);
        inputPassword.setBounds(350, 250, 260, 50);
        panel.add(inputPassword);

        JButton regisButton = new JButton("REGISTER");
        regisButton.setBounds(350, 310, 260, 50);
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
        exitButton.setBounds(350, 370, 260, 50);
        panel.add(exitButton);

        exitButton.addActionListener(e -> {
            frame.dispose();
            new MainMenu();
        });
        frame.add(panel);
        frame.setVisible(true);

    }

}