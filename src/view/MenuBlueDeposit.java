package view;

import controller.BlueDepositoController;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import javax.swing.*;
import model.CurrentUser;
import model.Nasabah;

public class MenuBlueDeposit {
    private static JFrame frame;

    public MenuBlueDeposit() {
        menuBlueDeposito();
    }

    public void menuBlueDeposito() {
        CurrentUser currentUser = CurrentUser.getInstance();
        Nasabah nasabah = currentUser.getNasabah();

        final int FRAME_WIDTH = 500;
        final int FRAME_HEIGHT = 700;

        frame = new JFrame("Blue Deposit");
        frame.setUndecorated(true);
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        frame.setLocationRelativeTo(null);
        frame.setShape(new RoundRectangle2D.Double(0, 0, FRAME_WIDTH, FRAME_HEIGHT, 30, 30));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.getHSBColor(0.6f, 0.7f, 0.9f));
        panel.setBounds(0, 0, FRAME_WIDTH, FRAME_HEIGHT);

        JLabel title = new JLabel("Blue Deposito");
        title.setBounds(0, 50, FRAME_WIDTH, 50);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(Color.WHITE);
        title.setHorizontalAlignment(JLabel.CENTER);
        panel.add(title);

        JLabel saldoLabel = new JLabel("Current saldo : Rp." + nasabah.getSaldo());
        saldoLabel.setBounds(0, 130, FRAME_WIDTH, 25);
        saldoLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        saldoLabel.setForeground(Color.WHITE);
        saldoLabel.setHorizontalAlignment(JLabel.CENTER);
        panel.add(saldoLabel);

        JButton btnTarik = createMenuButton("Withdraw Deposit", 250);
        JButton btnCreate = createMenuButton("Create New Deposit", 400);
        
        btnTarik.addActionListener(e -> {
            BlueDepositoController controller = new BlueDepositoController();
            if (!controller.hasBlueDeposito(nasabah.getUser_id())) {
                showWarning("You don't have an active Blue Deposit account.");
            } else {
                frame.dispose();
                new MenuTarikBlueDeposit();
            }
        });

        btnCreate.addActionListener(e -> {
            BlueDepositoController controller = new BlueDepositoController();
            if (controller.hasBlueDeposito(nasabah.getUser_id())) {
                showWarning("You already have an active Blue Deposit account.");
            } else {
                frame.dispose();
                new MenuCreateBlueDeposit();
            }
        });

        panel.add(btnTarik);
        panel.add(btnCreate);

        JButton backButton = new JButton("Back to Menu Tabungan");
        backButton.setBounds(125, 600, 250, 50);
        backButton.setFont(new Font("SansSerif", Font.BOLD, 18));
        backButton.setBackground(new Color(255, 69, 58));
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backButton.addActionListener(e -> {
            frame.dispose();
            new MenuTabungan();
        });
        panel.add(backButton);

        frame.add(panel);
        frame.setVisible(true);
    }

    private JButton createMenuButton(String title, int yPosition) {
        JButton button = new JButton(title);
        button.setBounds(150, yPosition, 200, 100);
        button.setFont(new Font("SansSerif", Font.BOLD, 18));
        button.setBackground(new Color(41, 128, 185));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    private void showWarning(String message) {
        JOptionPane.showMessageDialog(frame, message, "Warning", JOptionPane.WARNING_MESSAGE);
    }
}
