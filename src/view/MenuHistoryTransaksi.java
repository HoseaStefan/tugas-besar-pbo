package view;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import controller.TransaksiController;
import model.CurrentUser;
import model.Nasabah;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class MenuHistoryTransaksi {
    public MenuHistoryTransaksi() {
        showMenuHistoryTransaksi();
    }

    public void showMenuHistoryTransaksi() {
        CurrentUser currentUser = CurrentUser.getInstance();
        Nasabah nasabah = currentUser.getNasabah();
        String nasabahId = nasabah.getUser_id();

        Font buttonFont = new Font("SansSerif", Font.BOLD, 18);

        JFrame frame = new JFrame("Histori Transaksi");
        frame.setUndecorated(true);
        frame.setBounds(0, 0, 500, 700);
        frame.setShape(new RoundRectangle2D.Double(0, 0, 500, 700, 30, 30));
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.getHSBColor(0.6f, 0.7f, 0.9f));
        panel.setBounds(0, 0, 500, 700);

        JLabel title = new JLabel("Transaction History");
        title.setBounds(140, 20, 500, 50);
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        title.setForeground(Color.WHITE);
        panel.add(title);

        String[] columnNames = { "Type", "Date", "Amount", "Status" };
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(tableModel);

        table.setFont(new Font("SansSerif", Font.PLAIN, 14));
        table.setRowHeight(40);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN); 

        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public java.awt.Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {
                JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row,
                        column);

                label.setBackground(table.getBackground());

                if (column == 2) {
                    double amount = 0;

                    amount = (double) value;

                    if (amount < 0) {
                        label.setBackground(new Color(255, 204, 204));
                    } else {
                        label.setBackground(new Color(204, 255, 204));
                    }
                }

                if (isSelected) {
                    label.setBackground(table.getSelectionBackground());
                }

                return label;
            }
        });

        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("SansSerif", Font.BOLD, 16));
        header.setBackground(Color.DARK_GRAY);
        header.setForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(20, 80, 460, 500);
        panel.add(scrollPane);

        TransaksiController.loadTransactionData(tableModel, nasabahId);

        JButton exitButton = new JButton("Back To Homepage");
        exitButton.setBounds(120, 600, 260, 50);
        exitButton.setFont(buttonFont);
        exitButton.setBackground(new Color(255, 69, 58));
        exitButton.setForeground(Color.WHITE);
        exitButton.setFocusPainted(false);

        exitButton.addActionListener(e -> {
            frame.dispose();
            new MenuNasabah();
        });

        panel.add(exitButton);

        frame.add(panel);
        frame.setVisible(true);
    }
}
