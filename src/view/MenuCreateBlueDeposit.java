package view;

import controller.CreateTabunganController;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;
import java.sql.Timestamp;
import java.util.Calendar;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import model.BlueDeposito;
import model.CurrentUser;
import model.DepositoType;
import model.Nasabah;
import model.TabunganType;
import model.User;

public class MenuCreateBlueDeposit {

    private static JFrame frame;

    public MenuCreateBlueDeposit() {
        menuCreateBlueDeposit();
    }

    public void menuCreateBlueDeposit() {

        CurrentUser currentUser = CurrentUser.getInstance();
        User user = currentUser.getUser();
        Nasabah nasabah = currentUser.getNasabah();

        Timestamp startDate = new Timestamp(System.currentTimeMillis());

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();

        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;

        final int FRAME_WIDTH = 500;
        final int FRAME_HEIGHT = 700;

        int start_x = screenWidth / 2 - (FRAME_WIDTH / 2);
        int start_y = screenHeight / 2 - (FRAME_HEIGHT / 2);

        Font buttonFont = new Font("SansSerif", Font.BOLD, 18);

        frame = new JFrame("Blue Deposit");
        frame.setUndecorated(true);
        frame.setBounds(start_x, start_y, FRAME_WIDTH, FRAME_HEIGHT);
        frame.setShape(new RoundRectangle2D.Double(0, 0, FRAME_WIDTH, FRAME_HEIGHT, 30, 30));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.getHSBColor(0.6f, 0.7f, 0.9f));
        panel.setBounds(0, 0, FRAME_WIDTH, FRAME_HEIGHT);

        // Label judul
        JLabel title = new JLabel("Blue Deposito - Create");
        title.setBounds(120, 50, 500, 50);
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        title.setForeground(Color.WHITE);
        panel.add(title);

        // Label nominal
        JLabel lblCreate = new JLabel("Masukkan nominal untuk disimpan:");
        lblCreate.setBounds(50, 150, 400, 30);
        lblCreate.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lblCreate.setForeground(Color.WHITE);
        panel.add(lblCreate);

        // TextField nominal
        JTextField saldoawaField = new JTextField();
        saldoawaField.setBounds(50, 190, 400, 30);
        panel.add(saldoawaField);

        // Label tipe deposito
        JLabel lblDepositType = new JLabel("Pilih tipe deposito:");
        lblDepositType.setBounds(50, 230, 300, 30);
        lblDepositType.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lblDepositType.setForeground(Color.WHITE);
        panel.add(lblDepositType);

        // Radio buttons untuk tipe deposito
        JRadioButton depo3Bulan = new JRadioButton("3 Bulan");
        JRadioButton depo6Bulan = new JRadioButton("6 Bulan");
        JRadioButton depo12Bulan = new JRadioButton("12 Bulan");

        depo3Bulan.setBounds(50, 270, 100, 30);
        depo6Bulan.setBounds(180, 270, 100, 30);
        depo12Bulan.setBounds(310, 270, 100, 30);

        depo3Bulan.setBackground(panel.getBackground());
        depo6Bulan.setBackground(panel.getBackground());
        depo12Bulan.setBackground(panel.getBackground());

        depo3Bulan.setForeground(Color.WHITE);
        depo6Bulan.setForeground(Color.WHITE);
        depo12Bulan.setForeground(Color.WHITE);

        ButtonGroup group = new ButtonGroup();
        group.add(depo3Bulan);
        group.add(depo6Bulan);
        group.add(depo12Bulan);

        panel.add(depo3Bulan);
        panel.add(depo6Bulan);
        panel.add(depo12Bulan);

        // Tombol Submit
        JButton btnSubmit = new JButton("Submit");
        btnSubmit.setBounds(50, 320, 180, 40);
        btnSubmit.setFont(buttonFont);
        btnSubmit.setBackground(Color.WHITE);
        btnSubmit.setForeground(Color.BLUE);
        panel.add(btnSubmit);

        // Tombol Back
        JButton btnBack = new JButton("Back");
        btnBack.setBounds(270, 320, 180, 40);
        btnBack.setFont(buttonFont);
        btnBack.setBackground(Color.RED);
        btnBack.setForeground(Color.WHITE);
        panel.add(btnBack);

        JButton Close = new JButton("Back to Menu Tabungan");
        Close.setBounds(130, 600, 260, 50);
        Component.styleButton(Close, new Color(255, 69, 58), buttonFont);
        Close.addActionListener(e -> {
            frame.dispose();
            new MenuTabungan();
        });
        panel.add(Close);

        // Label output
        JLabel lblOutput = new JLabel();
        lblOutput.setBounds(50, 380, 400, 30);
        lblOutput.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lblOutput.setForeground(Color.BLACK);
        panel.add(lblOutput);

        // ActionListener tombol Submit
        btnSubmit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String saldoAwal = saldoawaField.getText().replace(",", "").trim();
                    if (saldoAwal.isEmpty()) {
                        JOptionPane.showMessageDialog(frame,
                                "Please fill all fields with valid numeric values.",
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }

                    Timestamp endDate = null;
                    DepositoType depoType = null;

                    if (depo3Bulan.isSelected()) {
                        depoType = DepositoType.TIGABULAN;
                        endDate = calculateEndDate(startDate, 3);

                    } else if (depo6Bulan.isSelected()) {
                        depoType = DepositoType.ENAMBULAN;
                        endDate = calculateEndDate(startDate, 6);

                    } else if (depo12Bulan.isSelected()) {
                        depoType = DepositoType.SETAHUN;
                        endDate = calculateEndDate(startDate, 12);

                    } else {
                        JOptionPane.showMessageDialog(frame,
                                "Input error ",
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }

                    double saldo_awal = Double.parseDouble(saldoAwal);

                    BlueDeposito blueDeposito = new BlueDeposito("", nasabah.getUser_id(), null,
                            TabunganType.BLUEDEPOSITO, saldo_awal, startDate, depoType, saldo_awal, endDate, false);

                    CreateTabunganController controller = new CreateTabunganController();
                    boolean isCreated = controller.createBlueDeposito(blueDeposito);

                    
                    if (isCreated) {
                        JOptionPane.showMessageDialog(frame,
                                "Deposito berhasil dibuat",
                                "Info", JOptionPane.INFORMATION_MESSAGE);
                        frame.dispose();
                        new MenuBlueDeposit(); // Kembali ke menu utama
                    } else {
                        JOptionPane.showMessageDialog(frame,
                                "Deposito tidak bisa dibuat. Please try again.",
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    frame.dispose();
                    new MenuTabungan();

                } catch (NumberFormatException ex) {
                    // JOptionPane.showMessageDialog(frame,
                    // "Input tidak valid!",
                    // "Error", JOptionPane.ERROR_MESSAGE);
                }

                frame.dispose();
                new MenuBlueDeposit();

            }
        });

        // ActionListener tombol Close
        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new MenuBlueDeposit();
            }
        });

        // Menambahkan panel ke frame dan menampilkan frame
        frame.setVisible(true);

    }
    public Timestamp calculateEndDate(Timestamp startDate, int depoUpdate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(startDate.getTime());
        calendar.add(Calendar.MONTH, depoUpdate);
        return new Timestamp(calendar.getTimeInMillis());
    }
}
