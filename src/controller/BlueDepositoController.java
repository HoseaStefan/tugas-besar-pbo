package controller;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.BlueDeposito;
import model.DepositoType;

public class BlueDepositoController {
    static DatabaseHandler conn = new DatabaseHandler();

    // Retrieve all deposits for a specific user
    public static List<BlueDeposito> getDepositsByUserId(String userId) {
        conn.connect();
        List<BlueDeposito> deposits = new ArrayList<>();

        try {
            String query = "SELECT * FROM blue_deposito WHERE user_id = ?";
            PreparedStatement stmt = conn.con.prepareStatement(query);
            stmt.setString(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                BlueDeposito deposito = new BlueDeposito(
                        rs.getString("tabungan_id"),
                        rs.getString("user_id"),
                        rs.getString("nama_tabungan"), // Mengambil nama tabungan
                        null, // Parsing enum untuk tabungan_type
                        rs.getDouble("saldo_awal"),
                        rs.getTimestamp("start_date"), // Parsing enum untuk deposito_type
                        DepositoType.valueOf(rs.getString("deposito_type").toUpperCase()),
                        rs.getDouble("saldo_akhir"),
                        rs.getTimestamp("end_date"),
                        rs.getBoolean("complete"));

                deposits.add(deposito);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return deposits;
    }

    public static double getSaldoAwalDepo(String user_id) {
        // Pastikan koneksi ke database telah terhubung
        conn.connect(); // Asumsi `conn` adalah objek koneksi ke database
        try {
            // Periksa apakah user memiliki Blue Deposito
            if (hasBlueDeposito(user_id) == true) {
                // Query SQL untuk mendapatkan saldo_awal berdasarkan user_id
                String query = "SELECT saldo_awal FROM blue_deposito WHERE user_id = ? LIMIT 1";
    
                // Siapkan pernyataan untuk eksekusi query
                PreparedStatement stmt = conn.con.prepareStatement(query);
    
                // Set parameter user_id dalam query
                stmt.setString(1, user_id);
    
                // Eksekusi query
                ResultSet rs = stmt.executeQuery();
    
                // Jika data ditemukan, ambil saldo_awal
                if (rs.next()) {
                    return rs.getDouble("saldo_awal");
                }
    
                // Tutup ResultSet dan PreparedStatement
                rs.close();
                stmt.close();
            } else {
                System.out.println("Blue Deposito tidak ditemukan untuk user_id: " + user_id);
            }
        } catch (Exception e) {
            // Cetak error jika terjadi masalah
            e.printStackTrace();
        }
        // Kembalikan 0 jika tidak ditemukan atau terjadi kesalahan
        return 0;
    }
    

    public static boolean hasBlueDeposito(String userId) {
        conn.connect();

        try {
            String query = "SELECT COUNT(*) FROM blue_deposito WHERE user_id = ? AND complete = false";
            PreparedStatement stmt = conn.con.prepareStatement(query);
            stmt.setString(1, userId);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0; // Mengembalikan true jika ada Blue Deposito yang belum selesai
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public static void deleteBlueDeposito(String userId) {
        conn.connect();
        try {
            String query = "DELETE FROM blue_deposito WHERE user_id = ?";

            PreparedStatement stmt = conn.con.prepareStatement(query);

            stmt.setString(1, userId);
            
            int rowsDeleted = stmt.executeUpdate();
            
            if (rowsDeleted > 0) {
                System.out.println("success");
            } else {
                System.out.println("gagal");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    
    public static boolean updateBlueDepositoSaldo(String userId, double newSaldoAwal) {
        conn.connect();
    
        try {
            String query = "UPDATE blue_deposito SET saldo_awal = ? WHERE user_id = ?";
            
            // Buat koneksi ke database dan persiapkan statement
            
            PreparedStatement stmt = conn.con.prepareStatement(query);
            
            stmt.setDouble(1, newSaldoAwal);  // Set nilai saldo baru
            stmt.setString(2, userId);        // Set user_id berdasarkan input
            
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;  // Kembalikan true jika update berhasil
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;  // Jika terjadi error, kembalikan false
        }
    }


    public boolean updateCompleteStatus(String userId, String tabunganId) {
        conn.connect();
        try {
            String updateQuery = "UPDATE blue_deposito SET complete = ? WHERE user_id = ? AND tabungan_id = ?";
            PreparedStatement stmt = conn.con.prepareStatement(updateQuery);

            stmt.setBoolean(1, true);
            stmt.setString(2, tabunganId);
            stmt.setString(3, userId);

            int rowsUpdated = stmt.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Status complete berhasil diperbarui untuk tabungan_id: " + tabunganId);
                return true;
            } else {
                System.out.println("Gagal memperbarui status complete untuk tabungan_id: " + tabunganId);
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

}
