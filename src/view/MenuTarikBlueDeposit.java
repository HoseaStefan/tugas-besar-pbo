package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;
import javax.swing.*;

public class MenuTarikBlueDeposit {

    private static JFrame frame;

    public MenuTarikBlueDeposit() {
        menuTarikBlueSaving();
    }

    public void menuTarikBlueSaving() {
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
        JButton btnClose = new JButton("EXIT");
        btnClose.setFont(buttonFont);
        btnClose.setBackground(new Color(255, 69, 58));
        btnClose.setForeground(Color.WHITE);
        btnClose.setBounds(50, 600, 400, 40);
        panel.add(btnClose);

        // ActionListener tombol Submit
        btnSubmit.addActionListener(e -> {
            String input = txtTarik.getText();
            try {
                double nominal = Double.parseDouble(input);
                if (nominal > 0) {
                    lblOutput.setText("Nominal berhasil ditarik: Rp " + nominal);
                    JOptionPane.showMessageDialog(frame, "Penarikan berhasil dilakukan!", "Sukses",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(frame, "Nominal harus lebih dari 0!", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Input tidak valid! Masukkan angka.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        btnBack.addActionListener(new ActionListener() {
            @Override
            public  void actionPerformed(ActionEvent e){
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

    public static void main(String[] args) {
        new MenuTarikBlueDeposit();
    }
}
