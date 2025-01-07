package view;

import controller.BlueDepositoController;
import controller.UserController;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;
import java.sql.Timestamp;
import java.util.List;
import javax.swing.*;
import model.BlueDeposito;
import model.CurrentUser;
import model.Nasabah;
import model.User;

public class MenuTarikBlueDeposit {

    private static JFrame frame;

    public MenuTarikBlueDeposit() {
        menuTarikBlueSaving();
    }

    public void menuTarikBlueSaving() {

        CurrentUser currentUser = CurrentUser.getInstance();
        User user = currentUser.getUser();
        Nasabah nasabah = currentUser.getNasabah();

        Timestamp now = new Timestamp(System.currentTimeMillis());

        // Mengambil ukuran layar
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();

        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;

        final int FRAME_WIDTH = 500;
        final int FRAME_HEIGHT = 700;

        int start_x = screenWidth / 2 - (FRAME_WIDTH / 2);
        int start_y = screenHeight / 2 - (FRAME_HEIGHT / 2);

        Font buttonFont = new Font("SansSerif", Font.BOLD, 18);

        // Frame utama
        frame = new JFrame("Blue Deposit");
        frame.setUndecorated(true);
        frame.setBounds(start_x, start_y, FRAME_WIDTH, FRAME_HEIGHT);
        frame.setShape(new RoundRectangle2D.Double(0, 0, FRAME_WIDTH, FRAME_HEIGHT, 30, 30));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.getHSBColor(0.6f, 0.7f, 0.9f));
        panel.setBounds(0, 0, FRAME_WIDTH, FRAME_HEIGHT);

        // Judul
        JLabel title = new JLabel("Blue Deposito - Tarik");
        title.setBounds(100, 50, 300, 50);
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        title.setForeground(Color.WHITE);
        panel.add(title);

        // Label untuk input nominal
        JLabel lblTarik = new JLabel("Masukkan nominal untuk ditarik:");
        lblTarik.setBounds(50, 150, 400, 30);
        lblTarik.setFont(new Font("SansSerif", Font.PLAIN, 16));
        lblTarik.setForeground(Color.WHITE);
        panel.add(lblTarik);

        // TextField untuk nominal
        JTextField txtTarik = new JTextField();
        txtTarik.setBounds(50, 190, 400, 30);
        txtTarik.setFont(new Font("SansSerif", Font.PLAIN, 14));
        panel.add(txtTarik);

        // Tombol Submit
        JButton btnSubmit = new JButton("Submit");
        btnSubmit.setBounds(50, 240, 180, 40);
        btnSubmit.setFont(buttonFont);
        btnSubmit.setBackground(Color.WHITE);
        btnSubmit.setForeground(Color.BLUE);
        panel.add(btnSubmit);

        // Tombol Back
        JButton btnBack = new JButton("Back");
        btnBack.setBounds(270, 240, 180, 40);
        btnBack.setFont(buttonFont);
        btnBack.setBackground(Color.RED);
        btnBack.setForeground(Color.WHITE);
        panel.add(btnBack);

        // Label hasil output
        JLabel lblOutput = new JLabel();
        lblOutput.setBounds(50, 300, 400, 30);
        lblOutput.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lblOutput.setForeground(Color.BLACK);
        panel.add(lblOutput);

        // Tombol Close
        JButton btnClose = new JButton("Back to Menu Tabungan");
        btnClose.setFont(buttonFont);
        btnClose.setBackground(new Color(255, 69, 58));
        btnClose.setForeground(Color.WHITE);
        btnClose.setBounds(50, 600, 400, 40);
        panel.add(btnClose);

        btnClose.addActionListener(e -> {
            frame.dispose();
            new MenuBlueDeposit();
        });

