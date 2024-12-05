package view;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import javax.swing.*;

public class MenuTarikBlueSaving {

    private static JFrame frame;

    public MenuTarikBlueSaving() {
        menuTarikBlueSaving();
    }

    public void menuTarikBlueSaving() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();

        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;

        final int FRAME_WIDTH = 500;
        final int FRAME_HEIGHT = 700;

        int start_x = screenWidth / 2 - (FRAME_WIDTH / 2);
        int start_y = screenHeight / 2 - (FRAME_HEIGHT / 2);

        Font buttonFont = new Font("SansSerif", Font.BOLD, 18);

        frame = new JFrame("Blue Deposit");
        frame.setUndecorated(true);
        frame.setBounds(start_x, start_y, FRAME_WIDTH, FRAME_HEIGHT);
        frame.setShape(new RoundRectangle2D.Double(0, 0, FRAME_WIDTH, FRAME_HEIGHT, 30, 30));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.getHSBColor(0.6f, 0.7f, 0.9f));
        panel.setBounds(0, 0, FRAME_WIDTH, FRAME_HEIGHT);

        JLabel title = new JLabel("Blue Deposito - Tarik");
        title.setBounds(130, 100, 500, 50);
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        title.setForeground(Color.WHITE);
        panel.add(title);

        // Label untuk input nominal
        JLabel lblTarik = new JLabel("Masukkan nominal untuk ditarik:");
        lblTarik.setBounds(130, 180, 300, 30);
        lblTarik.setForeground(Color.WHITE);
        panel.add(lblTarik);

        // Text field untuk nominal
        JTextField txtTarik = new JTextField();
        txtTarik.setBounds(130, 220, 260, 30);
        panel.add(txtTarik);

        // Tombol submit
        JButton btnSubmit = new JButton("Submit");
        btnSubmit.setFont(buttonFont);
        btnSubmit.setBounds(130, 260, 260, 30);
        panel.add(btnSubmit);

        

        // Action listener untuk tombol submit
        btnSubmit.addActionListener(e -> {
            String input = txtTarik.getText();
            // try {
            //     double nominal = Double.parseDouble(input);

            //     // Masukkan ke database
            //     if (insertToDatabase(nominal)) {
            //         lblOutput.setText("Nominal berhasil disimpan: Rp " + nominal);
            //         lblOutput.setForeground(Color.GREEN);
            //     } else {
            //         lblOutput.setText("Gagal menyimpan ke database!");
            //         lblOutput.setForeground(Color.RED);
            //     }
            // } catch (NumberFormatException ex) {
            //     lblOutput.setText("Input tidak valid. Masukkan angka!");
            //     lblOutput.setForeground(Color.RED);
            // }
        });

        frame.add(panel);
        frame.setVisible(true);
    }

 

    public static void main(String[] args) {
        new MenuTarikBlueSaving();
    }
}
