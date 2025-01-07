package view;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.*;

import model.BlueGether;
import model.CurrentUser;
import model.Nasabah;
import controller.BlueGetherController;

public class SettingsBlueGether {
    private JFrame frame;

    public SettingsBlueGether(BlueGether blueGether) {
        CurrentUser currentUser = CurrentUser.getInstance();
        Nasabah nasabah = currentUser.getNasabah();
        showSettingsBlueGether(blueGether, nasabah);
    }

    public void showSettingsBlueGether(BlueGether blueGether, Nasabah nasabah) {
        frame = new JFrame("Blue Gether Details");
        frame.setSize(500, 500);
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

        Font detailFont = new Font("SansSerif", Font.PLAIN, 16);
        Color textColor = new Color(54, 69, 79);

        // Nama Tabungan
        JLabel nameLabel = new JLabel("Nama Tabungan: " + blueGether.getNamaTabungan());
        nameLabel.setBounds(20, 80, 460, 25);
        nameLabel.setFont(detailFont);
        nameLabel.setForeground(textColor);
        panel.add(nameLabel);

        // Target Saldo Label
        JLabel targetLabel = new JLabel("Target Saldo: Rp. " + String.format("%,.2f", blueGether.getTargetSaldo()));
        targetLabel.setBounds(20, 120, 360, 25);
        targetLabel.setFont(detailFont);
        targetLabel.setForeground(textColor);
        panel.add(targetLabel);

        // Jangka Waktu Label
        JLabel jangkaWaktuLabel = new JLabel("Jangka Waktu: " + blueGether.getJangkaWaktu() + " bulan");
        jangkaWaktuLabel.setBounds(20, 160, 360, 25);
        jangkaWaktuLabel.setFont(detailFont);
        jangkaWaktuLabel.setForeground(textColor);
        panel.add(jangkaWaktuLabel);

        // Atur Target Button
        JButton aturTargetButton = new JButton("Atur");
        aturTargetButton.setBounds(350, 120, 120, 25);
        aturTargetButton.setFont(new Font("SansSerif", Font.BOLD, 12));
        aturTargetButton.setForeground(Color.BLACK);
        aturTargetButton.setBackground(Color.WHITE);
        aturTargetButton.setFocusPainted(false);
        aturTargetButton.setBorderPainted(false);
        aturTargetButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panel.add(aturTargetButton);

        // Atur Jangka Button
        JButton aturJangkaButton = new JButton("Atur");
        aturJangkaButton.setBounds(350, 160, 120, 25);
        aturJangkaButton.setFont(new Font("SansSerif", Font.BOLD, 12));
        aturJangkaButton.setForeground(Color.BLACK);
        aturJangkaButton.setBackground(Color.WHITE);
        aturJangkaButton.setFocusPainted(false);
        aturJangkaButton.setBorderPainted(false);
        aturJangkaButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panel.add(aturJangkaButton);

        // Ganti Nama Button
        JButton gantiNamaButton = new JButton("Ganti Nama");
        gantiNamaButton.setBounds(350, 80, 120, 25);
        gantiNamaButton.setFont(new Font("SansSerif", Font.BOLD, 12));
        gantiNamaButton.setForeground(Color.BLACK);
        gantiNamaButton.setBackground(Color.WHITE);
        gantiNamaButton.setFocusPainted(false);
        gantiNamaButton.setBorderPainted(false);
        gantiNamaButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panel.add(gantiNamaButton);

        // Action Listeners for Buttons
        gantiNamaButton.addActionListener(e -> {
            String newName = JOptionPane.showInputDialog(frame, "Masukkan Nama Tabungan Baru:");
            if (newName != null && !newName.trim().isEmpty()) {
                if (BlueGetherController.isNamaTabunganAlreadyExist(blueGether.getuser_id(), newName)) {
                    JOptionPane.showMessageDialog(frame, "Nama tabungan sudah digunakan, pilih nama lain.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                } else {
                    blueGether.setNamaTabungan(newName);

                    boolean isUpdated = BlueGetherController.gantiNamaBlueGether(blueGether, blueGether.getuser_id());

                    if (isUpdated) {
                        JOptionPane.showMessageDialog(frame, "Nama Blue Gether berhasil diperbarui!", "Success",
                                JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(frame, "Gagal memperbarui nama tabungan.", "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Nama tabungan tidak boleh kosong.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        aturTargetButton.addActionListener(e -> {
            String newTarget = JOptionPane.showInputDialog(frame, "Masukkan Target Baru (Rp):");
            if (newTarget != null && !newTarget.trim().isEmpty()) {
                try {
                    double target = Double.parseDouble(newTarget);
                    blueGether.setTargetSaldo(target);
                    targetLabel.setText("Target Saldo: Rp. " + String.format("%,.2f", target)); // Perbarui label
                    BlueGetherController.updateBlueGetherInDatabase(blueGether);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Input tidak valid. Masukkan angka.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        aturJangkaButton.addActionListener(e -> {
            String newJangka = JOptionPane.showInputDialog(frame, "Masukkan Jangka Waktu Baru (Bulan):");

            if (newJangka != null && !newJangka.trim().isEmpty()) {
                try {
                    int jangka = Integer.parseInt(newJangka);
                    blueGether.setJangkaWaktu(jangka); // Update objek BlueGether
                    jangkaWaktuLabel.setText("Jangka Waktu: " + jangka + " bulan"); // Perbarui label
                    BlueGetherController.updateBlueGetherInDatabase(blueGether);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(
                            frame,
                            "Input tidak valid. Masukkan angka.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Tombol Tutup Blue Gether
        JButton tutupBlueGetherButton = new JButton("Tutup Blue Gether");
        tutupBlueGetherButton.setBounds(120, 280, 260, 50);
        tutupBlueGetherButton.setFont(new Font("SansSerif", Font.BOLD, 18));
        tutupBlueGetherButton.setForeground(Color.WHITE);
        tutupBlueGetherButton.setBackground(new Color(255, 69, 58));
        tutupBlueGetherButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        tutupBlueGetherButton.addActionListener(e -> {
            // Konfirmasi untuk penutupan Blue Gether
            int confirmation = JOptionPane.showConfirmDialog(frame,
                    "Apakah Anda yakin ingin menutup Blue Gether ini? Saldo akan ditarik terlebih dahulu.",
                    "Konfirmasi Tutup Blue Gether", JOptionPane.YES_NO_OPTION);

            if (confirmation == JOptionPane.YES_OPTION) {
                // Menarik saldo terlebih dahulu sebelum menutup Blue Gether
                double nominal = blueGether.getSaldoGether(); // Mengambil saldo yang ada di Blue Gether
                String userId = blueGether.getuser_id(); // Mendapatkan ID pengguna yang terkait

                boolean tarikSaldo = BlueGetherController.tarikSaldoGether(userId, nominal,
                        blueGether.getTabungan_id());
                if (tarikSaldo) {
                    // Jika penarikan saldo berhasil, lanjutkan untuk menutup Blue Gether
                    boolean isClosed = BlueGetherController.tutupBlueGether(blueGether);

                    if (isClosed) {
                        JOptionPane.showMessageDialog(frame, "Blue Gether berhasil ditutup.", "Success",
                                JOptionPane.INFORMATION_MESSAGE);
                        double newSaldo = nasabah.getSaldo() + nominal;
                        CurrentUser.getInstance().getNasabah().setSaldo(newSaldo);
                        frame.dispose(); // Tutup halaman pengaturan dan kembali ke halaman sebelumnya
                        new MenuBlueGether(); // Ganti dengan halaman yang sesuai (misalnya, halaman menu utama)
                    } else {
                        JOptionPane.showMessageDialog(frame, "Gagal menutup Blue Gether.", "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Gagal menarik saldo. Pastikan saldo cukup.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        panel.add(tutupBlueGetherButton);

        // Back Button
        JButton backButton = new JButton("Back to Menu");
        Component.styleRoundedButton(backButton, new Color(255, 69, 58), Color.WHITE);
        backButton.setBounds(120, 370, 260, 50);
        backButton.addActionListener(e -> {
            frame.dispose();
            new DetailBlueGetherPage(blueGether); // Kembali ke halaman sebelumnya
        });
        // Adding hover effect for Back button
        Component.addHoverEffect(backButton, new Color(255, 69, 58), new Color(255, 82, 82));
        panel.add(backButton);

        // Frame finalize
        frame.add(panel);
        frame.setVisible(true);
    }
}