        // ActionListener tombol Submit
        btnSubmit.addActionListener(e -> {
            String input = txtTarik.getText();

            try {
                // Ambil data deposito berdasarkan user_id
                List<BlueDeposito> blueDepositos = BlueDepositoController.getDepositsByUserId(nasabah.getUser_id());
                double nominal = Double.parseDouble(input);

                // Validasi jika data deposito tidak ditemukan
                if (!BlueDepositoController.hasBlueDeposito(nasabah.getUser_id())) {
                    JOptionPane.showMessageDialog(frame, "Data deposito tidak ditemukan!", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Pilih deposito pertama dari daftar
                BlueDeposito blueDeposito = blueDepositos.get(0);
                double saldoAwal = blueDeposito.getSaldoAwal();
                double saldoAkhir = blueDeposito.getSaldoAkhir();
                Timestamp endDate = blueDeposito.getEndDate();

                // Validasi nominal yang diinput
                if (nominal > saldoAwal) {
                    // Jika input lebih besar dari saldo awal
                    JOptionPane.showMessageDialog(frame, "Nominal tidak valid! Saldo tidak mencukupi.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    frame.dispose();
                    new MenuBlueDeposit();

                } else if (nominal < saldoAwal) {
                    // Jika input lebih kecil dari saldo awal
                    JOptionPane.showMessageDialog(frame, "Penarikan sebesar Rp " + nominal + " berhasil dilakukan!",
                            "Sukses",
                            JOptionPane.INFORMATION_MESSAGE);

                    double newSaldoAwal = saldoAwal - nominal;

                    // Update saldo di BlueDeposito di database
                    boolean updated = BlueDepositoController.updateBlueDepositoSaldo(nasabah.getUser_id(),
                            newSaldoAwal);

                    // Tambahkan nominal ke saldo nasabah
                    if (!updated) {
                        JOptionPane.showMessageDialog(frame, "Gagal memperbarui saldo nasabah!", "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }

                    frame.dispose();
                    new MenuBlueDeposit();


                } else if (nominal == saldoAwal) {
                    // Jika input sama dengan saldo awal
                    if (now.after(endDate) || now.equals(endDate)) {
                        // Jika waktu saat ini lebih besar atau sama dengan end_date
                        JOptionPane.showMessageDialog(frame,
                                "Saldo akhir sebesar Rp " + saldoAkhir + " dikembalikan karena deposito telah selesai.",
                                "Sukses", JOptionPane.INFORMATION_MESSAGE);

                        // Tambahkan saldo akhir ke saldo nasabah
                        boolean updated = UserController.updateUserSaldo(nasabah.getUser_id(), saldoAkhir);
                        if (!updated) {
                            JOptionPane.showMessageDialog(frame, "Gagal memperbarui saldo nasabah!", "Error",
                                    JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        BlueDepositoController.deleteBlueDeposito(nasabah.getUser_id());
                        frame.dispose();
                        new MenuBlueDeposit();

                        
                    } else {
                        // Jika waktu saat ini belum mencapai end_date
                        JOptionPane.showMessageDialog(frame,
                                "Saldo awal sebesar Rp " + saldoAwal + " dikembalikan.", "Sukses",
                                JOptionPane.INFORMATION_MESSAGE);

                        // Tambahkan saldo awal ke saldo nasabah
                        boolean updated = UserController.updateUserSaldo(nasabah.getUser_id(), saldoAwal);
                        if (!updated) {
                            JOptionPane.showMessageDialog(frame, "Gagal memperbarui saldo nasabah!", "Error",
                                    JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        BlueDepositoController.deleteBlueDeposito(nasabah.getUser_id());
                        frame.dispose();
                        new MenuBlueDeposit();
    
                    }
                } else {
                    // Jika input tidak valid
                    JOptionPane.showMessageDialog(frame, "Input tidak valid! Masukkan nominal yang sesuai.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Terjadi kesalahan: " + ex.getMessage(), "Error",
                        JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });

        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new MenuBlueDeposit();
            }

        });

        // ActionListener tombol Close
        btnClose.addActionListener(e -> frame.dispose());

        // Tambahkan panel ke frame
        frame.add(panel);
        frame.setVisible(true);
    }
}
