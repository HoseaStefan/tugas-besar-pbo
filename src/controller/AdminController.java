package controller;

import java.sql.*;

import model.TransaksiType;

public class AdminController {

    static DatabaseHandler conn = new DatabaseHandler();

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
}
