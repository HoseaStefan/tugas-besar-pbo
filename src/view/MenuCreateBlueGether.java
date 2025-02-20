package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;
import java.sql.Timestamp;
import java.text.NumberFormat;
import java.util.ArrayList;

import model.BlueGether;
import model.CurrentUser;
import model.TabunganType;
import model.User;
import model.Nasabah;
import controller.BlueGetherController;
import controller.CreateTabunganController;
import view.Component;

public class MenuCreateBlueGether {

    private JFrame frame;

    public MenuCreateBlueGether() {
        showMenuCreateBlueGether();
    }

    public void showMenuCreateBlueGether() {

        CurrentUser currentUser = CurrentUser.getInstance();
        User user = currentUser.getUser();
        Nasabah nasabah = currentUser.getNasabah();

        ArrayList<Nasabah> nasabahList = new ArrayList<>();

        Timestamp startDate = new Timestamp(System.currentTimeMillis());

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();

        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;

        final int FRAME_WIDTH = 500;
        final int FRAME_HEIGHT = 700;

        int start_x = screenWidth / 2 - (FRAME_WIDTH / 2);
        int start_y = screenHeight / 2 - (FRAME_HEIGHT / 2);

        frame = new JFrame("Create Blue Gether");
        frame.setUndecorated(true);
        frame.setBounds(start_x, start_y, FRAME_WIDTH, FRAME_HEIGHT);
        frame.setShape(new RoundRectangle2D.Double(0, 0, FRAME_WIDTH, FRAME_HEIGHT, 30, 30));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.getHSBColor(0.6f, 0.7f, 0.9f));
        panel.setBounds(0, 0, FRAME_WIDTH, FRAME_HEIGHT);

        JLabel title = new JLabel("Create New Blue Gether!");
        title.setBounds(110, 60, 500, 50);
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        title.setForeground(Color.WHITE);
        panel.add(title);

        // Nama Tabungan
        JLabel namaTabunganLabel = new JLabel("Input Nama Tabungan:");
        namaTabunganLabel.setBounds(120, 110, 300, 30);
        panel.add(namaTabunganLabel);

        JTextField namaTabunganField = new JTextField();
        namaTabunganField.setBounds(120, 140, 260, 30);
        panel.add(namaTabunganField);

        // Saldo Awal
        JLabel saldoAwalLabel = new JLabel("Input Saldo Awal:");
        saldoAwalLabel.setBounds(120, 180, 300, 30);
        panel.add(saldoAwalLabel);

        JFormattedTextField saldoAwalField = new JFormattedTextField(NumberFormat.getNumberInstance());
        saldoAwalField.setBounds(120, 210, 260, 30);
        saldoAwalField.setColumns(10);
        panel.add(saldoAwalField);

        // Target Saldo
        JLabel targetSaldoLabel = new JLabel("Input Target Saldo:");
        targetSaldoLabel.setBounds(120, 250, 300, 30);
        panel.add(targetSaldoLabel);

        JFormattedTextField targetSaldoField = new JFormattedTextField(NumberFormat.getNumberInstance());
        targetSaldoField.setBounds(120, 280, 260, 30);
        targetSaldoField.setColumns(10);
        panel.add(targetSaldoField);

        JLabel jangakaWaktuLabel = new JLabel("Input Jangka Waktu Saldo:");
        jangakaWaktuLabel.setBounds(120, 320, 300, 30);
        panel.add(jangakaWaktuLabel);

        JFormattedTextField jangkaWaktuField = new JFormattedTextField(NumberFormat.getNumberInstance());
        jangkaWaktuField.setBounds(120, 350, 260, 30);
        jangkaWaktuField.setColumns(10);
        panel.add(jangkaWaktuField);

        JButton tambahMemberButton = new JButton();
        Component.styleMemberButton(tambahMemberButton, Color.WHITE, Color.WHITE);
        tambahMemberButton.setBounds(190, 390, 120, 100); // Ukuran lebih besar dari sebelumnya
        panel.add(tambahMemberButton);

        tambahMemberButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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

