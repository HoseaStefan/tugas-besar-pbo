package view;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

import model.CurrentUser;
import model.Nasabah;

public class FormSetorSaldo {

    private JFrame frame;

    public FormSetorSaldo(){
        showFormSetorSaldo();
    }

    public void showFormSetorSaldo(){
        CurrentUser currentUser = CurrentUser.getInstance();
        Nasabah nasabah = currentUser.getNasabah();

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();

        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;

        final int FRAME_WIDTH = 500;
        final int FRAME_HEIGHT = 700;

        int start_x = screenWidth / 2 - (FRAME_WIDTH / 2);
        int start_y = screenHeight / 2 - (FRAME_HEIGHT / 2);

        Font buttonFont = new Font("SansSerif", Font.BOLD, 18);

        frame = new JFrame("Login");
        frame.setUndecorated(true);
        frame.setBounds(start_x, start_y, FRAME_WIDTH, FRAME_HEIGHT);
        frame.setShape(new RoundRectangle2D.Double(0, 0, FRAME_WIDTH, FRAME_HEIGHT, 30, 30));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.getHSBColor(0.6f, 0.7f, 0.9f));
        panel.setBounds(0, 0, FRAME_WIDTH, FRAME_HEIGHT);

        JLabel title = new JLabel("SALDO ANDA : Rp." + nasabah.getSaldo());
        title.setBounds(50, 50, 400, 30);
        title.setFont(new Font("SansSerif", Font.BOLD, 20));
        title.setForeground(Color.WHITE);
        panel.add(title);

        JLabel usernameLabel = new JLabel("Input Jumlah Saldo Yang Ingin Di Setor : ");
        usernameLabel.setBounds(120, 200, 500, 50);
        title.setForeground(Color.WHITE);
        panel.add(usernameLabel);

        JTextField inputUsername = new JTextField(16);
        inputUsername.setHorizontalAlignment(JTextField.CENTER);
        inputUsername.setBorder(BorderFactory.createEmptyBorder());
        inputUsername.setBounds(120, 240, 260, 50);
        panel.add(inputUsername);

        JButton topUpButton = new JButton("SETOR!");
        topUpButton.setBounds(120, 310, 260, 50);
        Component.styleButton(topUpButton, new Color(3, 123, 252), buttonFont); 
        topUpButton.addActionListener(e -> {
            frame.dispose();
            // new FormLupaPassword(); 
        });
        panel.add(topUpButton);

        JButton exitButton = new JButton("Back To Homepage");
        exitButton.setBounds(120, 600, 260, 50);
        Component.styleButton(exitButton, new Color(255, 69, 58), buttonFont);
        exitButton.addActionListener(e -> {
            frame.dispose();
            new MenuNasabah();
        });
        panel.add(exitButton);

        frame.add(panel);
        frame.setVisible(true);
    }
}
