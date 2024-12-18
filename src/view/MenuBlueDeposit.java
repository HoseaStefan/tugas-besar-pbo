package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;
import javax.swing.*;

public class MenuBlueDeposit {

    private static JFrame frame;

    public MenuBlueDeposit() {
        menuBlueDeposito();
    }

    public void menuBlueDeposito() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();

        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;

        final int FRAME_WIDTH = 500;
        final int FRAME_HEIGHT = 700;

        int start_x = screenWidth / 2 - (FRAME_WIDTH / 2);
        int start_y = screenHeight / 2 - (FRAME_HEIGHT / 2);

        Font buttonFont = new Font("SansSerif", Font.BOLD, 18);

        frame = new JFrame("Blue Deposit");
        frame.setUndecorated(true);
        frame.setBounds(start_x, start_y, FRAME_WIDTH, FRAME_HEIGHT);
        frame.setShape(new RoundRectangle2D.Double(0, 0, FRAME_WIDTH, FRAME_HEIGHT, 30, 30));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.getHSBColor(0.6f, 0.7f, 0.9f));
        panel.setBounds(0, 0, FRAME_WIDTH, FRAME_HEIGHT);

        JLabel title = new JLabel("Blue Deposito");
        title.setBounds(170, 150, 500, 50);
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        title.setForeground(Color.WHITE);
        panel.add(title);

        JButton btnTarik = new JButton("Tarik");
        btnTarik.setBounds(150, 250, 200, 100);
        Component.styleButton(btnTarik, new Color(3, 123, 252), buttonFont); 
        panel.add(btnTarik);
        
        JButton btnCreate = new JButton("Create");
        btnCreate.setBounds(150,400 , 200, 100);
        Component.styleButton(btnCreate, new Color(3, 123, 252), buttonFont); 
        panel.add(btnCreate);

        btnTarik.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose(); 
                new MenuTarikBlueDeposit();
            }
        });
        
        btnCreate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose(); 
                new MenuCreateBlueDeposit();
            }
        });

        // Add panel to frame
        frame.add(panel);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new MenuBlueDeposit();
    }
}