                try {
                    int noRekening = Integer.parseInt(noRekeningStr.trim());

                    boolean alreadyExists = nasabahList.stream()
                            .anyMatch(n -> n.getNomorRekening() == noRekening);

                    if (alreadyExists) {
                        JOptionPane.showMessageDialog(frame,
                                "Nomor Rekening sudah ditambahkan sebelumnya.",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    if (noRekening == nasabah.getNomorRekening()) {
                        JOptionPane.showMessageDialog(frame,
                                "Nomor Rekening Anda sudah dalam daftar.",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    System.out.println("Mencari user_id untuk nomor rekening: " + noRekening);
                    String userId = BlueGetherController.getUserIdNasabah(noRekening);

                    if (userId == null) {
                        JOptionPane.showMessageDialog(frame,
                                "Nomor Rekening tidak ditemukan di sistem.",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    Nasabah newNasabah = new Nasabah(noRekening);
                    newNasabah.setUser_id(userId);

                    nasabahList.add(newNasabah);

                    JOptionPane.showMessageDialog(frame,
                            "Nomor Rekening berhasil ditambahkan ke daftar.",
                            "Success",
                            JOptionPane.INFORMATION_MESSAGE);

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame,
                            "Nomor Rekening harus berupa angka valid.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JButton createButton = new JButton("Create");
        Component.styleRoundedButton(createButton, new Color(0, 102, 204), Color.WHITE);
        createButton.setBounds(120, 500, 260, 50);
        Component.addHoverEffect(createButton, new Color(0, 102, 204), new Color(0, 123, 180));
        panel.add(createButton);

        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Ambil input dari user
                    String namaTabungan = namaTabunganField.getText().trim();
                    String saldoAwalStr = saldoAwalField.getText().replace(",", "").trim();
                    String targetSaldoStr = targetSaldoField.getText().replace(",", "").trim();
                    String jangkaWaktuStr = jangkaWaktuField.getText().replace(",", "").trim();

                    // Validasi input kosong
                    if (namaTabungan.isEmpty() || saldoAwalStr.isEmpty() || targetSaldoStr.isEmpty()) {
                        JOptionPane.showMessageDialog(frame,
                                "Please fill all fields with valid numeric values.",
                                "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Parsing nilai input
                    double saldoAwal = Double.parseDouble(saldoAwalStr);
                    double targetSaldo = Double.parseDouble(targetSaldoStr);
                    int jangkaWaktu = Integer.parseInt(jangkaWaktuStr);

                    // Validasi nilai harus positif
                    if (targetSaldo <= 0 || jangkaWaktu <= 0) {
                        JOptionPane.showMessageDialog(frame,
                                "Target Saldo dan Jangka Waktu harus lebih besar dari 0.",
                                "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Validasi Target Saldo > Saldo Awal
                    if (targetSaldo < saldoAwal) {
                        JOptionPane.showMessageDialog(frame,
                                "Target Saldo harus lebih besar dari Saldo Awal.",
                                "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    double tabunganHarian = BlueGether.hitungTabunganHarian(saldoAwal, targetSaldo, jangkaWaktu);
                    double saldoGether = 0;

                    BlueGether blueGether = new BlueGether("", nasabah.getUser_id(), namaTabungan,
                            TabunganType.BLUEGETHER, saldoAwal, startDate, saldoGether, jangkaWaktu, targetSaldo,
                            tabunganHarian, nasabahList);

                    CreateTabunganController controller = new CreateTabunganController();
                    boolean isCreated = controller.createBlueGether(blueGether);

                    String userId = nasabah.getUser_id();
                    double nominal = saldoAwal;

                    boolean success = BlueGetherController.pindahSaldoGether(userId, nominal, blueGether);
                    if (success) {

                        double newSaldoUser = nasabah.getSaldo() - nominal;
                        CurrentUser.getInstance().getNasabah().setSaldo(newSaldoUser);

                        blueGether.setSaldoGether(blueGether.getSaldoGether() + nominal);

                    } else {
                        JOptionPane.showMessageDialog(frame, "Saldo tidak mencukupi atau terjadi kesalahan.", "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }

                    if (isCreated) {
                        JOptionPane.showMessageDialog(frame, "Blue Gether Created Successfully!");
                        frame.dispose(); // Tutup form jika berhasil
                        new MenuBlueGether(); // Kembali ke menu utama
                    } else {
                        JOptionPane.showMessageDialog(frame,
                                "Failed to create Blue Saving. Please try again.",
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame,
                            "Please enter valid numeric values for Saldo Awal, Target Saldo, and Jangka Waktu.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Back Button
        JButton backButton = new JButton("Back to Menu Blue Gether");
        Component.styleRoundedButton(backButton, new Color(255, 69, 58), Color.WHITE);
        backButton.setBounds(120, 600, 260, 50);
        Component.addHoverEffect(backButton, new Color(255, 69, 58), new Color(255, 82, 82));
        backButton.addActionListener(e -> {
            frame.dispose();
            new MenuBlueGether();
        });
        panel.add(backButton);

        // Add panel to frame
        frame.add(panel);
        frame.setVisible(true);

    }

}