package view;

import javax.swing.*;
import java.awt.*;
import model.BlueSaving;

public class DetailBlueSavingPage {
    private JFrame frame;

    public DetailBlueSavingPage(BlueSaving blueSaving) {
        showDetailPage(blueSaving);
    }

    public void showDetailPage(BlueSaving blueSaving) {
        // Frame setup
        frame = new JFrame("Blue Saving Details");
        frame.setSize(500, 500);
        frame.setUndecorated(true);
        frame.setLocationRelativeTo(null);

        // Panel setup
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.getHSBColor(0.6f, 0.7f, 0.9f));
        panel.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0, 50), 2));

        // Title Label
        JLabel titleLabel = new JLabel("Detail Blue Saving");
        titleLabel.setBounds(120, 30, 300, 40);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 26));
        titleLabel.setForeground(Color.WHITE);
        panel.add(titleLabel);

        // Create a rounded panel to contain details
        JPanel detailPanel = new JPanel();
        detailPanel.setLayout(null);
        detailPanel.setBackground(new Color(245, 245, 245)); // Warna putih lembut
        detailPanel.setBounds(50, 90, 400, 250);
        detailPanel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 2));
        panel.add(detailPanel);

        // Label for details
        Font detailFont = new Font("SansSerif", Font.PLAIN, 16);
        Color textColor = new Color(54, 69, 79);

        JLabel nameLabel = new JLabel("Nama Tabungan: " + blueSaving.getNamaTabungan());
        nameLabel.setBounds(20, 20, 360, 25);
        nameLabel.setFont(detailFont);
        nameLabel.setForeground(textColor);
        detailPanel.add(nameLabel);

        JLabel saldoLabel = new JLabel("Saldo: Rp. " + String.format("%,.2f", blueSaving.getSaldoSaving()));
        saldoLabel.setBounds(20, 60, 360, 25);
        saldoLabel.setFont(detailFont);
        saldoLabel.setForeground(textColor);
        detailPanel.add(saldoLabel);

        JLabel targetLabel = new JLabel("Target Saldo: Rp. " + String.format("%,.2f", blueSaving.getTargetSaldo()));
        targetLabel.setBounds(20, 100, 360, 25);
        targetLabel.setFont(detailFont);
        targetLabel.setForeground(textColor);
        detailPanel.add(targetLabel);

        JLabel tabunganHarianLabel = new JLabel(
                "Tabungan Harian: Rp. " + String.format("%,.2f", blueSaving.getTabunganHarian()));
        tabunganHarianLabel.setBounds(20, 140, 360, 25);
        tabunganHarianLabel.setFont(detailFont);
        tabunganHarianLabel.setForeground(textColor);
        detailPanel.add(tabunganHarianLabel);

        JLabel jangkaWaktuLabel = new JLabel("Jangka Waktu: " + blueSaving.getJangkaWaktu() + " bulan");
        jangkaWaktuLabel.setBounds(20, 180, 360, 25);
        jangkaWaktuLabel.setFont(detailFont);
        jangkaWaktuLabel.setForeground(textColor);
        detailPanel.add(jangkaWaktuLabel);

        // Back Button
        JButton backButton = new JButton("Back to Menu");
        backButton.setBounds(120, 370, 260, 50);
        backButton.setFont(new Font("SansSerif", Font.BOLD, 18));
        backButton.setForeground(Color.WHITE);
        backButton.setBackground(new Color(255, 69, 58));
        backButton.setFocusPainted(false);
        backButton.setBorderPainted(false);
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Hover effect for button
        backButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                backButton.setBackground(new Color(220, 50, 40));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                backButton.setBackground(new Color(255, 69, 58));
            }
        });

        backButton.addActionListener(e -> {
            frame.dispose();
            new MenuBlueSaving(); // Kembali ke halaman sebelumnya
        });
        panel.add(backButton);

        // Frame finalize
        frame.add(panel);
        frame.setVisible(true);
    }
}
