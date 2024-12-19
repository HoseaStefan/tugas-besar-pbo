package view;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controller.TransaksiController;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;

import model.CurrentUser;
import model.Nasabah;
import model.Transaksi;
import model.TransaksiType;

public class FormSetorSaldo {

    private JFrame frame;

    public FormSetorSaldo() {
        showFormSetorSaldo();
    }

    public void showFormSetorSaldo() {
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

        frame = new JFrame("Setor Saldo");
        frame.setUndecorated(true);
        frame.setBounds(start_x, start_y, FRAME_WIDTH, FRAME_HEIGHT);
        frame.setShape(new RoundRectangle2D.Double(0, 0, FRAME_WIDTH, FRAME_HEIGHT, 30, 30));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.getHSBColor(0.6f, 0.7f, 0.9f));
        panel.setBounds(0, 0, FRAME_WIDTH, FRAME_HEIGHT);

        JLabel title = new JLabel("SALDO ANDA : Rp." + nasabah.getSaldo());
        title.setBounds(50, 50, 400, 30);
        title.setFont(new Font("SansSerif", Font.BOLD, 20));
        title.setForeground(Color.WHITE);
        panel.add(title);

        JLabel saldoLabel = new JLabel("Input Jumlah Saldo Yang Ingin Di Setor : ");
        saldoLabel.setBounds(120, 200, 500, 50);
        saldoLabel.setForeground(Color.WHITE);
        panel.add(saldoLabel);

        JTextField inputSaldo = new JTextField(16);
        inputSaldo.setHorizontalAlignment(JTextField.CENTER);
        inputSaldo.setBorder(BorderFactory.createEmptyBorder());
        inputSaldo.setBounds(120, 240, 260, 50);
        panel.add(inputSaldo);

        JLabel promoLabel = new JLabel("Promo Code (optional) : ");
        promoLabel.setBounds(120, 280, 500, 50);
        promoLabel.setForeground(Color.WHITE);
        panel.add(promoLabel);

        JTextField inputPromo = new JTextField(16);
        inputPromo.setHorizontalAlignment(JTextField.CENTER);
        inputPromo.setBorder(BorderFactory.createEmptyBorder());
        inputPromo.setBounds(120, 320, 260, 50);
        panel.add(inputPromo);

        JButton topUpButton = new JButton("SETOR!");
        topUpButton.setBounds(120, 380, 260, 50);
        Component.styleButton(topUpButton, new Color(3, 123, 252), buttonFont);
        topUpButton.addActionListener(e -> {
            try {
                String promoCode = inputPromo.getText();
                String saldoInput = inputSaldo.getText();
                double amount = Double.parseDouble(saldoInput);

                if (amount < 0) {
                    JOptionPane.showMessageDialog(frame, "Jumlah saldo tidak boleh negatif.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                boolean promoValid = TransaksiController.verifyKodePromo(promoCode, TransaksiType.SETOR);
                if (promoValid || promoCode.isEmpty()) {
                    frame.dispose();
                    System.out.println(promoValid);
                    new MenuBonTransaksi(TransaksiType.SETOR, promoValid, amount, nasabah.getNomorRekening(), 5000.0, null);
                } else {
                    JOptionPane.showMessageDialog(frame, "Kode promo tidak valid.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Input saldo harus berupa angka.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
        panel.add(topUpButton);

        JButton exitButton = new JButton("Back To Homepage");
        exitButton.setBounds(120, 600, 260, 50);
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
