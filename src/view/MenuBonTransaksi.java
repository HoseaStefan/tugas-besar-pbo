package view;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;

import controller.TransaksiController;
import model.CurrentUser;
import model.Nasabah;
import model.TopUpType;
import model.TransaksiType;

public class MenuBonTransaksi {

    private JFrame frame;

    public MenuBonTransaksi(TransaksiType transaksiType, Boolean promo, Double amount, int norekTujuan,
            Double biayaAdmin, TopUpType topUpType) {
        showMenuBonTransaksi(transaksiType, promo, amount, norekTujuan, biayaAdmin, topUpType);
    }

    public void showMenuBonTransaksi(TransaksiType transaksiType, Boolean promo, Double amount, int norekTujuan,
            Double biayaAdmin, TopUpType topUpType) {
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

        JLabel saldoLabel = new JLabel("Amount " + transaksiType + " : " + amount);
        saldoLabel.setBounds(50, 100, 400, 30);
        saldoLabel.setForeground(Color.WHITE);
        panel.add(saldoLabel);

        JLabel norekLabel = new JLabel("Nomor Rekening Tujuan: " + norekTujuan);
        if (transaksiType == TransaksiType.TOPUP) {
            norekLabel.setText("Nomor Emoney " + topUpType + " : " + norekTujuan);
        } else {
            norekLabel.setText("Nomor Rekening Tujuan: " + norekTujuan);
        }
        norekLabel.setBounds(50, 140, 400, 30);
        norekLabel.setForeground(Color.WHITE);
        panel.add(norekLabel);

        JLabel adminLabel = new JLabel("Potongan Biaya Admin (-): " + biayaAdmin);
        adminLabel.setBounds(50, 180, 400, 30);
        adminLabel.setForeground(Color.WHITE);
        panel.add(adminLabel);

        double total = 0;
        if (transaksiType == TransaksiType.SETOR) {
            total += amount - biayaAdmin;
        } else {
            total += amount + biayaAdmin;
        }

        if (promo) {
            JLabel promoLabel = new JLabel("Promo Terpakai (+): " + biayaAdmin);
            promoLabel.setBounds(50, 230, 400, 30);
            promoLabel.setForeground(Color.WHITE);
            panel.add(promoLabel);
            if (transaksiType == TransaksiType.SETOR) {
                total += biayaAdmin;
            } else {
                total -= biayaAdmin;
            }
        }

        JLabel totalLabel = new JLabel();
        if (transaksiType == TransaksiType.SETOR) {
            totalLabel.setText("Total saldo yang ingin ditambahkan : " + total);
        } else {
            totalLabel.setText("Total saldo yang akan terpotong : " + total);
        }
        totalLabel.setBounds(50, 280, 400, 30);
        totalLabel.setForeground(Color.WHITE);
        panel.add(totalLabel);

        JButton confirmButton = new JButton("KONFIRMASI");
        confirmButton.setBounds(120, 330, 260, 50);
        Component.styleButton(confirmButton, new Color(3, 123, 252), buttonFont);
        confirmButton.addActionListener(e -> {
            try {
                double totalcalculated = 0;
                System.out.println("nasabah");

                FormInputPIN formInputPIN = new FormInputPIN();
                boolean isVerified = formInputPIN.showInputPIN(nasabah);
                if (isVerified) {
                    if (transaksiType == TransaksiType.SETOR) {
                        totalcalculated += amount - biayaAdmin;
                        if (promo) {
                            totalcalculated += biayaAdmin;
                        }
                    } else {
                        totalcalculated += amount + biayaAdmin;
                        if (promo) {
                            totalcalculated -= biayaAdmin;
                        }
                    }

                    boolean success = TransaksiController.createTransaksi(
                            transaksiType,
                            amount,
                            promo ? "VALID_PROMO" : "",
                            nasabah,
                            norekTujuan,
                            biayaAdmin,
                            topUpType);

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
                    return;
                } else {
                    frame.dispose();
                    new MenuNasabah();
                }

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Terjadi kesalahan.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        panel.add(confirmButton);

        JButton exitButton = new JButton(
                "Cancel");
        exitButton.setBounds(120, 420, 260, 50);
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
