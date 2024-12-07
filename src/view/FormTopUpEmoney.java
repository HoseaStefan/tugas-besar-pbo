package view;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import controller.TransaksiController;
import model.CurrentUser;
import model.Nasabah;
import model.TransaksiType;
import model.TopUpType; // Assuming there's a TopUpType enum

public class FormTopUpEmoney {

    private JFrame frame;

    public FormTopUpEmoney() {
        showFormTopUpEmoney();
    }

    public void showFormTopUpEmoney() {
        CurrentUser currentUser = CurrentUser.getInstance();
        Nasabah nasabah = currentUser.getNasabah();

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();

        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;

        final int FRAME_WIDTH = 500;
        final int FRAME_HEIGHT = 750; // Adjusted height for the form

        int start_x = screenWidth / 2 - (FRAME_WIDTH / 2);
        int start_y = screenHeight / 2 - (FRAME_HEIGHT / 2);

        Font buttonFont = new Font("SansSerif", Font.BOLD, 18);

        frame = new JFrame("Top-Up Saldo");
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

        JLabel saldoLabel = new JLabel("Input Jumlah Saldo Yang Ingin Di Top-Up : ");
        saldoLabel.setBounds(120, 200, 500, 50);
        saldoLabel.setForeground(Color.WHITE);
        panel.add(saldoLabel);

        JTextField inputSaldo = new JTextField(16);
        inputSaldo.setHorizontalAlignment(JTextField.CENTER);
        inputSaldo.setBorder(BorderFactory.createEmptyBorder());
        inputSaldo.setBounds(120, 240, 260, 50);
        panel.add(inputSaldo);

        JLabel eMoneyLabel = new JLabel("Nomor E-Money Tujuan : ");
        eMoneyLabel.setBounds(120, 280, 500, 50);
        eMoneyLabel.setForeground(Color.WHITE);
        panel.add(eMoneyLabel);

        JTextField inputEMoney = new JTextField(16);
        inputEMoney.setHorizontalAlignment(JTextField.CENTER);
        inputEMoney.setBorder(BorderFactory.createEmptyBorder());
        inputEMoney.setBounds(120, 320, 260, 50);
        panel.add(inputEMoney);

        JLabel topUpTypeLabel = new JLabel("Pilih Jenis E-Money : ");
        topUpTypeLabel.setBounds(120, 360, 500, 50);
        topUpTypeLabel.setForeground(Color.WHITE);
        panel.add(topUpTypeLabel);

        JComboBox<String> eMoneyComboBox = new JComboBox<>(new String[] {"DANA", "OVO", "GOPAY"});
        eMoneyComboBox.setBounds(120, 400, 260, 50);
        eMoneyComboBox.setBackground(Color.WHITE);
        panel.add(eMoneyComboBox);

        JLabel promoLabel = new JLabel("Promo Code (optional) : ");
        promoLabel.setBounds(120, 440, 500, 50);
        promoLabel.setForeground(Color.WHITE);
        panel.add(promoLabel);

        JTextField inputPromo = new JTextField(16);
        inputPromo.setHorizontalAlignment(JTextField.CENTER);
        inputPromo.setBorder(BorderFactory.createEmptyBorder());
        inputPromo.setBounds(120, 480, 260, 50);
        panel.add(inputPromo);

        JButton topUpButton = new JButton("TOP UP!");
        topUpButton.setBounds(120, 540, 260, 50);
        Component.styleButton(topUpButton, new Color(3, 123, 252), buttonFont);
        topUpButton.addActionListener(e -> {
            try {
                String promoCode = inputPromo.getText();
                String saldoInput = inputSaldo.getText();
                String eMoneyInput = inputEMoney.getText();
                double amount = Double.parseDouble(saldoInput);

                if (amount < 0) {
                    JOptionPane.showMessageDialog(frame, "Jumlah saldo tidak boleh negatif.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                

                String selectedTopUpType = (String) eMoneyComboBox.getSelectedItem();
                TopUpType topUpType = TopUpType.valueOf(selectedTopUpType);

                if (TransaksiController.verifyNomorRekeningTujuan(Integer.parseInt(eMoneyInput))) { 
                    JOptionPane.showMessageDialog(frame, "Nomor e-money tujuan tidak valid.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                boolean promoValid = TransaksiController.verifyKodePromo(promoCode, TransaksiType.TOPUP);
                if (amount > nasabah.getSaldo()) {
                    JOptionPane.showMessageDialog(frame, "Jumlah saldo tidak mencukupi.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (!promoValid) {
                    if (amount + 2500 > nasabah.getSaldo()) {
                        JOptionPane.showMessageDialog(frame, "Jumlah saldo tidak mencukupi.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
                if (promoValid || promoCode.isEmpty()) {
                    frame.dispose();
                    new MenuBonTransaksi(TransaksiType.TOPUP, promoValid, amount, Integer.parseInt(eMoneyInput), 2500.0, topUpType);
                } else {
                    JOptionPane.showMessageDialog(frame, "Kode promo tidak valid.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Input saldo harus berupa angka.", "Error", JOptionPane.ERROR_MESSAGE);
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
