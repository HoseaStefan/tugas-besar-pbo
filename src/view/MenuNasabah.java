package view;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import javax.swing.*;
import model.CurrentUser;
import model.Nasabah;

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
        welcomeTitle.setBounds(50, 50, 400, 30);
        welcomeTitle.setFont(new Font("SansSerif", Font.BOLD, 24));
        welcomeTitle.setForeground(Color.WHITE);
        panel.add(welcomeTitle);

        JLabel norekLabel = new JLabel("Nomor Rekeningmu : " + nasabah.getNomorRekening());
        norekLabel.setBounds(50, 100, 400, 25);
        norekLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        norekLabel.setForeground(Color.WHITE);
        panel.add(norekLabel);

        JLabel saldoLabel = new JLabel("Current saldo : Rp." + nasabah.getSaldo());
        saldoLabel.setBounds(50, 130, 400, 25);
        saldoLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        saldoLabel.setForeground(Color.WHITE);
        panel.add(saldoLabel);

        JButton btnLoyalty = new JButton("Loyalty");
        btnLoyalty.setBounds(120, 200, 260, 50);
        Component.styleButton(btnLoyalty, new Color(3, 123, 252), buttonFont);
        panel.add(btnLoyalty);

        btnLoyalty.addActionListener((actionEvent) -> {
            frame.dispose();
            new MenuBeliLoyalty();
        });

        JButton createSavingButton = new JButton("Tabungan");
        createSavingButton.setBounds(120, 300, 260, 50);
        Component.styleButton(createSavingButton, new Color(3, 123, 252), buttonFont);
        panel.add(createSavingButton);

        createSavingButton.addActionListener(e -> {
            frame.dispose();
            new MenuTabungan();
        });

        JButton transaksiButton = new JButton("Transaksi");
        transaksiButton.setBounds(120, 400, 260, 50);
        Component.styleButton(transaksiButton, new Color(3, 123, 252), buttonFont);
        panel.add(transaksiButton);

        transaksiButton.addActionListener(e -> {
            frame.dispose();
            new MenuTransaksi();
        });

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