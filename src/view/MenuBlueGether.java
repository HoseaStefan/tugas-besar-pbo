package view;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import model.CurrentUser;
import model.Nasabah;
import model.BlueGether;
import controller.BlueGetherController;

public class MenuBlueGether {

    private JFrame frame;

    public MenuBlueGether() {
        showMenuBlueGether();
    }

    public void showMenuBlueGether() {
        // Get current user
        CurrentUser currentUser = CurrentUser.getInstance();
        Nasabah nasabah = currentUser.getNasabah();

        // Screen setup
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        final int FRAME_WIDTH = 500;
        final int FRAME_HEIGHT = 700;
        int start_x = (screenSize.width - FRAME_WIDTH) / 2;
        int start_y = (screenSize.height - FRAME_HEIGHT) / 2;

        // Frame setup
        frame = new JFrame("Blue Gether Menu");
        frame.setUndecorated(true);
        frame.setBounds(start_x, start_y, FRAME_WIDTH, FRAME_HEIGHT);
        frame.setShape(new java.awt.geom.RoundRectangle2D.Double(0, 0, FRAME_WIDTH, FRAME_HEIGHT, 30, 30));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Gradient background panel
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.getHSBColor(0.6f, 0.7f, 0.9f));
        panel.setBounds(0, 0, FRAME_WIDTH, FRAME_HEIGHT);

        // Title Label
        JLabel titleLabel = new JLabel("Blue Gether", SwingConstants.CENTER);
        titleLabel.setBounds(0, 30, FRAME_WIDTH, 40);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        panel.add(titleLabel);

        // Total Dana Label
        double totalDana = BlueGetherController.getTotalDanaGetherByUserId(nasabah.getUser_id());
        JLabel totalDanaLabel = new JLabel("Total Dana: Rp. " + String.format("%,.2f", totalDana));
        totalDanaLabel.setBounds(50, 90, 400, 30);
        totalDanaLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        totalDanaLabel.setForeground(Color.WHITE);
        panel.add(totalDanaLabel);

        // Scrollable Blue Gether List
        displayBlueGethers(panel);

        // Create Blue Gether Button
        JButton createButton = new JButton("Create New Blue Gether");
        Component.styleRoundedButton(createButton, new Color(0, 102, 204), Color.WHITE);
        createButton.setBounds(120, 500, 260, 50);
        createButton.addActionListener(e -> {
            frame.dispose();
            new MenuCreateBlueGether();
        });
        // Adding hover effect for Create button
        Component.addHoverEffect(createButton, new Color(0, 102, 204), new Color(0, 123, 180));
        panel.add(createButton);

        // Back Button
        JButton backButton = new JButton("Back to Menu Tabungan");
        Component.styleRoundedButton(backButton, new Color(255, 69, 58), Color.WHITE);
        backButton.setBounds(120, 600, 260, 50);
        backButton.addActionListener(e -> {
            frame.dispose();
            new MenuTabungan();
        });
        // Adding hover effect for Back button
        Component.addHoverEffect(backButton, new Color(255, 69, 58), new Color(255, 82, 82));
        panel.add(backButton);

        // Add panel to frame
        frame.add(panel);
        frame.setVisible(true);
    }

    private void displayBlueGethers(JPanel panel) {
        CurrentUser currentUser = CurrentUser.getInstance();
        String userId = currentUser.getNasabah().getUser_id();

        // Get Blue Gethers
        List<BlueGether> blueGethers = BlueGetherController.getBlueGethersByUserId(userId);

        // Scrollable panel setup
        JPanel blueGetherPanel = new JPanel();
        blueGetherPanel.setLayout(new BoxLayout(blueGetherPanel, BoxLayout.Y_AXIS));
        blueGetherPanel.setBackground(new Color(34, 59, 94)); // Dark blue

        JScrollPane scrollPane = new JScrollPane(blueGetherPanel);
        scrollPane.setBounds(50, 120, 400, 300);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        // Set viewport background
        scrollPane.getViewport().setBackground(new Color(34, 59, 94));

        panel.add(scrollPane);

        // Populate Blue Gethers list
        if (blueGethers != null && !blueGethers.isEmpty()) {
            for (BlueGether blueGether : blueGethers) {
                JPanel itemPanel = new JPanel();
                itemPanel.setLayout(new BorderLayout());
                itemPanel.setBackground(new Color(0, 102, 153)); // Bright blue
                itemPanel.setPreferredSize(new Dimension(380, 60));
                itemPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(34, 59, 94)));

                JButton nameButton = new JButton(blueGether.getNamaTabungan());
                nameButton.setFont(new Font("SansSerif", Font.BOLD, 16));
                nameButton.setForeground(Color.WHITE);
                nameButton.setBackground(new Color(0, 153, 204)); // Lighter blue
                nameButton.setFocusPainted(false);
                nameButton.setBorderPainted(false);
                nameButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

                // Hover effect for item button
                Component.styleButtonWithHoverEffect(nameButton);

                nameButton.addActionListener(e -> {
                    frame.dispose();
                    new DetailBlueGetherPage(blueGether);
                });

                itemPanel.add(nameButton, BorderLayout.CENTER);
                blueGetherPanel.add(Box.createVerticalStrut(5)); // Spacing
                blueGetherPanel.add(itemPanel);
            }
        } else {
            JLabel noDataLabel = new JLabel("No Blue Gethers available", SwingConstants.CENTER);
            noDataLabel.setFont(new Font("SansSerif", Font.ITALIC, 16));
            noDataLabel.setForeground(Color.WHITE);
            blueGetherPanel.add(noDataLabel);
        }
    }

}
