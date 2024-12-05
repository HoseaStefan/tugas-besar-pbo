package view;

import javax.swing.*;

import model.CurrentUser;
import model.User;
import model.Nasabah;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class MenuNasabah {

    private JFrame frame;

    public MenuNasabah() {
        showMenuNasabah();
    }

    public void showMenuNasabah() {
        CurrentUser currentUser = CurrentUser.getInstance();
        Nasabah nasabah = currentUser.getNasabah();
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();

        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;

        final int FRAME_WIDTH = 500;
        final int FRAME_HEIGHT = 700;

        int start_x = screenWidth / 2 - (FRAME_WIDTH / 2);
        int start_y = screenHeight / 2 - (FRAME_HEIGHT / 2);

        Font buttonFont = new Font("SansSerif", Font.BOLD, 18);

        frame = new JFrame("Homepage");
        frame.setUndecorated(true);
        frame.setBounds(start_x, start_y, FRAME_WIDTH, FRAME_HEIGHT);
        frame.setShape(new RoundRectangle2D.Double(0, 0, FRAME_WIDTH, FRAME_HEIGHT, 30, 30));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.getHSBColor(0.6f, 0.7f, 0.9f));
        panel.setBounds(0, 0, FRAME_WIDTH, FRAME_HEIGHT);

        JLabel welcomeTitle = new JLabel("Welcome, " + nasabah.getName());
        welcomeTitle.setBounds(50, 50, 200, 25);
        welcomeTitle.setFont(new Font("SansSerif", Font.BOLD, 24));
        welcomeTitle.setForeground(Color.WHITE);
        panel.add(welcomeTitle);

        JLabel saldoLabel = new JLabel("Current saldo : " + nasabah.getSaldo());
        saldoLabel.setBounds(50, 80, 500, 50);
        panel.add(saldoLabel);

        // ------------------------------------------------------------

        JButton CreateSaving = new JButton("Create Blue Saving");
        CreateSaving.setBounds(120, 240, 260, 50);
        panel.add(CreateSaving);

        CreateSaving.addActionListener(e -> {
            frame.dispose();
            new ShowMenuCreateBlueSaving();
        });

        // ------------------------------------------------------------

        JButton exitButton = new JButton("Logout");
        exitButton.setBounds(120, 600, 260, 50);
        Component.styleButton(exitButton, new Color(255, 69, 58), buttonFont);
        panel.add(exitButton);

        exitButton.addActionListener(e -> {
            frame.dispose();
            CurrentUser.getInstance().clearUser();
            new MainMenu();
        });

        frame.add(panel);
        frame.setVisible(true);

    }
}