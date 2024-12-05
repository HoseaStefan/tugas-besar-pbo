package view;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class MainMenu {

    private JFrame frame;

    public MainMenu() {
        showMainMenu();
    }

    public void showMainMenu() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();

        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;

        final int FRAME_WIDTH = 500;
        final int FRAME_HEIGHT = 700;

        int start_x = screenWidth / 2 - (FRAME_WIDTH / 2);
        int start_y = screenHeight / 2 - (FRAME_HEIGHT / 2);

        frame = new JFrame("Main Menu");
        frame.setUndecorated(true);
        frame.setBounds(start_x, start_y, FRAME_WIDTH, FRAME_HEIGHT);
        frame.setShape(new RoundRectangle2D.Double(0, 0, FRAME_WIDTH, FRAME_HEIGHT, 30, 30));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.getHSBColor(0.6f, 0.7f, 0.9f));
        panel.setBounds(0, 0, FRAME_WIDTH, FRAME_HEIGHT);

        JLabel title = new JLabel("WELCOME TO BLUE!");
        title.setBounds(130, 100, 500, 50);
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        title.setForeground(Color.WHITE);
        panel.add(title);

        Font buttonFont = new Font("SansSerif", Font.BOLD, 18);

        JButton loginButton = new JButton("LOGIN");
        loginButton.setBounds(120, 275, 260, 50);
        Component.styleButton(loginButton, new Color(3, 123, 252), buttonFont); 
        loginButton.addActionListener(e -> {
            frame.dispose();
            new FormLogin(); 
        });
        panel.add(loginButton);

        JButton registerButton = new JButton("REGISTER");
        registerButton.setBounds(120, 345, 260, 50);
        Component.styleButton(registerButton, new Color(3, 123, 252), buttonFont); 
        registerButton.addActionListener(e -> {
            frame.dispose();
            new FormRegister(); 
        });
        panel.add(registerButton);

        JButton lupaPassButton = new JButton("LUPA PASSWORD");
        lupaPassButton.setBounds(120, 415, 260, 50);
        Component.styleButton(lupaPassButton, new Color(3, 123, 252), buttonFont); 
        lupaPassButton.addActionListener(e -> {
            frame.dispose();
            new FormLupaPassword(); 
        });
        panel.add(lupaPassButton);

        JButton exitButton = new JButton("EXIT");
        exitButton.setBounds(120, 600, 260, 50);
        Component.styleButton(exitButton, new Color(255, 69, 58), buttonFont);
        exitButton.addActionListener(e -> {
            frame.dispose();
        });
        panel.add(exitButton);

        frame.add(panel);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainMenu::new);
    }
}
