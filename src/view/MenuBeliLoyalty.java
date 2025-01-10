package view;

import controller.LoyaltyController;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.geom.RoundRectangle2D;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import model.CurrentUser;
import model.Loyalty;
import model.Nasabah;
import model.User;

public class MenuBeliLoyalty {
    private static JFrame frame;

    public MenuBeliLoyalty() {
        menuMembeliLoyalty();
    }

    static Timestamp expiredTimestamp;
    static LoyaltyController controller;
    static Loyalty loyalty;

    public void menuMembeliLoyalty() {

        LoyaltyController loyaltyController = new LoyaltyController();

        Font buttonFont = new Font("SansSerif", Font.BOLD, 18);

        CurrentUser currentUser = CurrentUser.getInstance();
        User user = currentUser.getUser();
        Nasabah nasabah = currentUser.getNasabah();

        Timestamp startDate = new Timestamp(System.currentTimeMillis());

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();

        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;

        final int FRAME_WIDTH = 500;
        final int FRAME_HEIGHT = 700;

        int start_x = screenWidth / 2 - (FRAME_WIDTH / 2);
        int start_y = screenHeight / 2 - (FRAME_HEIGHT / 2);

        frame = new JFrame("Loyalty");
        frame.setUndecorated(true);
        frame.setBounds(start_x, start_y, FRAME_WIDTH, FRAME_HEIGHT);
        frame.setShape(new RoundRectangle2D.Double(0, 0, FRAME_WIDTH, FRAME_HEIGHT, 30, 30));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.getHSBColor(0.6f, 0.7f, 0.9f));
        panel.setBounds(0, 0, FRAME_WIDTH, FRAME_HEIGHT);

        JLabel saldoLabel = new JLabel("Current saldo : Rp." + nasabah.getSaldo());
        saldoLabel.setBounds(50, 130, 400, 25);
        saldoLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        saldoLabel.setForeground(Color.WHITE);
        panel.add(saldoLabel);

        if (loyaltyController.hasLoyaltyActive(nasabah.getUser_id()) == false) {
            JButton btnBuyLoyalty = new JButton("BUY NOW ONLY 99k !!");
            btnBuyLoyalty.setBounds(120, 300, 260, 50);
            Component.styleButton(btnBuyLoyalty, new Color(3, 123, 252), buttonFont);
            panel.add(btnBuyLoyalty);

            btnBuyLoyalty.addActionListener((actionEvent) -> {
                try {
                    controller = new LoyaltyController();
                    double loyaltyPrice = 99000; // Harga loyalty
                    double userSaldo = nasabah.getSaldo(); // Saldo pengguna saat ini

                    // Cek apakah saldo mencukupi
                    if (userSaldo < loyaltyPrice) {
                        JOptionPane.showMessageDialog(frame, "Insufficient saldo. Minimum required: Rp." + loyaltyPrice,
                                "Error", JOptionPane.ERROR_MESSAGE);
                        return; // Berhenti jika saldo kurang
                    }

                    if (!controller.hasLoyaltyActive(nasabah.getUser_id())) {
                        expiredTimestamp = controller.calculateEndDate(startDate);
                        loyalty = new Loyalty("", nasabah.getUser_id(), loyaltyPrice, 100, 100, 100, true,
                                expiredTimestamp);

                        Boolean isCreated = controller.buyLoyaltyByUserId(loyalty);
                        if (isCreated) {
                            loyaltyController.kurangSaldoUser(nasabah.getUser_id());
                            double updatedSaldo = userSaldo - loyaltyPrice;
                            nasabah.setSaldo(updatedSaldo);

                            saldoLabel.setText("Current saldo : Rp." + updatedSaldo);

                            JOptionPane.showMessageDialog(frame, "Success! Loyalty purchased successfully.", "Info",
                                    JOptionPane.INFORMATION_MESSAGE);
                        }
                        frame.dispose();
                        new MenuNasabah();
                    } else {
                        JOptionPane.showMessageDialog(frame, "Failed to purchase loyalty. Please try again.", "Error",
                                JOptionPane.WARNING_MESSAGE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "An unexpected error occurred: " + e.getMessage(), "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            });
        } else {
            JButton btnn = new JButton("Your Loyalty is Active !!");
            btnn.setBounds(120, 300, 260, 50);
            Component.styleButton(btnn, new Color(3, 123, 252), buttonFont);
            btnn.addActionListener((actionEvent) -> {
                JOptionPane.showMessageDialog(frame, "Loyalty is already exist", "info",
                        JOptionPane.INFORMATION_MESSAGE);
            });
            panel.add(btnn);

            Timestamp endDate = controller.calculateEndDate(startDate);
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy"); // Format tanggal dan waktu
            String formattedDate = dateFormat.format(endDate);

            // JLabel untuk menampilkan tanggal kadaluarsa
            JLabel showExpDate = new JLabel("Expired Date: " + formattedDate);
            showExpDate.setBounds(50, 150, 400, 25);
            showExpDate.setFont(new Font("SansSerif", Font.PLAIN, 16));
            showExpDate.setForeground(Color.WHITE);
            panel.add(showExpDate);
        }

        JButton Close = new JButton("Back to Home Page");
        Close.setBounds(130, 600, 260, 50);
        Component.styleButton(Close, new Color(255, 69, 58), buttonFont);
        Close.addActionListener(e -> {
            frame.dispose();
            new MenuNasabah();
        });
        panel.add(Close);

        frame.add(panel);
        frame.setVisible(true);

    }
}
