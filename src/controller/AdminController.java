package controller;

import java.sql.*;
import javax.swing.table.DefaultTableModel;
import model.TransaksiType;

public class AdminController {

    static DatabaseHandler conn = new DatabaseHandler();

    public static boolean deletePromoCode(String promoCode) {
        String query = "DELETE FROM promo WHERE kode_promo = ?";
        try {
            conn.connect();

            PreparedStatement stmt = conn.con.prepareStatement(query);
            stmt.setString(1, promoCode);
            int rowsAffected = stmt.executeUpdate();

            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return false;
    }

    public static void loadPromoCodesForDeletion(DefaultTableModel tableModel) {
        String query = "SELECT kode_promo, promo_type, potongan_value FROM promo";
        try {
            conn.connect();

            PreparedStatement stmt = conn.con.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String promoCode = rs.getString("kode_promo");
                String promoType = rs.getString("promo_type");
                double discountValue = rs.getDouble("potongan_value");
                tableModel.addRow(new Object[] { promoCode, promoType, discountValue });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
    }

    public static boolean checkPromoCodeExists(String promoCode) {
        String query = "SELECT COUNT(*) FROM promo WHERE kode_promo = ?";
        try {
            conn.connect();

            PreparedStatement stmt = conn.con.prepareStatement(query);
            stmt.setString(1, promoCode);
            ResultSet rs = stmt.executeQuery();

            if (rs.next() && rs.getInt(1) > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return false;
    }

    public static boolean insertPromoCode(String promoCode, String promoType, double discountValue) {
        String query = "INSERT INTO promo (promo_id, kode_promo, promo_type, potongan_value) VALUES (?, ?, ?, ?)";
        try {
            conn.connect();

            PreparedStatement stmt = conn.con.prepareStatement(query);
            stmt.setString(1, "ID-" + promoCode);
            stmt.setString(2, promoCode);
            stmt.setString(3, promoType);
            stmt.setDouble(4, discountValue);
            int rowsAffected = stmt.executeUpdate();

            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return false;
    }

    public static double pendapatanAdminTransaksi(TransaksiType transaksiType) {
        double totalAdminFee = 0.0;

        try {
            conn.connect();

            String query = "SELECT SUM(biaya_admin) AS total_biaya_admin FROM transaksi WHERE transaksi_type = ?";
            PreparedStatement stmt = conn.con.prepareStatement(query);
            stmt.setString(1, transaksiType.toString());

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                totalAdminFee = rs.getDouble("total_biaya_admin");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }

        return totalAdminFee;
    }

    public static double pendapatanAdminLoyalty() {
        conn.connect(); 
        double pendapatan = 0;
    
        try {
            String query = "SELECT COUNT(*) AS total FROM loyalty";
            PreparedStatement stmt = conn.con.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
    
            if (rs.next()) {
                int totalRows = rs.getInt("total");
                pendapatan = totalRows * 99000; 
            }
    
            rs.close();
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect(); 
        }
    
        return pendapatan;
    }
    
}
