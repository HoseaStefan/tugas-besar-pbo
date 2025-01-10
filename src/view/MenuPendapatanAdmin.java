package view;

import controller.AdminController;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import javax.swing.*;
import model.TransaksiType;

public class MenuPendapatanAdmin {

    public MenuPendapatanAdmin() {
        showMenuPendapatanAdmin();
    }

    public void showMenuPendapatanAdmin() {
        Font buttonFont = new Font("SansSerif", Font.BOLD, 18);

        JFrame frame = new JFrame("Pendapatan Admin");
        frame.setUndecorated(true);
        frame.setBounds(0, 0, 500, 700);
        frame.setShape(new RoundRectangle2D.Double(0, 0, 500, 700, 30, 30));
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.getHSBColor(0.6f, 0.7f, 0.9f));
        panel.setBounds(0, 0, 500, 700);

        JLabel title = new JLabel("Pendapatan Admin");
        title.setBounds(140, 20, 500, 50);
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        title.setForeground(Color.WHITE);
        panel.add(title);

        JLabel setorUangLabel = new JLabel("Pendapatan dari Setor Uang:");
        setorUangLabel.setBounds(50, 100, 400, 30);
        setorUangLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        setorUangLabel.setForeground(Color.WHITE);
        panel.add(setorUangLabel);

        JLabel setorUangValue = new JLabel("Rp 0,000");
        setorUangValue.setBounds(50, 130, 400, 30);
        setorUangValue.setFont(new Font("SansSerif", Font.PLAIN, 16));
        setorUangValue.setForeground(Color.WHITE);
        panel.add(setorUangValue);

        JLabel transferLabel = new JLabel("Pendapatan dari Transfer:");
        transferLabel.setBounds(50, 170, 400, 30);
        transferLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        transferLabel.setForeground(Color.WHITE);
        panel.add(transferLabel);

        JLabel transferValue = new JLabel("Rp 0,000");
        transferValue.setBounds(50, 200, 400, 30);
        transferValue.setFont(new Font("SansSerif", Font.PLAIN, 16));
        transferValue.setForeground(Color.WHITE);
        panel.add(transferValue);

        JLabel topUpEButtonLabel = new JLabel("Pendapatan dari Top Up E-Money:");
        topUpEButtonLabel.setBounds(50, 240, 400, 30);
        topUpEButtonLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        topUpEButtonLabel.setForeground(Color.WHITE);
        panel.add(topUpEButtonLabel);

        JLabel topUpEButtonValue = new JLabel("Rp 0,000");
        topUpEButtonValue.setBounds(50, 270, 400, 30);
        topUpEButtonValue.setFont(new Font("SansSerif", Font.PLAIN, 16));
        topUpEButtonValue.setForeground(Color.WHITE);
        panel.add(topUpEButtonValue);

        JLabel loyaltyPackageLabel = new JLabel("Pendapatan dari Pembelian Loyalty Package:");
        loyaltyPackageLabel.setBounds(50, 310, 400, 30);
        loyaltyPackageLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        loyaltyPackageLabel.setForeground(Color.WHITE);
        panel.add(loyaltyPackageLabel);

        JLabel loyaltyPackageValue = new JLabel("Rp 0,000");
        loyaltyPackageValue.setBounds(50, 340, 400, 30);
        loyaltyPackageValue.setFont(new Font("SansSerif", Font.PLAIN, 16));
        loyaltyPackageValue.setForeground(Color.WHITE);
        panel.add(loyaltyPackageValue);

        JLabel adminTotalLabel = new JLabel("Total Pendapatan Admin:");
        adminTotalLabel.setBounds(50, 450, 400, 30);
        adminTotalLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        adminTotalLabel.setForeground(Color.WHITE);
        panel.add(adminTotalLabel);

        JLabel adminTotalValue = new JLabel("Rp 0,000");
        adminTotalValue.setBounds(50, 480, 400, 30);
        adminTotalValue.setFont(new Font("SansSerif", Font.PLAIN, 16));
        adminTotalValue.setForeground(Color.WHITE);
        panel.add(adminTotalValue);

        double setorUangPendapatan = AdminController.pendapatanAdminTransaksi(TransaksiType.SETOR);
        double transferPendapatan = AdminController.pendapatanAdminTransaksi(TransaksiType.TRANSFER);
        double topUpPendapatan = AdminController.pendapatanAdminTransaksi(TransaksiType.TOPUP);
        double loyaltyPackagePendapatan = AdminController.pendapatanAdminLoyalty(); 

        setorUangValue.setText("Rp " + formatCurrency(setorUangPendapatan));
        transferValue.setText("Rp " + formatCurrency(transferPendapatan));
        topUpEButtonValue.setText("Rp " + formatCurrency(topUpPendapatan));
        loyaltyPackageValue.setText("Rp " + formatCurrency(loyaltyPackagePendapatan));

        double totalPendapatan = setorUangPendapatan + transferPendapatan + topUpPendapatan + loyaltyPackagePendapatan;
        adminTotalValue.setText("Rp " + formatCurrency(totalPendapatan));

        JButton exitButton = new JButton("Back To Homepage");
        exitButton.setBounds(120, 600, 260, 50);
        exitButton.setFont(buttonFont);
        exitButton.setBackground(new Color(255, 69, 58));
        exitButton.setForeground(Color.WHITE);
        exitButton.setFocusPainted(false);

        exitButton.addActionListener(e -> {
            frame.dispose();
            new MenuAdmin();
        });

        panel.add(exitButton);
        frame.add(panel);
        frame.setVisible(true);
    }

    private String formatCurrency(double amount) {
        return String.format("%,.0f", amount); 
    }
}
