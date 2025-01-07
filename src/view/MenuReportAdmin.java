package view;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import model.Report;
import controller.ReportController;

public class MenuReportAdmin {

    private JFrame frame;

    public MenuReportAdmin() {
        showMenuReportAdmin();
    }

    public void showMenuReportAdmin() {
        // Screen setup
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        final int FRAME_WIDTH = 500;
        final int FRAME_HEIGHT = 700;
        int start_x = (screenSize.width - FRAME_WIDTH) / 2;
        int start_y = (screenSize.height - FRAME_HEIGHT) / 2;

        // Frame setup
        frame = new JFrame("Report Menu");
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
        JLabel titleLabel = new JLabel("Report Menu", SwingConstants.CENTER);
        titleLabel.setBounds(0, 30, FRAME_WIDTH, 40);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        panel.add(titleLabel);

        // JComboBox for selecting report type
        JComboBox<String> pilihTypeReport = new JComboBox<>(
                new String[] { "DIPROSES", "DITERIMA", "DIABAIKAN", "ALL", "BELUMTERSELESAIKAN" });
        pilihTypeReport.setBounds(120, 450, 260, 40);
        pilihTypeReport.setFont(new Font("SansSerif", Font.PLAIN, 18));
        panel.add(pilihTypeReport);

        // Panel to display reports
        JPanel reportPanelContainer = new JPanel(); // Container panel for dynamic report list
        reportPanelContainer.setLayout(null);
        reportPanelContainer.setBounds(50, 120, 400, 300);
        panel.add(reportPanelContainer);

        // Action listener for JComboBox
        pilihTypeReport.addActionListener(e -> {
            String selectedTypeReport = (String) pilihTypeReport.getSelectedItem();

            // Clear the previous report panel before updating
            reportPanelContainer.removeAll(); // Remove old components (reports)
            displayReport(reportPanelContainer, selectedTypeReport); // Refresh the report list
            reportPanelContainer.revalidate(); // Revalidate the layout
            reportPanelContainer.repaint(); // Repaint the container to update the UI
        });

        // Default value setting
        pilihTypeReport.setSelectedIndex(4);

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

    private void displayReport(JPanel reportPanelContainer, String pilihTypeReport) {
        // Get Reports based on selected status
        List<Report> reports = ReportController.getReportsByUserId(pilihTypeReport);

        // Create a panel to display reports dynamically
        JPanel reportPanel = new JPanel();
        reportPanel.setLayout(new BoxLayout(reportPanel, BoxLayout.Y_AXIS));
        reportPanel.setBackground(new Color(34, 59, 94)); // Elegant dark blue color

        JScrollPane scrollPane = new JScrollPane(reportPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBounds(0, 0, 400, 300);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        reportPanelContainer.add(scrollPane); // Add the scrollable report panel

        // Populate the report list
        if (reports != null && !reports.isEmpty()) {
            for (Report report : reports) {
                JPanel itemPanel = new JPanel();
                itemPanel.setLayout(new BorderLayout());
                itemPanel.setBackground(new Color(0, 102, 153)); // Elegant bright blue color
                itemPanel.setPreferredSize(new Dimension(380, 60));
                itemPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(34, 59, 94)));

                JButton nameButton = new JButton(report.getReport_id());
                Component.styleButtonWithHoverEffect(nameButton);

                nameButton.addActionListener(e -> {
                    frame.dispose();
                    new DetailReportPage(report);
                });

                itemPanel.add(nameButton, BorderLayout.CENTER);
                reportPanel.add(Box.createVerticalStrut(5)); // Add spacing between items
                reportPanel.add(itemPanel);
            }
        } else {
            JLabel noDataLabel = new JLabel("No Reports available", SwingConstants.CENTER);
            noDataLabel.setFont(new Font("SansSerif", Font.ITALIC, 16));
            noDataLabel.setForeground(Color.WHITE);
            reportPanel.add(noDataLabel);
        }
    }

}