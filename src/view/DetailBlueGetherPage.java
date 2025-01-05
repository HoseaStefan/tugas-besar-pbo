package view;

import javax.swing.*;
import controller.BlueGetherController;
import java.awt.*;
import model.BlueGether;
import model.CurrentUser;
import model.Nasabah;

public class DetailBlueGetherPage {
    private JFrame frame;

    public DetailBlueGetherPage(BlueGether blueGether) {
        CurrentUser currentUser = CurrentUser.getInstance();
        Nasabah nasabah = currentUser.getNasabah();
        showDetailPage(blueGether, nasabah);
    }

    public void showDetailPage(BlueGether blueGether, Nasabah nasabah) {
        // Frame setup
        frame = new JFrame("Blue Gether Details");
        frame.setSize(500, 550);
        frame.setUndecorated(true);
        frame.setLocationRelativeTo(null);

        // Panel setup
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.getHSBColor(0.6f, 0.7f, 0.9f));
        panel.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0, 50), 2));

        // Title Label
        JLabel titleLabel = new JLabel("Detail Blue Gether");
        titleLabel.setBounds(120, 30, 300, 40);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 26));
        titleLabel.setForeground(Color.WHITE);
        panel.add(titleLabel);

        // Label for details
        Font detailFont = new Font("SansSerif", Font.PLAIN, 16);
        Color textColor = new Color(54, 69, 79);

        JLabel nameLabel = new JLabel("Nama Tabungan: " + blueGether.getNamaTabungan());
        nameLabel.setBounds(20, 100, 460, 25);
        nameLabel.setFont(detailFont);
        nameLabel.setForeground(textColor);
        panel.add(nameLabel);

        JLabel saldoLabel = new JLabel("Saldo Gether: Rp. " + String.format("%,.2f", blueGether.getSaldoGether()));
        saldoLabel.setBounds(20, 140, 460, 25);
        saldoLabel.setFont(detailFont);
        saldoLabel.setForeground(textColor);
        panel.add(saldoLabel);

        JLabel tabunganHarianLabel = new JLabel(
                "Nominal debit/hari: Rp. " + String.format("%,.2f", blueGether.getTabunganHarian()));
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
            new SettingsBlueGether(blueGether);
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
            String tabunganId = blueGether.getTabungan_id();
            try {
                double nominal = Double
                        .parseDouble(JOptionPane.showInputDialog("Masukkan nominal yang ingin dipindahkan:"));

                boolean success = BlueGetherController.pindahSaldo(userId, nominal, tabunganId);
                if (success) {
                    JOptionPane.showMessageDialog(frame, "Saldo berhasil dipindahkan!");

                    double newSaldoUser = nasabah.getSaldo() - nominal;
                    CurrentUser.getInstance().getNasabah().setSaldo(newSaldoUser);
                    blueGether.setSaldoGether(blueGether.getSaldoGether() + nominal);

                    frame.dispose();
                    new DetailBlueGetherPage(blueGether);
                } else {
                    JOptionPane.showMessageDialog(frame, "Saldo tidak mencukupi atau terjadi kesalahan.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Input nominal tidak valid.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        panel.add(pindahSaldoButton);

        boolean owner = BlueGetherController.cekOwnerBlueGether(blueGether.getTabungan_id(), nasabah.getUser_id());

        if (owner) {

            JButton tambahMemberButton = new JButton();
            Component.styleMemberButton(tambahMemberButton, Color.WHITE, Color.WHITE);
            tambahMemberButton.setBounds(350, 100, 120, 100); // Ukuran lebih besar dari sebelumnya
            panel.add(tambahMemberButton);

            tambahMemberButton.addActionListener(e -> {
                String noRekeningStr = JOptionPane.showInputDialog(frame,
                        "Masukkan Nomor Rekening Member (angka):",
                        "Tambah Member",
                        JOptionPane.QUESTION_MESSAGE);

                if (noRekeningStr == null || noRekeningStr.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(frame,
                            "Nomor Rekening tidak boleh kosong.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int noRekening = Integer.parseInt(noRekeningStr.trim());

                if (noRekening == nasabah.getNomorRekening()) {
                    JOptionPane.showMessageDialog(frame,
                            "Nomor Rekening Anda sudah dalam daftar.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String userId = BlueGetherController.getUserIdNasabah(noRekening);
                boolean successTambahMember = BlueGetherController
                        .tambahNasabahToListNasabah(blueGether.getTabungan_id(), userId);

                if (successTambahMember) {
                    JOptionPane.showMessageDialog(frame,
                            "Berhasil Menambahkan Member.",
                            "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                    return;
                } else {
                    JOptionPane.showMessageDialog(frame,
                            "Gagal Menambahkan Member.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

            });

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
                String tabunganId = blueGether.getTabungan_id();
                try {
                    double nominal = Double
                            .parseDouble(JOptionPane.showInputDialog("Masukkan nominal yang ingin ditarik:"));

                    boolean success = BlueGetherController.tarikSaldoGether(userId, nominal, tabunganId);
                    if (success) {
                        JOptionPane.showMessageDialog(frame, "Saldo berhasil ditarik!");

                        double newSaldo = nasabah.getSaldo() + nominal;
                        CurrentUser.getInstance().getNasabah().setSaldo(newSaldo);
                        blueGether.setSaldoGether(blueGether.getSaldoGether() - nominal);

                        frame.dispose();
                        new DetailBlueGetherPage(blueGether);
                    } else {
                        JOptionPane.showMessageDialog(frame, "Saldo BlueGether tidak mencukupi atau terjadi kesalahan.",
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Input nominal tidak valid.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            });
            panel.add(tarikSaldoButton);

        }

        // Back Button
        JButton backButton = new JButton("Back to Menu");
        Component.styleRoundedButton(backButton, new Color(255, 69, 58), Color.WHITE);
        backButton.setBounds(120, 370, 260, 50);
        backButton.addActionListener(e -> {
            frame.dispose();
            new MenuBlueGether();
        });
        // Adding hover effect for Back button
        Component.addHoverEffect(backButton, new Color(255, 69, 58), new Color(255, 82, 82));
        panel.add(backButton);

        // Finalize frame
        frame.add(panel);
        frame.setVisible(true);
    }
}
