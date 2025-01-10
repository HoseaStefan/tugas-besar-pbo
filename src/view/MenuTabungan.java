package view;

import controller.BlueDepositoController;
import controller.BlueGetherController;
import controller.BlueSavingController;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import javax.swing.*;
import model.CurrentUser;
import model.Nasabah;

public class MenuTabungan {

    private JFrame frame;

    public MenuTabungan() {
        showMenuTabungan();
    }

    public void showMenuTabungan() {
        // Get current user
        CurrentUser currentUser = CurrentUser.getInstance();
        Nasabah nasabah = currentUser.getNasabah();

        // Screen setup
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();

        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;

        final int FRAME_WIDTH = 500;
        final int FRAME_HEIGHT = 700;

        int start_x = screenWidth / 2 - (FRAME_WIDTH / 2);
        int start_y = screenHeight / 2 - (FRAME_HEIGHT / 2);

        Font buttonFont = new Font("SansSerif", Font.BOLD, 18);

        // Frame setup
        frame = new JFrame("Home Tabungan");
        frame.setUndecorated(true);
        frame.setBounds(start_x, start_y, FRAME_WIDTH, FRAME_HEIGHT);
        frame.setShape(new RoundRectangle2D.Double(0, 0, FRAME_WIDTH, FRAME_HEIGHT, 30, 30));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.getHSBColor(0.6f, 0.7f, 0.9f));
        panel.setBounds(0, 0, FRAME_WIDTH, FRAME_HEIGHT);

        // Title Label
        JLabel welcomeTitle = new JLabel("Menu Tabungan", SwingConstants.CENTER);
        welcomeTitle.setBounds(0, 40, FRAME_WIDTH, 40);
        welcomeTitle.setFont(new Font("SansSerif", Font.BOLD, 28));
        welcomeTitle.setForeground(Color.WHITE);
        panel.add(welcomeTitle);

        double totalSimpanan = nasabah.getSaldo()
                + BlueGetherController.getTotalDanaGetherByUserId(nasabah.getUser_id())
                + BlueSavingController.getTotalDanaByUserId(nasabah.getUser_id())
                + BlueDepositoController.getTotalDanaByUserId(nasabah.getUser_id());
        // Saldo Label
        JLabel saldoLabel = new JLabel("Total Simpanan: Rp." + totalSimpanan, SwingConstants.CENTER);
        saldoLabel.setBounds(0, 100, FRAME_WIDTH, 30);
        saldoLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
        saldoLabel.setForeground(Color.WHITE);
        panel.add(saldoLabel);

        JButton BlueSavingButton = new JButton("Blue Saving");
        BlueSavingButton.setBounds(120, 280, 260, 50);
        Component.styleButton(BlueSavingButton, new Color(3, 123, 252), buttonFont);
        panel.add(BlueSavingButton);

        BlueSavingButton.addActionListener(e -> {
            frame.dispose();
            new MenuBlueSaving();
        });

        JButton BlueGetherButton = new JButton("Blue Gether");
        BlueGetherButton.setBounds(120, 350, 260, 50);
        Component.styleButton(BlueGetherButton, new Color(3, 123, 252), buttonFont);
        panel.add(BlueGetherButton);

        BlueGetherButton.addActionListener(e -> {
            frame.dispose();
            new MenuBlueGether();
        });

        JButton BlueDepositoButton = new JButton("Blue Deposito");
        BlueDepositoButton.setBounds(120, 420, 260, 50);
        Component.styleButton(BlueDepositoButton, new Color(3, 123, 252), buttonFont);
        panel.add(BlueDepositoButton);

        BlueDepositoButton.addActionListener(e -> {
            frame.dispose();
            new MenuBlueDeposit();
        });

        // Exit Button
        JButton exitButton = new JButton("Back to Menu Nasabah");
        exitButton.setBounds(120, 600, 260, 50);
        Component.styleButton(exitButton, new Color(255, 69, 58), buttonFont);
        panel.add(exitButton);

        exitButton.addActionListener(e -> {
            frame.dispose();
            new MenuNasabah();
        });

        // Add panel to frame
        frame.add(panel);
        frame.setVisible(true);
    }

}
