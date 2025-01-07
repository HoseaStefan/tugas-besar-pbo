package view;

import java.awt.*;
import javax.swing.*;

public class Component {
    public static void styleButton(JButton button, Color backgroundColor, Font font) {
        button.setFont(font);
        button.setBackground(backgroundColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(backgroundColor.darker(), 2));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

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

    public Color getBackground() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getBackground'");
    }

    public static void addHoverEffect(JButton button, Color normalColor, Color hoverColor) {
        button.setBackground(normalColor);
        button.setFont(new Font("SansSerif", Font.BOLD, 16));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Hover Effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(hoverColor);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(normalColor);
            }
        });
    }

    public static void styleRoundedButton(JButton button, Color bgColor, Color fgColor) {
        button.setBackground(bgColor);
        button.setForeground(fgColor);
        button.setFont(new Font("SansSerif", Font.BOLD, 16));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
    }

    public static void styleMemberButton(JButton button, Color borderColor, Color textColor) {
        button.setContentAreaFilled(false); // Background transparan
        button.setOpaque(false); // Pastikan transparansi diterapkan
        button.setForeground(textColor); // Warna teks tombol
        button.setFont(new Font("SansSerif", Font.PLAIN, 14)); // Font default
        button.setFocusPainted(false); // Hilangkan efek fokus
        button.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Cursor tangan

        // Variabel untuk hover state dan transformasi ukuran
        final boolean[] isHovered = { false };
        final float[] scale = { 1.0f }; // Skala tombol (1.0 normal)

        // Timer untuk animasi transisi skala
        Timer timer = new Timer(15, e -> {
            if (isHovered[0] && scale[0] < 1.03f) {
                scale[0] += 0.01f; // Naikkan skala hingga mencapai 1.03
                button.repaint();
            } else if (!isHovered[0] && scale[0] > 1.0f) {
                scale[0] -= 0.01f; // Kembalikan skala hingga mencapai 1.0
                button.repaint();
            }
        });
        timer.start();

        // Hover listener
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                isHovered[0] = true;
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                isHovered[0] = false;
            }
        });

        // Atur multi-line teks dengan tanda + lebih besar
        String buttonText = "<html><div style='text-align:center;'>"
                + "<div style='padding:5px;'>"
                + "<span style='font-size:32px;'>+</span><br>" // Ukuran font besar untuk tanda +
                + "<span style='font-size:10px;'>Tambah Member</span>" // Ukuran font normal untuk teks lainnya
                + "</div>"
                + "</div></html>";
        button.setText(buttonText);

        // Custom painting untuk efek border dan transformasi
        button.setBorder(BorderFactory.createEmptyBorder()); // Hilangkan border default
        button.setBorderPainted(false);

        button.setUI(new javax.swing.plaf.basic.BasicButtonUI() {
            @Override
            public void paint(Graphics g, JComponent c) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int width = button.getWidth();
                int height = button.getHeight();

                // Terapkan transformasi skala
                int borderOffset = (int) ((1 - scale[0]) * width / 2);
                g2.translate(borderOffset, borderOffset);
                g2.scale(scale[0], scale[0]);

                // Gambar border dashed
                g2.setColor(borderColor);
                Stroke dashed = new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[] { 5 },
                        0);
                g2.setStroke(dashed);
                g2.drawRoundRect(2, 2, (int) (width - 4 * scale[0]), (int) (height - 4 * scale[0]), 15, 15);

                // Panggil renderer default untuk teks
                g2.scale(1 / scale[0], 1 / scale[0]);
                g2.translate(-borderOffset, -borderOffset);
                super.paint(g2, c);
                g2.dispose();
            }
        });
    }

    public static void styleButtonWithHoverEffect(JButton button) {
        button.setFont(new Font("SansSerif", Font.BOLD, 16));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(0, 153, 204)); // Warna biru lebih cerah
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Hover Effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(0, 123, 180));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(0, 153, 204));
            }
        });
    }

}
