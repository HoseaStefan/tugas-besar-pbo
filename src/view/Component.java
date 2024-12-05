package view;

import javax.swing.*;

import java.awt.*;

public class Component {
    public static void styleButton(JButton button, Color backgroundColor, Font font) {
        button.setFont(font);
        button.setBackground(backgroundColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false); 
        button.setBorder(BorderFactory.createLineBorder(backgroundColor.darker(), 2)); // Subtle border

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(backgroundColor.brighter()); // Lighter on hover
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(backgroundColor); // Original color
            }
        });
    }
}
