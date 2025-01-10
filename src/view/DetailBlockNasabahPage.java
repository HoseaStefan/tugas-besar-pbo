package view;

import javax.swing.*;

import controller.BlockNasabahController;

import java.awt.*;
import model.Nasabah;

public class DetailBlockNasabahPage {
    private JFrame frame;
    private JLabel statusLabel; // Tambahkan referensi untuk statusLabel

    public DetailBlockNasabahPage(Nasabah nasabah) {
        showDetailPage(nasabah);
    }

    public void showDetailPage(Nasabah nasabah) {
        // Frame setup
        frame = new JFrame("Nasabah Details");
        frame.setSize(500, 550);
        frame.setUndecorated(true);
        frame.setLocationRelativeTo(null);

        // Panel setup
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.getHSBColor(0.6f, 0.7f, 0.9f));
        panel.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0, 50), 2));

        // Title Label
        JLabel titleLabel = new JLabel("Detail Nasabah");
        titleLabel.setBounds(120, 30, 300, 40);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 26));
        titleLabel.setForeground(Color.WHITE);
        panel.add(titleLabel);

        // Label for details
        Font detailFont = new Font("SansSerif", Font.PLAIN, 16);
        Color textColor = new Color(54, 69, 79);

        JLabel idNasabahLabel = new JLabel("Nasabah ID: " + nasabah.getUser_id());
        idNasabahLabel.setBounds(20, 100, 460, 25);
        idNasabahLabel.setFont(detailFont);
        idNasabahLabel.setForeground(textColor);
        panel.add(idNasabahLabel);

        JLabel nameNasabahLabel = new JLabel("Nama Nasbah: " + nasabah.getUsername());
        nameNasabahLabel.setBounds(20, 140, 460, 25);
        nameNasabahLabel.setFont(detailFont);
        nameNasabahLabel.setForeground(textColor);
        panel.add(nameNasabahLabel);

        JLabel emailLabel = new JLabel("Email Nasabah: " + nasabah.getUsername());
        emailLabel.setBounds(20, 180, 460, 25);
        emailLabel.setFont(detailFont);
        emailLabel.setForeground(textColor);
        panel.add(emailLabel);

        // Simpan statusLabel sebagai atribut kelas
        statusLabel = new JLabel("Status: " + nasabah.getStatus());
        statusLabel.setBounds(20, 220, 460, 25);
        statusLabel.setFont(detailFont);
        statusLabel.setForeground(textColor);
        panel.add(statusLabel);

        JButton UbahStatus = new JButton("Ubah Status");
        Component.styleRoundedButton(UbahStatus, new Color(0, 102, 204), Color.WHITE);
        UbahStatus.setBounds(120, 340, 260, 50);

        UbahStatus.addActionListener(e -> {
            String[] options = { "ALLOWED", "BLOCKED" };

            // Menampilkan dialog dengan opsi pilihan
            String selectedStatus = (String) JOptionPane.showInputDialog(
                    null,
                    "Pilih status untuk nasabah:",
                    "Status Nasabah",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[0]);

            // Menampilkan hasil pilihan
            if (selectedStatus != null) {
                boolean isUpdated = BlockNasabahController.updateStatusNasabah(nasabah, selectedStatus);

                if (isUpdated) {
                    nasabah.setStatus(selectedStatus);

                    // Perbarui teks pada statusLabel
                    statusLabel.setText("Status: " + nasabah.getStatus());

                    JOptionPane.showMessageDialog(frame, "Status berhasil diperbarui!", "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(frame, "Gagal memperbarui status.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            } else {
                System.out.println("Tidak ada status yang dipilih.");
            }
        });

        Component.addHoverEffect(UbahStatus, new Color(0, 102, 204), new Color(0, 123, 180));
        panel.add(UbahStatus);

        // Back Button
        JButton backButton = new JButton("Back to Menu");
        Component.styleRoundedButton(backButton, new Color(255, 69, 58), Color.WHITE);
        backButton.setBounds(120, 400, 260, 50);
        backButton.addActionListener(e -> {
            frame.dispose();
            new MenuBlockNasabahByAdmin();
        });
        // Adding hover effect for Back button
        Component.addHoverEffect(backButton, new Color(255, 69, 58), new Color(255, 82, 82));
        panel.add(backButton);

        JButton backMenuReportButton = new JButton("Back to Menu Report");
        Component.styleRoundedButton(backMenuReportButton, new Color(255, 69, 58), Color.WHITE);
        backMenuReportButton.setBounds(120, 460, 260, 50);
        backMenuReportButton.addActionListener(e -> {
            frame.dispose();
            new MenuReportAdmin();
        });
        // Adding hover effect for Back button
        Component.addHoverEffect(backMenuReportButton, new Color(255, 69, 58), new Color(255, 82, 82));
        panel.add(backMenuReportButton);

        // Finalize frame
        frame.add(panel);
        frame.setVisible(true);
    }
}
