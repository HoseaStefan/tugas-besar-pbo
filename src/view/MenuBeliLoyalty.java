// package view;

// import controller.LoyaltyController;
// import java.awt.Color;
// import java.awt.Dimension;
// import java.awt.Font;
// import java.awt.Toolkit;
// import java.awt.geom.RoundRectangle2D;
// import java.sql.Timestamp;
// import javax.swing.JButton;
// import javax.swing.JFrame;
// import javax.swing.JLabel;
// import javax.swing.JOptionPane;
// import javax.swing.JPanel;
// import model.CurrentUser;
// import model.Nasabah;
// import model.User;

// public class MenuBeliLoyalty {
//     private  static JFrame frame;

//     public MenuBeliLoyalty() {
//         menuMembeliLoyalty();
//     }

//     public void menuMembeliLoyalty() {

//         LoyaltyController loyaltyController =  new LoyaltyController();

//         Font buttonFont = new Font("SansSerif", Font.BOLD, 18);
        
//         CurrentUser currentUser = CurrentUser.getInstance();
//         User user = currentUser.getUser();
//         Nasabah nasabah = currentUser.getNasabah();

//         Timestamp startDate = new Timestamp(System.currentTimeMillis());

//         Toolkit toolkit = Toolkit.getDefaultToolkit();
//         Dimension screenSize = toolkit.getScreenSize();

//         int screenWidth = screenSize.width;
//         int screenHeight = screenSize.height;

//         final int FRAME_WIDTH = 500;
//         final int FRAME_HEIGHT = 700;

//         int start_x = screenWidth / 2 - (FRAME_WIDTH / 2);
//         int start_y = screenHeight / 2 - (FRAME_HEIGHT / 2);

//         frame = new JFrame("Loyalty");
//         frame.setUndecorated(true);
//         frame.setBounds(start_x, start_y, FRAME_WIDTH, FRAME_HEIGHT);
//         frame.setShape(new RoundRectangle2D.Double(0, 0, FRAME_WIDTH, FRAME_HEIGHT, 30, 30));
//         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

//         JPanel panel = new JPanel();
//         panel.setLayout(null);
//         panel.setBackground(Color.getHSBColor(0.6f, 0.7f, 0.9f));
//         panel.setBounds(0, 0, FRAME_WIDTH, FRAME_HEIGHT);

//         JLabel saldoLabel = new JLabel("Current saldo : Rp." + nasabah.getSaldo());
//         saldoLabel.setBounds(50, 130, 400, 25);
//         saldoLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
//         saldoLabel.setForeground(Color.WHITE);
//         panel.add(saldoLabel);

//         if (loyaltyController.getLoyaltyByUserId(nasabah.getUser_id()) == true) {
//             JButton btnBuyLoyalty = new JButton("BUY NOW ONLY 99k !!");
//             btnBuyLoyalty.setBounds(120, 300, 260, 50);
//             Component.styleButton(btnBuyLoyalty, new Color(3, 123, 252), buttonFont);
//             panel.add(btnBuyLoyalty);
//             btnBuyLoyalty.addActionListener((actionEvent) -> {
//                 try {
//                     if (loyaltyController.buyLoyaltyByUserId(nasabah.getUser_id())) {
//                         JOptionPane.showMessageDialog(frame, "Success","info",JOptionPane.INFORMATION_MESSAGE);
                        
//                     }
                    
//                 } catch (Exception e) {
//                     e.printStackTrace();
//                 }
//             });   
//         } else {
//             JButton btnBuyLoyalty = new JButton("Your Loyalty is Active !!");
//             btnBuyLoyalty.setBounds(120, 300, 260, 50);
//             Component.styleButton(btnBuyLoyalty, new Color(3, 123, 252), buttonFont);
//             JOptionPane.showMessageDialog(frame, "Loyalty is already exist","info",JOptionPane.INFORMATION_MESSAGE);
//             panel.add(btnBuyLoyalty);

//         }

//         JButton btnBack = new JButton("Back to Main Menu");
//         btnBack.setBounds(130, 600, 260, 50);
//         Component.styleButton(btnBack, new Color(255, 69, 58), buttonFont);
//         btnBack.addActionListener(e -> {
//             frame.dispose();
//             new MenuNasabah();
//         });

        

//         frame.add(panel);
//         frame.setVisible(true);

//     }
// }
