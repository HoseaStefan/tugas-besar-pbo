package view;

import javax.swing.*;
import controller.BlueSavingController;
import java.awt.*;
import model.BlueSaving;
import model.CurrentUser;
import model.Nasabah;

public class DetailBlueSavingPage {
    private JFrame frame;

    public DetailBlueSavingPage(BlueSaving blueSaving) {
        CurrentUser currentUser = CurrentUser.getInstance();
        Nasabah nasabah = currentUser.getNasabah();
        showDetailPage(blueSaving, nasabah);
    }

    public void showDetailPage(BlueSaving blueSaving, Nasabah nasabah) {
        // Frame setup
        frame = new JFrame("Blue Saving Details");
        frame.setSize(500, 550);
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

        // Label for details
        Font detailFont = new Font("SansSerif", Font.PLAIN, 16);
        Color textColor = new Color(54, 69, 79);

        JLabel nameLabel = new JLabel("Nama Tabungan: " + blueSaving.getNamaTabungan());
        nameLabel.setBounds(20, 100, 460, 25);
        nameLabel.setFont(detailFont);
        nameLabel.setForeground(textColor);
        panel.add(nameLabel);

        JLabel saldoLabel = new JLabel("Saldo Saving: Rp. " + String.format("%,.2f", blueSaving.getSaldoSaving()));
        saldoLabel.setBounds(20, 140, 460, 25);
        saldoLabel.setFont(detailFont);
        saldoLabel.setForeground(textColor);
        panel.add(saldoLabel);

        JLabel tabunganHarianLabel = new JLabel(
                "Nominal debit/hari: Rp. " + String.format("%,.2f", blueSaving.getTabunganHarian()));
        tabunganHarianLabel.setBounds(20, 180, 460, 25);
        tabunganHarianLabel.setFont(detailFont);
        tabunganHarianLabel.setForeground(textColor);
        panel.add(tabunganHarianLabel);

        // Settings Button
        JButton settingsButton = new JButton("...");
        settingsButton.setBounds(450, 20, 30, 30);
        settingsButton.setFont(new Font("SansSerif", Font.BOLD, 12));
        settingsButton.setForeground(Color.BLACK);
        settingsButton.setBackground(Color.WHITE);
        settingsButton.setFocusPainted(false);
        settingsButton.setBorderPainted(false);
        settingsButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        settingsButton.addActionListener(e -> {
            frame.dispose();
            new SettingsBlueSaving(blueSaving);
        });
        panel.add(settingsButton);

        // Pindah Saldo Button
        JButton pindahSaldoButton = new JButton("Pindah Saldo");
        pindahSaldoButton.setBounds(100, 250, 130, 40);
        pindahSaldoButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        pindahSaldoButton.setForeground(Color.WHITE);
        pindahSaldoButton.setBackground(new Color(0, 123, 255));
        pindahSaldoButton.setFocusPainted(false);
        pindahSaldoButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        pindahSaldoButton.addActionListener(e -> {
            String userId = nasabah.getUser_id();
            String tabunganId = blueSaving.getTabungan_id();
            double nominal = Double
                    .parseDouble(JOptionPane.showInputDialog("Masukkan nominal yang ingin dipindahkan:"));

            boolean success = BlueSavingController.pindahSaldo(userId, nominal, tabunganId);
            if (success) {
                JOptionPane.showMessageDialog(frame, "Saldo berhasil dipindahkan!");

                double newSaldoUser = nasabah.getSaldo() - nominal;
                CurrentUser.getInstance().getNasabah().setSaldo(newSaldoUser);
                blueSaving.setSaldoSaving(blueSaving.getSaldoSaving() + nominal);

                frame.dispose();
                new DetailBlueSavingPage(blueSaving);
            } else {
                JOptionPane.showMessageDialog(frame, "Saldo tidak mencukupi atau terjadi kesalahan.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
        panel.add(pindahSaldoButton);

        // Tarik Saldo Button
        JButton tarikSaldoButton = new JButton("Tarik Saldo");
        tarikSaldoButton.setBounds(270, 250, 130, 40);
        tarikSaldoButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        tarikSaldoButton.setForeground(Color.WHITE);
        tarikSaldoButton.setBackground(new Color(0, 123, 255));
        tarikSaldoButton.setFocusPainted(false);
        tarikSaldoButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        tarikSaldoButton.addActionListener(e -> {
            String userId = nasabah.getUser_id();
            String tabunganId = blueSaving.getTabungan_id();
            double nominal = Double.parseDouble(JOptionPane.showInputDialog("Masukkan nominal yang ingin ditarik:"));

            boolean success = BlueSavingController.tarikSaldo(userId, nominal, tabunganId);
            if (success) {
                JOptionPane.showMessageDialog(frame, "Saldo berhasil ditarik!");

                double newSaldo = nasabah.getSaldo() + nominal;
                CurrentUser.getInstance().getNasabah().setSaldo(newSaldo);
                blueSaving.setSaldoSaving(blueSaving.getSaldoSaving() - nominal);

                frame.dispose();
                new DetailBlueSavingPage(blueSaving);
            } else {
                JOptionPane.showMessageDialog(frame, "Saldo BlueSaving tidak mencukupi atau terjadi kesalahan.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        panel.add(tarikSaldoButton);

        // Back Button
        JButton backButton = new JButton("Back to Menu");
        Component.styleRoundedButton(backButton, new Color(255, 69, 58), Color.WHITE);
        backButton.setBounds(120, 370, 260, 50);
        backButton.addActionListener(e -> {
            frame.dispose();
            new MenuBlueSaving();
        });
        // Adding hover effect for Back button
        Component.addHoverEffect(backButton, new Color(255, 69, 58), new Color(255, 82, 82));
        panel.add(backButton);

        // Finalize frame
        frame.add(panel);
        frame.setVisible(true);
    }
}
