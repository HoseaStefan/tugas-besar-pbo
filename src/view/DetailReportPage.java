package view;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import controller.ReportController;

import model.Nasabah;
import model.Report;
import model.StatusReport;

public class DetailReportPage {
    private JFrame frame;
    private JLabel statusLabel; // Tambahkan referensi untuk statusLabel

    public DetailReportPage(Report report) {
        showDetailPage(report);
    }

    public void showDetailPage(Report report) {
        // Frame setup
        frame = new JFrame("Report Details");
        frame.setSize(500, 550);
        frame.setUndecorated(true);
        frame.setLocationRelativeTo(null);

        // Panel setup
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.getHSBColor(0.6f, 0.7f, 0.9f));
        panel.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0, 50), 2));

        // Title Label
        JLabel titleLabel = new JLabel("Detail Report");
        titleLabel.setBounds(150, 30, 300, 40);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 26));
        titleLabel.setForeground(Color.WHITE);
        panel.add(titleLabel);

        // Label for details
        Font detailFont = new Font("SansSerif", Font.PLAIN, 16);
        Color textColor = new Color(54, 69, 79);

        JLabel idReportLabel = new JLabel("Report ID: " + report.getReport_id());
        idReportLabel.setBounds(20, 100, 460, 25);
        idReportLabel.setFont(detailFont);
        idReportLabel.setForeground(textColor);
        panel.add(idReportLabel);

        JLabel idNasabahLabel = new JLabel("Nasabah ID: " + report.getNasabah_id());
        idNasabahLabel.setBounds(20, 140, 460, 25);
        idNasabahLabel.setFont(detailFont);
        idNasabahLabel.setForeground(textColor);
        panel.add(idNasabahLabel);

        JLabel emailVerifikasiLabel = new JLabel("Email_Verifikasi: " + report.getEmailVerifikasi());
        emailVerifikasiLabel.setBounds(20, 180, 460, 25);
        emailVerifikasiLabel.setFont(detailFont);
        emailVerifikasiLabel.setForeground(textColor);
        panel.add(emailVerifikasiLabel);

        // Simpan statusLabel sebagai atribut kelas
        statusLabel = new JLabel("Status report: " + report.getStatusReport());
        statusLabel.setBounds(20, 220, 460, 25);
        statusLabel.setFont(detailFont);
        statusLabel.setForeground(textColor);
        panel.add(statusLabel);

        JLabel massageLabel = new JLabel("Massage: " + report.getDeskripsi());
        massageLabel.setBounds(20, 260, 460, 25);
        massageLabel.setFont(detailFont);
        massageLabel.setForeground(textColor);
        panel.add(massageLabel);

        JButton UbahStatus = new JButton("Ubah Status");
        Component.styleRoundedButton(UbahStatus, new Color(0, 102, 204), Color.WHITE);
        UbahStatus.setBounds(120, 340, 260, 50);

        UbahStatus.addActionListener(e -> {
            String[] options = { "DIABAIKAN", "DITERIMA" };

            // Menampilkan dialog dengan opsi pilihan
            String selectedStatus = (String) JOptionPane.showInputDialog(
                    null,
                    "Pilih status untuk report:",
                    "Status Report",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[0] // Default value
            );

            // Menampilkan hasil pilihan
            if (selectedStatus != null) {
                boolean isUpdated = ReportController.updateStatusReport(report, selectedStatus);

                if (isUpdated) {
                    // Perbarui status di model
                    StatusReport status = StatusReport.valueOf(selectedStatus);
                    report.setStatusReport(status);

                    // Perbarui teks pada statusLabel
                    statusLabel.setText("Status: " + report.getStatusReport());

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

        JButton blockNasabahButton = new JButton("Status nasabah");
        Component.styleRoundedButton(blockNasabahButton, new Color(255, 69, 58), Color.WHITE);
        blockNasabahButton.setBounds(300, 220, 180, 25);

        blockNasabahButton.addActionListener(e -> {
            List<Nasabah> nasabahs = ReportController.getNasabahByUserId(report.getNasabah_id());

            if (nasabahs == null || nasabahs.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Nasabah tidak ditemukan.", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (nasabahs.size() == 1) {
                // Jika hanya ada satu Nasabah, buka halaman detail langsung
                frame.dispose();
                new DetailBlockNasabahPage(nasabahs.get(0)); // Kirim Nasabah pertama
            } else {
                // Jika ada beberapa Nasabah, tampilkan dialog untuk memilih
                String[] nasabahNames = nasabahs.stream()
                        .map(Nasabah::getName) // Ambil nama lengkap Nasabah
                        .toArray(String[]::new);

                // Tampilkan dialog pilihan
                String selectedName = (String) JOptionPane.showInputDialog(
                        frame,
                        "Pilih Nasabah:",
                        "Pilih Nasabah",
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        nasabahNames,
                        nasabahNames[0]);

                // Jika pengguna memilih Nasabah
                if (selectedName != null) {
                    // Cari Nasabah yang sesuai berdasarkan nama
                    for (Nasabah nasabah : nasabahs) {
                        if (nasabah.getName().equals(selectedName)) {
                            frame.dispose();
                            new DetailBlockNasabahPage(nasabah); // Kirim Nasabah yang dipilih
                            break;
                        }
                    }
                }
            }
        });

        // Adding hover effect for Back button
        Component.addHoverEffect(blockNasabahButton, new Color(255, 69, 58), new Color(255, 82, 82));
        panel.add(blockNasabahButton);

        // Back Button
        JButton backButton = new JButton("Back to Menu");
        Component.styleRoundedButton(backButton, new Color(255, 69, 58), Color.WHITE);
        backButton.setBounds(120, 400, 260, 50);
        backButton.addActionListener(e -> {
            frame.dispose();
            new MenuReportAdmin();
        });
        // Adding hover effect for Back button
        Component.addHoverEffect(backButton, new Color(255, 69, 58), new Color(255, 82, 82));
        panel.add(backButton);

        // Finalize frame
        frame.add(panel);
        frame.setVisible(true);
    }
}
