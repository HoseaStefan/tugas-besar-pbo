package view;

import javax.swing.*;

import controller.UserController;
import model.CurrentUser;
import model.Nasabah;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;

public class FormNewPIN {

    private StringBuilder pinBuilder = new StringBuilder();

    public FormNewPIN() {
        showFormNewPIN();
    }

    public void showFormNewPIN() {

        CurrentUser currentUser = CurrentUser.getInstance();
        Nasabah nasabah = currentUser.getNasabah();

        Font buttonFont = new Font("SansSerif", Font.BOLD, 18);

        JFrame frame = new JFrame("PIN");
        frame.setUndecorated(true);
        frame.setBounds(0, 0, 500, 700);
        frame.setShape(new RoundRectangle2D.Double(0, 0, 500, 700, 30, 30));
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.getHSBColor(0.6f, 0.7f, 0.9f));
        panel.setBounds(0, 0, 500, 700);

        JLabel title = new JLabel("Input New PIN");
        title.setBounds(170, 50, 500, 50);
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        title.setForeground(Color.WHITE);
        panel.add(title);

        JTextField inputPIN = new JTextField();
        inputPIN.setHorizontalAlignment(JTextField.CENTER);
        inputPIN.setBounds(120, 150, 260, 50);
        inputPIN.setFont(new Font("SansSerif", Font.BOLD, 24));
        inputPIN.setEditable(false);
        inputPIN.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        panel.add(inputPIN);

        ActionListener buttonListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (pinBuilder.length() < 4) {
                    JButton button = (JButton) e.getSource();
                    pinBuilder.append(button.getText());
                    inputPIN.setText(pinBuilder.toString());
                }

                if (pinBuilder.length() == 4) {
                    JOptionPane.showMessageDialog(frame, "PIN entry complete: " + pinBuilder.toString());
                    UserController.insertNewPIN(nasabah, pinBuilder.toString());
                    frame.dispose();
                    new MenuNasabah();
                }
            }
        };

        int x = 120, y = 250;
        for (int i = 1; i <= 9; i++) {
            JButton numberButton = new JButton(String.valueOf(i));
            numberButton.setBounds(x, y, 80, 50);
            numberButton.setFont(buttonFont);
            Component.styleButton(numberButton, new Color(3, 123, 252), buttonFont);
            panel.add(numberButton);
            numberButton.addActionListener(buttonListener);

            x += 90;
            if (i % 3 == 0) {
                x = 120;
                y += 70;
            }
        }

        JButton zeroButton = new JButton("0");
        zeroButton.setBounds(210, y, 80, 50);
        zeroButton.setFont(buttonFont);
        Component.styleButton(zeroButton, new Color(3, 123, 252), buttonFont);
        panel.add(zeroButton);
        zeroButton.addActionListener(buttonListener);

        JButton deleteButton = new JButton("X");
        deleteButton.setBounds(300, y, 80, 50);
        deleteButton.setFont(buttonFont);
        deleteButton.setBackground(new Color(220, 53, 69));
        deleteButton.setForeground(Color.WHITE);
        deleteButton.addActionListener(e -> {
            if (pinBuilder.length() > 0) {
                pinBuilder.deleteCharAt(pinBuilder.length() - 1);
                inputPIN.setText(pinBuilder.toString());
            }
        });
        panel.add(deleteButton);

        JButton exitButton = new JButton("Cancel");
        exitButton.setBounds(120, 600, 260, 50);
        exitButton.setFont(buttonFont);
        exitButton.setBackground(new Color(255, 69, 58));
        exitButton.setForeground(Color.WHITE);
        exitButton.setFocusPainted(false);

        exitButton.addActionListener(e -> {
            frame.dispose();
            JOptionPane.showMessageDialog(null, "PIN input cancelled.");
            new MainMenu();
        });

        panel.add(exitButton);

        frame.add(panel);
        frame.setVisible(true);
    }
}
