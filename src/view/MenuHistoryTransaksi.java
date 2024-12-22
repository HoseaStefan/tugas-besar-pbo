package view;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import model.CurrentUser;
import model.Nasabah;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class MenuHistoryTransaksi {
    public MenuHistoryTransaksi(){
        showMenuHistoryTransaksi();
    }

    public void showMenuHistoryTransaksi(){
        CurrentUser currentUser = CurrentUser.getInstance();
        Nasabah nasabah = currentUser.getNasabah();

        Font buttonFont = new Font("SansSerif", Font.BOLD, 18);

        JFrame frame = new JFrame("Histori Transaksi");
        frame.setUndecorated(true);
        frame.setBounds(0, 0, 500, 700);
        frame.setShape(new RoundRectangle2D.Double(0, 0, 500, 700, 30, 30));
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.getHSBColor(0.6f, 0.7f, 0.9f));
        panel.setBounds(0, 0, 500, 700);

        JLabel title = new JLabel("Transaction History");
        title.setBounds(150, 50, 500, 50);
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        title.setForeground(Color.WHITE);
        panel.add(title);

        JButton exitButton = new JButton("Back To Homepage");
        exitButton.setBounds(120, 600, 260, 50);
        exitButton.setFont(buttonFont);
        exitButton.setBackground(new Color(255, 69, 58));
        exitButton.setForeground(Color.WHITE);
        exitButton.setFocusPainted(false);

        exitButton.addActionListener(e -> {
            frame.dispose();
            new MainMenu();
        });

        panel.add(exitButton);

        frame.add(panel);
        frame.setVisible(true);
    }
}
