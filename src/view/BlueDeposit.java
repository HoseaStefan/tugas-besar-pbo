package view;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import javax.swing.*;

public class BlueDeposit {

    private JFrame frame;

    public BlueDeposit() {
        menuBlueDeposito();
    }

    public void menuBlueDeposito() {
        // Setup frame size and position
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();

        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;

        final int FRAME_WIDTH = 980;
        final int FRAME_HEIGHT = 500;

        int start_x = screenWidth / 2 - (FRAME_WIDTH / 2);
        int start_y = screenHeight / 2 - (FRAME_HEIGHT / 2);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(0, 0, FRAME_WIDTH, FRAME_HEIGHT);

        JLabel title = new JLabel("===== Blue Deposito =====");
        title.setBounds(350, 10, 500, 50);
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        panel.add(title);

        JRadioButton priaRadio = new JRadioButton("Pria");
        JRadioButton wanitaRadio = new JRadioButton("Wanita");

        priaRadio.setBounds(250, 250, 90, 50);
        wanitaRadio.setBounds(340, 250, 100, 50);

        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(priaRadio);
        genderGroup.add(wanitaRadio);

        panel.add(priaRadio);
        panel.add(wanitaRadio);

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new BlueDeposit();
    }
}
