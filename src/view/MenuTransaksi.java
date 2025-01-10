package view;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import controller.UserController;
import model.CurrentUser;
import model.Nasabah;
import model.User;
import model.UserType;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class MenuTransaksi {

    private JFrame frame;

    public MenuTransaksi() {
        showMenuTransaksi();
    }

    public void showMenuTransaksi() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();

        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;

        final int FRAME_WIDTH = 500;
        final int FRAME_HEIGHT = 700;

        int start_x = screenWidth / 2 - (FRAME_WIDTH / 2);
        int start_y = screenHeight / 2 - (FRAME_HEIGHT / 2);

        Font buttonFont = new Font("SansSerif", Font.BOLD, 18);

        frame = new JFrame("Transaksi Menu");
        frame.setUndecorated(true);
        frame.setBounds(start_x, start_y, FRAME_WIDTH, FRAME_HEIGHT);
        frame.setShape(new RoundRectangle2D.Double(0, 0, FRAME_WIDTH, FRAME_HEIGHT, 30, 30));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.getHSBColor(0.6f, 0.7f, 0.9f));
        panel.setBounds(0, 0, FRAME_WIDTH, FRAME_HEIGHT);

        JLabel title = new JLabel("TRANSAKSI");
        title.setBounds(175, 150, 500, 50);
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        title.setForeground(Color.WHITE);
        panel.add(title);

        frame = new JFrame("Main Menu");
        frame.setUndecorated(true);
        frame.setBounds(start_x, start_y, FRAME_WIDTH, FRAME_HEIGHT);
        frame.setShape(new RoundRectangle2D.Double(0, 0, FRAME_WIDTH, FRAME_HEIGHT, 30, 30));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton setorSaldoButton = new JButton("Setor Saldo");
        setorSaldoButton.setBounds(120, 275, 260, 50);
        Component.styleButton(setorSaldoButton, new Color(3, 123, 252), buttonFont);
        setorSaldoButton.addActionListener(e -> {
            frame.dispose();
            new FormSetorSaldo();
        });
        panel.add(setorSaldoButton);

        JButton transferButton = new JButton("Transfer");
        transferButton.setBounds(120, 345, 260, 50);
        Component.styleButton(transferButton, new Color(3, 123, 252), buttonFont);
        transferButton.addActionListener(e -> {
            frame.dispose();
            new FormTransferSaldo();
        });
        panel.add(transferButton);

        JButton topUpButton = new JButton("Top Up E-money");
        topUpButton.setBounds(120, 415, 260, 50);
        Component.styleButton(topUpButton, new Color(3, 123, 252), buttonFont);
        topUpButton.addActionListener(e -> {
            frame.dispose();
            new FormTopUpEmoney();
        });
        panel.add(topUpButton);

        JButton exitButton = new JButton("Back To Homepage");
        exitButton.setBounds(120, 600, 260, 50);
        Component.styleButton(exitButton, new Color(255, 69, 58), buttonFont);
        exitButton.addActionListener(e -> {
            frame.dispose();
            new MenuNasabah();
        });
        panel.add(exitButton);

        frame.add(panel);
        frame.setVisible(true);

    }
}
