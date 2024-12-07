package view;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;

import controller.TransaksiController;
import model.CurrentUser;
import model.Nasabah;
import model.TransaksiType;

public class MenuBonTransaksi {

    private JFrame frame;

    public MenuBonTransaksi(TransaksiType transaksiType, Boolean promo, Double amount, Double biayaAdmin) {
        showMenuBonTransaksi(transaksiType, promo, amount, biayaAdmin);
    }

    public void showMenuBonTransaksi(TransaksiType transaksiType, Boolean promo, Double amount, Double biayaAdmin) {
        CurrentUser currentUser = CurrentUser.getInstance();
        Nasabah nasabah = currentUser.getNasabah();

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();

        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;

        final int FRAME_WIDTH = 500;
        final int FRAME_HEIGHT = 700;

        int start_x = screenWidth / 2 - (FRAME_WIDTH / 2);
        int start_y = screenHeight / 2 - (FRAME_HEIGHT / 2);

        Font buttonFont = new Font("SansSerif", Font.BOLD, 18);

        frame = new JFrame("Bon Transaksi");
        frame.setUndecorated(true);
        frame.setBounds(start_x, start_y, FRAME_WIDTH, FRAME_HEIGHT);
        frame.setShape(new RoundRectangle2D.Double(0, 0, FRAME_WIDTH, FRAME_HEIGHT, 30, 30));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.getHSBColor(0.6f, 0.7f, 0.9f));
        panel.setBounds(0, 0, FRAME_WIDTH, FRAME_HEIGHT);

        JLabel title = new JLabel("TIPE TRANSAKSI : " + transaksiType);
        title.setBounds(50, 50, 400, 30);
        title.setFont(new Font("SansSerif", Font.BOLD, 20));
        title.setForeground(Color.WHITE);
        panel.add(title);

        JLabel saldoLabel = new JLabel("Amount "+ transaksiType + " : " + amount);
        saldoLabel.setBounds(50, 100, 400, 30);
        saldoLabel.setForeground(Color.WHITE);
        panel.add(saldoLabel);

        JLabel adminLabel = new JLabel("Potongan Biaya Admin (-): " + biayaAdmin);
        adminLabel.setBounds(50, 150, 400, 30);
        adminLabel.setForeground(Color.WHITE);
        panel.add(adminLabel);

        double total = amount - biayaAdmin;
        if (promo) {
            JLabel promoLabel = new JLabel("Promo Terpakai (+): " + biayaAdmin);
            promoLabel.setBounds(50, 200, 400, 30);
            promoLabel.setForeground(Color.WHITE);
            panel.add(promoLabel);
            total += biayaAdmin; // Promo mengurangi biaya admin
        }

        JLabel totalLabel = new JLabel();
        if (transaksiType == TransaksiType.SETOR) {
            totalLabel.setText("Total saldo yang ingin ditambahkan : " + total);
        } else {
            totalLabel.setText("Total saldo yang akan terpotong : " + total);
        }
        totalLabel.setBounds(50, 250, 400, 30);
        totalLabel.setForeground(Color.WHITE);
        panel.add(totalLabel);

        JButton confirmButton = new JButton("KONFIRMASI");
        confirmButton.setBounds(120, 300, 260, 50);
        Component.styleButton(confirmButton, new Color(3, 123, 252), buttonFont);
        confirmButton.addActionListener(e -> {
            try {
                double totalcalculated = amount - biayaAdmin;
                if (promo) {
                    totalcalculated += biayaAdmin;
                }
                boolean success = TransaksiController.createTransaksi(
                        transaksiType,
                        amount,
                        promo ? "VALID_PROMO" : "",
                        nasabah,
                        null,
                        biayaAdmin,
                        null);

                if (success) {
                    double updatedSaldo = transaksiType == TransaksiType.SETOR
                            ? nasabah.getSaldo() + totalcalculated
                            : nasabah.getSaldo() - totalcalculated;

                    nasabah.setSaldo(updatedSaldo);
                    JOptionPane.showMessageDialog(frame, "Transaksi berhasil! Saldo baru: Rp. " + updatedSaldo,
                            "Success", JOptionPane.INFORMATION_MESSAGE);
                    frame.dispose();
                    new MenuNasabah();
                } else {
                    JOptionPane.showMessageDialog(frame, "Transaksi gagal, coba lagi.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Terjadi kesalahan.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        panel.add(confirmButton);

        JButton exitButton = new JButton("Cancel");
        exitButton.setBounds(120, 400, 260, 50);
        Component.styleButton(exitButton, new Color(255, 69, 58), buttonFont);
        exitButton.addActionListener(e -> {
            frame.dispose();
            new MenuNasabah();
        });
        panel.add(exitButton);

        frame.add(panel);
        frame.setVisible(true);
    }
}
