package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MenuCreateBlueDeposit {

    private static JFrame frame;

    public MenuCreateBlueDeposit() {
        menuCreateBlueDeposit();
    }

    public void menuCreateBlueDeposit() {
        // Mendapatkan ukuran layar
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();

        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;

        final int FRAME_WIDTH = 500;
        final int FRAME_HEIGHT = 700;

        int start_x = screenWidth / 2 - (FRAME_WIDTH / 2);
        int start_y = screenHeight / 2 - (FRAME_HEIGHT / 2);

        Font buttonFont = new Font("SansSerif", Font.BOLD, 18);

        // Mengatur frame
        frame = new JFrame("Blue Deposit");
        frame.setUndecorated(true);
        frame.setBounds(start_x, start_y, FRAME_WIDTH, FRAME_HEIGHT);
        frame.setShape(new RoundRectangle2D.Double(0, 0, FRAME_WIDTH, FRAME_HEIGHT, 30, 30));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.getHSBColor(0.6f, 0.7f, 0.9f));
        panel.setBounds(0, 0, FRAME_WIDTH, FRAME_HEIGHT);

        JLabel title = new JLabel("Blue Deposito - Create");
        title.setBounds(130, 100, 500, 50);
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        title.setForeground(Color.WHITE);
        panel.add(title);

        JLabel lblCreate = new JLabel("Masukkan nominal untuk disimpan:");
        lblCreate.setBounds(130, 180, 300, 30);
        lblCreate.setFont(new Font("SansSerif", Font.PLAIN, 14));
        panel.add(lblCreate);

        JTextField txtCreate = new JTextField();
        txtCreate.setBounds(130, 220, 260, 30);
        panel.add(txtCreate);

        // Tombol Submit
        JButton btnSubmit = new JButton("Submit");
        btnSubmit.setBounds(50, 150, 100, 30);
        btnSubmit.setFont(buttonFont);
        panel.add(btnSubmit);

        // Label output
        JLabel lblOutput = new JLabel();
        lblOutput.setBounds(50, 200, 400, 30);
        lblOutput.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lblOutput.setForeground(Color.BLACK);
        panel.add(lblOutput);

        // Menambahkan ActionListener untuk tombol Submit
        btnSubmit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = txtCreate.getText();
                try {
                    double nominal = Double.parseDouble(input);
                    if (nominal > 0) {
                        lblOutput.setText("Nominal yang disimpan: Rp " + nominal);
                    } else {
                        lblOutput.setText("Nominal harus lebih dari 0!");
                    }
                } catch (NumberFormatException ex) {
                    lblOutput.setText("Input tidak valid!");
                }
            }
        });

        // Menambahkan panel ke frame dan menampilkan frame
        frame.add(panel);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new MenuCreateBlueDeposit();
    }
}
