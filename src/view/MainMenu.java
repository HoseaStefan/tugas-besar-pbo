package view;

import javax.swing.*;
import java.awt.*;

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

        final int FRAME_WIDTH = 980; 
        final int FRAME_HEIGHT = 500; 

        int start_x = screenWidth / 2 - (FRAME_WIDTH / 2);
        int start_y = screenHeight / 2 - (FRAME_HEIGHT / 2); 
        
        frame = new JFrame("Main Menu");
        frame.setBounds(start_x, start_y, FRAME_WIDTH, FRAME_HEIGHT); 
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(0, 0, FRAME_WIDTH, FRAME_HEIGHT);

        JLabel title = new JLabel("WELCOME TO BLUE!");
        title.setBounds(350, 10, 500, 50);
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        panel.add(title);

        JButton perekamanBtn = new JButton("LOGIN");
        perekamanBtn.setBounds(350, 100, 260, 50);
        panel.add(perekamanBtn);

        // perekamanBtn.addActionListener(e -> {
        //     frame.dispose();
        //     new FormInputData(1, null);
        // });

        JButton pencarianBtn = new JButton("REGISTER");
        pencarianBtn.setBounds(350, 170, 260, 50);
        panel.add(pencarianBtn);

        // pencarianBtn.addActionListener(e -> {
        //     frame.dispose();
        //     new FormPencarianData();
        // });

        JButton lupaPassButton = new JButton("LUPA PASSWORD");
        lupaPassButton.setBounds(350, 240, 260, 50);
        panel.add(lupaPassButton);

        lupaPassButton.addActionListener(e -> {
            frame.dispose();
        });

        JButton exitBtn = new JButton("EXIT");
        exitBtn.setBounds(350, 310, 260, 50);
        panel.add(exitBtn);

        exitBtn.addActionListener(e -> {
            frame.dispose();
        });

        frame.add(panel);
        frame.setVisible(true);

    }

}