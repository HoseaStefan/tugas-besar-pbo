package view;

import controller.AdminController;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class FormDeletePromoCode {

    public FormDeletePromoCode() {
        showDeletePromoCodeForm();
    }

    public void showDeletePromoCodeForm() {
        Font buttonFont = new Font("SansSerif", Font.BOLD, 18);

        JFrame frame = new JFrame("Delete Promo Code");
        frame.setUndecorated(true);
        frame.setBounds(0, 0, 600, 700);
        frame.setShape(new RoundRectangle2D.Double(0, 0, 600, 700, 30, 30));
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.getHSBColor(0.6f, 0.7f, 0.9f));
        panel.setBounds(0, 0, 600, 700);

        JLabel title = new JLabel("Delete Promo Code");
        title.setBounds(180, 20, 500, 50);
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        title.setForeground(Color.WHITE);
        panel.add(title);

        String[] columnNames = { "Promo Code", "Promo Type", "Discount Value", "Action" };
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(tableModel);

        table.setFont(new Font("SansSerif", Font.PLAIN, 14));
        table.setRowHeight(40);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);

        table.getColumn("Action").setCellRenderer(new ButtonRenderer());
        table.getColumn("Action").setCellEditor(new ButtonEditor(table));

        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("SansSerif", Font.BOLD, 16));
        header.setBackground(Color.DARK_GRAY);
        header.setForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(20, 80, 560, 500);
        panel.add(scrollPane);

        AdminController.loadPromoCodesForDeletion(tableModel);

        JButton exitButton = new JButton("Back to Menu");
        exitButton.setBounds(170, 600, 260, 50);
        exitButton.setFont(buttonFont);
        exitButton.setBackground(new Color(255, 69, 58));
        exitButton.setForeground(Color.WHITE);
        exitButton.setFocusPainted(false);

        exitButton.addActionListener(e -> {
            frame.dispose();
            new MenuAdmin();
        });

        panel.add(exitButton);

        frame.add(panel);
        frame.setVisible(true);
    }

    class ButtonRenderer extends JButton implements javax.swing.table.TableCellRenderer {

        public ButtonRenderer() {
            setText("Delete");
            setBackground(new Color(255, 69, 58));
            setForeground(Color.WHITE);
            setFocusPainted(false);
        }

        @Override
        public java.awt.Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                boolean hasFocus, int row, int column) {
            return this;
        }
    }

    class ButtonEditor extends DefaultCellEditor {

        private JButton button;
        private int row;
        private JTable table;

        public ButtonEditor(JTable table) {
            super(new JCheckBox());
            this.table = table;
            button = new JButton("Delete");
            button.setOpaque(true);
            button.setBackground(new Color(255, 69, 58));
            button.setForeground(Color.WHITE);
            button.setFocusPainted(false);

            button.addActionListener(e -> {
                fireEditingStopped();

                String promoCode = (String) table.getModel().getValueAt(row, 0);
                boolean isDeleted = AdminController.deletePromoCode(promoCode);
                System.out.println(promoCode);
                if (isDeleted) {
                    JOptionPane.showMessageDialog(null, "Promo code deleted successfully!");
                    ((DefaultTableModel) table.getModel()).removeRow(row);
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to delete promo code.");
                }
            });
        }

        @Override
        public java.awt.Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
                int column) {
            this.row = row;
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            return null;
        }

        @Override
        public boolean stopCellEditing() {
            return super.stopCellEditing();
        }
    }
}
