package controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Nasabah;
import model.UserType;

public class BlockNasabahController {

    static DatabaseHandler conn = new DatabaseHandler();

    public static List<Nasabah> getNasabahByUserId(String pilihStatus) {
        conn.connect(); // Pastikan metode ini menginisialisasi conn.con
        List<Nasabah> nasabahs = new ArrayList<>();
        String query;

        try {
            // Tentukan query berdasarkan pilihan status
            if ("ALL".equalsIgnoreCase(pilihStatus)) {
                query = "SELECT * FROM users"; // Semua laporan
            } else {
                query = "SELECT * FROM users WHERE status = ?";
            }

            try (PreparedStatement checkStmt = conn.con.prepareStatement(query)) {
                // Hanya set parameter jika query memiliki placeholder
                if (!"ALL".equalsIgnoreCase(pilihStatus)) {
                    checkStmt.setString(1, pilihStatus);
                }

                try (ResultSet rs = checkStmt.executeQuery()) {
                    // Iterasi melalui semua hasil
                    while (rs.next()) {
                        Nasabah nasabah = new Nasabah(
                                rs.getString("user_id"),
                                rs.getString("name"),
                                rs.getString("username"),
                                rs.getString("email"),
                                UserType.NASABAH,
                                rs.getString("full_name"),
                                rs.getInt("pin"),
                                rs.getInt("nomor_rekening"),
                                rs.getDouble("saldo"),
                                null,
                                rs.getString("status"));
                        nasabahs.add(nasabah);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return nasabahs;
    }

    public static boolean updateStatusNasabah(Nasabah nasabah, String selectedStatus) {
        conn.connect();

        try {
            String updateQuery = "UPDATE users SET status = ? WHERE user_id = ?";
            PreparedStatement stmt = conn.con.prepareStatement(updateQuery);
            stmt.setString(1, selectedStatus);
            stmt.setString(2, nasabah.getUser_id());

            // Eksekusi query
            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
            } else {
                return false;
            }

            return true;
        } catch (SQLException e) {
            System.out.println("Terjadi kesalahan saat memperbarui status report: " + e.getMessage());
            return false;
        }
    }

}
