package controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.swing.JOptionPane;
import model.Loyalty;

public class LoyaltyController {
    static DatabaseHandler conn = new DatabaseHandler();

    public static List<Loyalty> getLoyaltyByUserId(String userId) {
        conn.connect();
        List<Loyalty> loyaltyList = new ArrayList<>();

        try {
            String query = "SELECT * FROM loyalty WHERE user_id = ?";
            PreparedStatement stmt = conn.con.prepareStatement(query);
            stmt.setString(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Loyalty loyalty = new Loyalty(
                        rs.getString("loyalty_id"),
                        rs.getNString("user_id"),
                        rs.getDouble("loyalty_value"),
                        rs.getInt("banyak_voucher_setor"),
                        rs.getInt("banyak_voucher_transfer"),
                        rs.getInt("banyak_voucher_topup_money"),
                        rs.getBoolean("loyalty_active"),
                        rs.getTimestamp("expired_date"));

                // Only add to the list if loyalty is active
                loyaltyList.add(loyalty);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return loyaltyList;
    }

    public static boolean useVoucherSetor(String userId) {
        conn.connect();
        try {
            String query = "SELECT banyak_voucher_setor FROM loyalty WHERE user_id = ?";
            PreparedStatement stmt = conn.con.prepareStatement(query);
            stmt.setString(1, userId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int banyakVoucherSetor = rs.getInt("banyak_voucher_setor");

                if (banyakVoucherSetor > 0) {
                    // Decrement the voucher count
                    String updateQuery = "UPDATE loyalty SET banyak_voucher_setor = ? WHERE user_id = ?";
                    PreparedStatement updateStmt = conn.con.prepareStatement(updateQuery);
                    updateStmt.setInt(1, banyakVoucherSetor - 1);
                    updateStmt.setString(2, userId);
                    updateStmt.executeUpdate();

                    return true; // Voucher available and decremented
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false; // Voucher not available or error occurred
    }

    public static boolean useVoucherTransfer(String userId) {
        conn.connect();
        try {
            String query = "SELECT banyak_voucher_transfer FROM loyalty WHERE user_id = ?";
            PreparedStatement stmt = conn.con.prepareStatement(query);
            stmt.setString(1, userId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int banyakVoucherTransfer = rs.getInt("banyak_voucher_transfer");

                if (banyakVoucherTransfer > 0) {
                    // Decrement the voucher count
                    String updateQuery = "UPDATE loyalty SET banyak_voucher_transfer = ? WHERE user_id = ?";
                    PreparedStatement updateStmt = conn.con.prepareStatement(updateQuery);
                    updateStmt.setInt(1, banyakVoucherTransfer - 1);
                    updateStmt.setString(2, userId);
                    updateStmt.executeUpdate();

                    return true; // Voucher available and decremented
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false; // Voucher not available or error occurred
    }

    public static boolean useVoucherTopup(String userId) {
        conn.connect();
        try {
            String query = "SELECT banyak_voucher_topup_money FROM loyalty WHERE user_id = ?";
            PreparedStatement stmt = conn.con.prepareStatement(query);
            stmt.setString(1, userId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int banyakVoucherTopup = rs.getInt("banyak_voucher_topup_money");

                if (banyakVoucherTopup > 0) {
                    // Decrement the voucher count
                    String updateQuery = "UPDATE loyalty SET banyak_voucher_topup_money = ? WHERE user_id = ?";
                    PreparedStatement updateStmt = conn.con.prepareStatement(updateQuery);
                    updateStmt.setInt(1, banyakVoucherTopup - 1);
                    updateStmt.setString(2, userId);
                    updateStmt.executeUpdate();

                    return true; // Voucher available and decremented
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false; // Voucher not available or error occurred
    }

    public static boolean hasLoyaltyActive(String userId) {
        conn.connect();
        try {
            // Query to check if the user has an active loyalty
            String query = "SELECT loyalty_active FROM loyalty WHERE user_id = ?";
            PreparedStatement stmt = conn.con.prepareStatement(query);
            stmt.setString(1, userId);
            ResultSet rs = stmt.executeQuery();

            // If result set has a row, then the user has an active loyalty
            return rs.next(); // If there's at least one row, it means the user has an active loyalty
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false; // If no active loyalty found
    }

    public static void deleteExpiredLoyalty(String userId) {
        conn.connect();
        Timestamp now = new Timestamp(System.currentTimeMillis());

        try {
            // Query to get the expiration date of loyalty for the user
            String query = "SELECT expired_date FROM loyalty WHERE user_id = ?";
            PreparedStatement stmt = conn.con.prepareStatement(query);
            stmt.setString(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Timestamp expiredDate = rs.getTimestamp("expired_date");

                // If the current date is after the expiration date, delete the loyalty entry
                if (now.after(expiredDate)) {
                    String deleteQuery = "DELETE FROM loyalty WHERE user_id = ?";
                    PreparedStatement deleteStmt = conn.con.prepareStatement(deleteQuery);
                    deleteStmt.setString(1, userId);
                    deleteStmt.executeUpdate();
                    System.out.println("Expired loyalty entry deleted for user: " + userId);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Timestamp calculateEndDate(Timestamp startDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(startDate.getTime());
        calendar.add(Calendar.MONTH, 3);
        return new Timestamp(calendar.getTimeInMillis());
    }

    public static boolean buyLoyaltyByUserId(Loyalty loyalty) {
        conn.connect();
        try {
            // Generate loyalty_id
            String loyaltyId = generateLoyaltyId();
            if (loyaltyId == null) {
                JOptionPane.showMessageDialog(null, "Failed to generate Loyalty ID.", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
    
            // Check if the user already has an active loyalty
            String checkQuery = "SELECT COUNT(*) AS count FROM loyalty WHERE user_id = ? AND loyalty_active = true";
            PreparedStatement checkStmt = conn.con.prepareStatement(checkQuery);
            checkStmt.setString(1, loyalty.getUser_id());
            ResultSet rs = checkStmt.executeQuery();
    
            if (rs.next() && rs.getInt("count") > 0) {
                JOptionPane.showMessageDialog(null, "Loyalty is already active for this user.", "Info", JOptionPane.WARNING_MESSAGE);
                return false; // User already has an active loyalty
            }
    
            // Prepare insert query
            String query = "INSERT INTO loyalty (loyalty_id, user_id, loyalty_value, banyak_voucher_setor, banyak_voucher_transfer, banyak_voucher_topup_money, loyalty_active, expired_date) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.con.prepareStatement(query);
    
            // Calculate loyalty details
            double loyaltyPrice = 99000; // Price for loyalty
            Timestamp expiryDate = calculateEndDate(new Timestamp(System.currentTimeMillis()));
    
            stmt.setString(1, loyaltyId);
            stmt.setString(2, loyalty.getUser_id());
            stmt.setDouble(3, loyaltyPrice);
            stmt.setInt(4, loyalty.getBanyakVoucherSetor());
            stmt.setInt(5, loyalty.getBanyakVoucherTransfer());
            stmt.setInt(6, loyalty.getBanyakVoucherTopup());
            stmt.setBoolean(7, true); // Loyalty active
            stmt.setTimestamp(8, expiryDate);
    
            // Execute insert query
            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(null, "Successfully purchased loyalty!", "Success", JOptionPane.INFORMATION_MESSAGE);
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "An error occurred: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return false;
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

    public static boolean paymentLoyaltyCode(String userId){
        int response = JOptionPane.showConfirmDialog(
                                null,
                                "Apakah anda mau menggunakan voucher loyalty?",
                                "Konfirmasi Penggunaan Voucher",
                                JOptionPane.YES_NO_OPTION,
                                JOptionPane.QUESTION_MESSAGE);
        if (response == JOptionPane.YES_OPTION) {
            return true;
        }

        return false;
    }
    public static boolean getChecked(String userId){
        return true;
    }

}
