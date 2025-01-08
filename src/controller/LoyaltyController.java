package controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import javax.swing.JOptionPane;

public class LoyaltyController {
    static DatabaseHandler conn = new DatabaseHandler();

    public static Boolean getLoyaltyByUserId(String userId) {
        conn.connect();
        try {
            String query = "SELECT loyalty_id FROM users WHERE user_id = ?";
            PreparedStatement stmt = conn.con.prepareStatement(query);
            stmt.setString(1, userId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String loyaltyId = rs.getString("loyalty_id");

                // Jika loyalty_id tidak null, cek status loyalty_active
                if (loyaltyId != null) {
                    String loyaltyQuery = "SELECT loyalty_active FROM loyalty WHERE loyalty_id = ?";
                    PreparedStatement loyaltyStmt = conn.con.prepareStatement(loyaltyQuery);
                    loyaltyStmt.setString(1, loyaltyId);
                    ResultSet loyaltyRs = loyaltyStmt.executeQuery();

                    if (loyaltyRs.next()) {
                        boolean loyaltyActive = loyaltyRs.getBoolean("loyalty_active");
                        if (loyaltyActive) {
                            return true; // loyalty_active adalah true
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace(); // Log error untuk debugging
        }
        return false; // Jika kondisi tidak terpenuhi, kembalikan false
    }

    public static Boolean buyLoyaltyByUserId(String user_id) {
        conn.connect();
        try {
            // Query untuk mendapatkan loyalty_id dan saldo dari tabel users
            String query = "SELECT loyalty_id, saldo FROM users WHERE user_id = ?";
            PreparedStatement stmt = conn.con.prepareStatement(query);
            stmt.setString(1, user_id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String loyaltyId = rs.getString("loyalty_id");
                double saldoUser = rs.getDouble("saldo");
                double loyaltyValue = 99000; // Harga loyalty selalu 99000

                // Jika loyalty_id null, lanjutkan proses pembelian loyalty
                if (loyaltyId == null) {
                    // Cek apakah saldo user cukup untuk membeli loyalty
                    if (saldoUser >= loyaltyValue) {
                        // Generate loyalty_id baru
                        String newLoyaltyId = generateLoyaltyId();

                        // Hitung expired_date: waktu saat ini + 3 bulan
                        Calendar cal = Calendar.getInstance();
                        cal.add(Calendar.MONTH, 3);
                        Timestamp expiredDate = new Timestamp(cal.getTimeInMillis());

                        // Update user untuk membeli loyalty
                        String updateUserQuery = "UPDATE users SET loyalty_id = ?, saldo = saldo - ? WHERE user_id = ?";
                        PreparedStatement updateUserStmt = conn.con.prepareStatement(updateUserQuery);
                        updateUserStmt.setString(1, newLoyaltyId);
                        updateUserStmt.setDouble(2, loyaltyValue);
                        updateUserStmt.setString(3, user_id);
                        updateUserStmt.executeUpdate();

                        // Update loyalty atribut termasuk expired_date
                        String updateLoyaltyQuery = "UPDATE loyalty SET banyak_voucher_topup_money = 100, banyak_voucher_transfer = 100, loyalty_active = true, expired_date = ? WHERE loyalty_id = ?";
                        PreparedStatement updateLoyaltyStmt = conn.con.prepareStatement(updateLoyaltyQuery);
                        updateLoyaltyStmt.setTimestamp(1, expiredDate);
                        updateLoyaltyStmt.setString(2, newLoyaltyId);
                        updateLoyaltyStmt.executeUpdate();

                        JOptionPane.showMessageDialog(null, "Berhasil membeli loyalty!", "Sukses",
                                JOptionPane.INFORMATION_MESSAGE);
                        return true; // Berhasil membeli loyalty
                    } else {
                        JOptionPane.showMessageDialog(null, "Saldo user tidak mencukupi untuk membeli loyalty.",
                                "Kesalahan", JOptionPane.ERROR_MESSAGE);
                        return false;
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "User sudah memiliki loyalty.", "Informasi",
                            JOptionPane.WARNING_MESSAGE);
                    return false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace(); // Log error untuk debugging
            JOptionPane.showMessageDialog(null, "Terjadi kesalahan: " + e.getMessage(), "Kesalahan",
                    JOptionPane.ERROR_MESSAGE);
        }
        return false; // Jika kondisi tidak terpenuhi
    }




    public static String generateLoyaltyId() {
        conn.connect();
        String prefix = "l-"; // Prefix untuk loyalty_id
        int nextId = 1;

        try {
            String query = "SELECT COUNT(*) AS total FROM loyalty";
            PreparedStatement stmt = conn.con.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int total = rs.getInt("total");
                nextId = total + 1;
            }
            return prefix + nextId;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    
}
