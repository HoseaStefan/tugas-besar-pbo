package view;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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

        // Frame setup
        frame = new JFrame("Home Tabungan");
        frame.setUndecorated(true);
        frame.setBounds(start_x, start_y, FRAME_WIDTH, FRAME_HEIGHT);
        frame.setShape(new RoundRectangle2D.Double(0, 0, FRAME_WIDTH, FRAME_HEIGHT, 30, 30));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Main Panel with Gradient
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                Color color1 = new Color(108, 92, 231);
                Color color2 = new Color(255, 175, 204);
                GradientPaint gp = new GradientPaint(0, 0, color1, 0, getHeight(), color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        panel.setLayout(null);

        // Title Label
        JLabel welcomeTitle = new JLabel("Menu Tabungan", SwingConstants.CENTER);
        welcomeTitle.setBounds(0, 40, FRAME_WIDTH, 40);
        welcomeTitle.setFont(new Font("SansSerif", Font.BOLD, 28));
        welcomeTitle.setForeground(Color.WHITE);
        panel.add(welcomeTitle);

        // Saldo Label
        JLabel saldoLabel = new JLabel("Total Simpanan: Rp." + nasabah.getSaldo(), SwingConstants.CENTER);
        saldoLabel.setBounds(0, 100, FRAME_WIDTH, 30);
        saldoLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
        saldoLabel.setForeground(Color.WHITE);
        panel.add(saldoLabel);

        // Buttons
        addStyledButton(panel, "Blue Saving", 200, e -> {
            frame.dispose();
            new MenuBlueSaving();
        });

        addStyledButton(panel, "Blue Gether", 280, e -> {
            JOptionPane.showMessageDialog(frame, "Fitur Blue Gether belum tersedia.");
        });

        addStyledButton(panel, "Blue Deposit", 360, e -> {
            frame.dispose();
            new MenuBlueDeposit();
        });

        // Exit Button
        JButton exitButton = new JButton("Back to Menu Nasabah");
        styleRoundedButton(exitButton, new Color(255, 69, 58), Color.WHITE);
        exitButton.setBounds(100, 550, 300, 50);
        panel.add(exitButton);

        exitButton.addActionListener(e -> {
            frame.dispose();
            new MenuNasabah();
        });

        // Add panel to frame
        frame.add(panel);
        frame.setVisible(true);
    }

    private void addStyledButton(JPanel panel, String text, int yPosition,
            java.awt.event.ActionListener actionListener) {
        JButton button = new JButton(text);
        button.setBounds(100, yPosition, 300, 50);
        styleRoundedButton(button, new Color(255, 255, 255), new Color(108, 92, 231));
        button.addActionListener(actionListener);

        // Hover Effect
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(230, 230, 250));
            }

            public void mouseExited(MouseEvent e) {
                button.setBackground(Color.WHITE);
            }
        });

        panel.add(button);
    }

    private void styleRoundedButton(JButton button, Color bgColor, Color fgColor) {
        button.setBackground(bgColor);
        button.setForeground(fgColor);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setFont(new Font("SansSerif", Font.BOLD, 18));
        button.setOpaque(true);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(108, 92, 231), 2, true),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)));
    }
}
