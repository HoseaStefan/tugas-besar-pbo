package controller;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.BlueDeposito;
import model.DepositoType;
import model.StatusType;
import model.TopUpType;
import model.TransaksiType;

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

    public static boolean updateBlueDepositoSaldo(String userId, double newSaldoAwal, double nominal) {
        conn.connect();
    
        try {
            String query = "UPDATE blue_deposito SET saldo_awal = ? WHERE user_id = ?";
            PreparedStatement stmt = conn.con.prepareStatement(query);
    
            boolean transaksi = createTransaksi(TransaksiType.BLUEDEPOSIT, null, 0, nominal, userId, 0.0, 0,
                    null);
            if (!transaksi) {
                System.out.println("Create Transaksi gagal");
                return false;
            }

            stmt.setDouble(1, newSaldoAwal); 
            stmt.setString(2, userId); 
    
            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                boolean isTarikBerhasil = tarikSaldoDeposit(userId, nominal);
                if (!isTarikBerhasil) {
                    System.out.println("Gagal menambah saldo ke akun pengguna.");
                    return false;
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return false;
    }
    
    public static boolean tarikSaldoDeposit(String userId, double tarikSaldo) {
        conn.connect();
        try {
            String query = "UPDATE users SET saldo = saldo + ? WHERE user_id = ?";
            PreparedStatement stmt = conn.con.prepareStatement(query);
    
            stmt.setDouble(1, tarikSaldo);
            stmt.setString(2, userId);
    
            int rowsUpdated = stmt.executeUpdate();
    
            if (rowsUpdated > 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return false;
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

    public static Boolean createTransaksi(TransaksiType tipeTransaksi, String kodePromo,
            double saldoTerpotong, double saldoDitambah, String userId, Double biayaAdmin, int norekTujuan,
            TopUpType topUpType) {

        conn.connect(); 

        try {
            String transaksiId = java.util.UUID.randomUUID().toString();
            String query = "INSERT INTO transaksi (transaksi_id, nasabah_id, nomor_rekening_tujuan, transaksi_type, biaya_admin, transaksi_date, kode_promo, jumlah_saldo_terpotong, jumlah_saldo_ditambah, status_type, topup_type) "
                    + "VALUES (?, ?, ?, ?, ?, NOW(), ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.con.prepareStatement(query);

            // Set parameter untuk query
            stmt.setString(1, transaksiId);
            stmt.setString(2, userId);
            stmt.setInt(3, norekTujuan);
            stmt.setString(4, tipeTransaksi.name());
            stmt.setDouble(5, biayaAdmin);
            stmt.setString(6, kodePromo != null ? kodePromo : "");
            stmt.setDouble(7, saldoTerpotong);
            stmt.setDouble(8, saldoDitambah);
            stmt.setString(9, StatusType.BERHASIL.name());
            stmt.setString(10, topUpType != null ? topUpType.name() : null);

            int rows = stmt.executeUpdate();
            conn.con.setAutoCommit(false);

            if (rows > 0) {
                return true;
            } else {
                return false;
            }

        } catch (SQLException e) {
            System.out.println("Error executing update: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public static double getTotalDanaByUserId(String userId) {
        conn.connect();
        double totalDana = 0.0;

        try {
            String query = "SELECT saldo_awal FROM blue_deposito WHERE user_id = ?";
            PreparedStatement stmt = conn.con.prepareStatement(query);
            stmt.setString(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                totalDana += rs.getDouble("saldo_awal");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return totalDana;
    }
}
