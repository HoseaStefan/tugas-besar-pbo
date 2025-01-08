package view;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import model.Nasabah;
import controller.BlockNasabahController;

public class MenuBlockNasabahByAdmin {

    private JFrame frame;

    public MenuBlockNasabahByAdmin() {
        showMenuBlockNasabahByAdmin();
    }

    public void showMenuBlockNasabahByAdmin() {
        // Screen setup
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        final int FRAME_WIDTH = 500;
        final int FRAME_HEIGHT = 700;
        int start_x = (screenSize.width - FRAME_WIDTH) / 2;
        int start_y = (screenSize.height - FRAME_HEIGHT) / 2;

        // Frame setup
        frame = new JFrame("BLock Nasabah Menu");
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
        JLabel titleLabel = new JLabel("Block Nasabah Menu", SwingConstants.CENTER);
        titleLabel.setBounds(0, 30, FRAME_WIDTH, 40);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        panel.add(titleLabel);

        // JComboBox for selecting report type
        JComboBox<String> pilihStatus = new JComboBox<>(
                new String[] { "BLOCKED", "ALLOWED", "ALL" });
        pilihStatus.setBounds(120, 450, 260, 40);
        pilihStatus.setFont(new Font("SansSerif", Font.PLAIN, 18));
        panel.add(pilihStatus);

        // Panel to display reports
        JPanel nasabahPanelContainer = new JPanel(); // Container panel for dynamic report list
        nasabahPanelContainer.setLayout(null);
        nasabahPanelContainer.setBounds(50, 120, 400, 300);
        panel.add(nasabahPanelContainer);

        // Action listener for JComboBox
        pilihStatus.addActionListener(e -> {
            String selectedTypeReport = (String) pilihStatus.getSelectedItem();

            // Clear the previous report panel before updating
            nasabahPanelContainer.removeAll(); // Remove old components (reports)
            displayNasabahByStatus(nasabahPanelContainer, selectedTypeReport); // Refresh the report list
            nasabahPanelContainer.revalidate(); // Revalidate the layout
            nasabahPanelContainer.repaint(); // Repaint the container to update the UI
        });

        // Default value setting
        pilihStatus.setSelectedIndex(2);

        // Back Button with decorator
        JButton backButton = new JButton("Back to Menu Admin");
        Component.styleRoundedButton(backButton, new Color(255, 69, 58), Color.WHITE);
        backButton.setBounds(120, 600, 260, 50);
        backButton.addActionListener(e -> {
            frame.dispose();
            new MenuAdmin();
        });
        Component.addHoverEffect(backButton, new Color(255, 69, 58), new Color(255, 82, 82));
        panel.add(backButton);

        // Add panel to frame
        frame.add(panel);
        frame.setVisible(true);
    }

    private void displayNasabahByStatus(JPanel nasabahPanelContainer, String pilihStatus) {
        List<Nasabah> nasabahs = BlockNasabahController.getNasabahByUserId(pilihStatus);

        // Create a panel to display reports dynamically
        JPanel nasabahPanel = new JPanel();
        nasabahPanel.setLayout(new BoxLayout(nasabahPanel, BoxLayout.Y_AXIS));
        nasabahPanel.setBackground(new Color(34, 59, 94)); // Elegant dark blue color

        JScrollPane scrollPane = new JScrollPane(nasabahPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBounds(0, 0, 400, 300);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        nasabahPanelContainer.add(scrollPane); // Add the scrollable report panel

        // Populate the report list
        if (nasabahs != null && !nasabahs.isEmpty()) {
            for (Nasabah nasabah : nasabahs) {
                JPanel itemPanel = new JPanel();
                itemPanel.setLayout(new BorderLayout());
                itemPanel.setBackground(new Color(0, 102, 153)); // Elegant bright blue color
                itemPanel.setPreferredSize(new Dimension(380, 60));
                itemPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(34, 59, 94)));

                JButton nameButton = new JButton(nasabah.getUser_id());
                Component.styleButtonWithHoverEffect(nameButton);

                nameButton.addActionListener(e -> {
                    frame.dispose();
                    System.out.println(nasabah);
                    new DetailBlockNasabahPage(nasabah);
                });

                itemPanel.add(nameButton, BorderLayout.CENTER);
                nasabahPanel.add(Box.createVerticalStrut(5)); // Add spacing between items
                nasabahPanel.add(itemPanel);
            }
        } else {
            JLabel noDataLabel = new JLabel("No Nasabah available", SwingConstants.CENTER);
            noDataLabel.setFont(new Font("SansSerif", Font.ITALIC, 16));
            noDataLabel.setForeground(Color.WHITE);
            nasabahPanel.add(noDataLabel);
        }
    }

}