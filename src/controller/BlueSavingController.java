package controller;

import model.BlueSaving;
import model.TabunganType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

public class BlueSavingController {
    static DatabaseHandler conn = new DatabaseHandler();

    public static List<BlueSaving> getBlueSavingsByUserId(String userId) {
        conn.connect();
        List<BlueSaving> blueSavings = new ArrayList<>();

        try {
            String query = "SELECT * FROM bluesaving WHERE user_id = ?";
            PreparedStatement stmt = conn.con.prepareStatement(query);
            stmt.setString(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                BlueSaving blueSaving = new BlueSaving(
                        rs.getString("tabungan_id"),
                        rs.getString("user_id"),
                        rs.getString("namaTabungan"),
                        TabunganType.BLUESAVING, // TabunganType jika diperlukan, sesuaikan dengan model Anda
                        rs.getDouble("saldoAwal"),
                        rs.getTimestamp("dateCreated"),
                        rs.getDouble("saldoSaving"),
                        rs.getInt("jangkaWaktu"),
                        rs.getDouble("targetSaldo"),
                        rs.getDouble("tabunganHarian"));

                blueSavings.add(blueSaving);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return blueSavings;
    }

    public static double getTotalDanaByUserId(String userId) {
        conn.connect();
        double totalDana = 0.0;

        try {
            String query = "SELECT saldoSaving FROM bluesaving WHERE user_id = ?";
            PreparedStatement stmt = conn.con.prepareStatement(query);
            stmt.setString(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                totalDana += rs.getDouble("saldoSaving");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return totalDana;
    }

    public static void updateBlueSavingInDatabase(BlueSaving blueSaving) {
        try {
            conn.connect(); // Pastikan koneksi aktif

            // Hitung tabungan harian
            double tabunganHarian = BlueSaving.hitungTabunganHarian(blueSaving.getSaldoSaving(),
                    blueSaving.getTargetSaldo(), blueSaving.getJangkaWaktu());
            blueSaving.setTabunganHarian(tabunganHarian); // Set nilai tabungan harian ke objek

            // Query untuk memperbarui targetSaldo, jangkaWaktu, dan tabunganHarian
            String query = "UPDATE bluesaving SET targetSaldo = ?, jangkaWaktu = ?, tabunganHarian = ? WHERE tabungan_id = ?";
            PreparedStatement stmt = conn.con.prepareStatement(query);
            stmt.setDouble(1, blueSaving.getTargetSaldo()); // Update targetSaldo
            stmt.setInt(2, blueSaving.getJangkaWaktu()); // Update jangkaWaktu
            stmt.setDouble(3, blueSaving.getTabunganHarian()); // Update tabunganHarian
            stmt.setString(4, blueSaving.getTabungan_id()); // Kondisi berdasarkan tabungan_id

            // Eksekusi update
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Data berhasil diperbarui.");
            } else {
                System.out.println("Tidak ada data yang diperbarui. Periksa tabungan_id.");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static boolean pindahSaldo(String userId, double nominal, String tabunganId) {

        conn.connect();

        try {
            conn.con.setAutoCommit(false); // Mulai transaksi

            // Periksa saldo pengguna
            String cekSaldoQuery = "SELECT saldo FROM users WHERE user_id = ?";
            PreparedStatement cekSaldoStmt = conn.con.prepareStatement(cekSaldoQuery);
            cekSaldoStmt.setString(1, userId);
            ResultSet rs = cekSaldoStmt.executeQuery();

            if (!rs.next()) {
                conn.con.rollback();
                return false; // User tidak ditemukan
            }

            double saldoUser = rs.getDouble("saldo");
            if (saldoUser < nominal) {
                conn.con.rollback();
                return false; // Saldo tidak cukup
            }

            // Kurangi saldo pengguna
            String updateUserSaldoQuery = "UPDATE users SET saldo = saldo - ? WHERE user_id = ?";
            PreparedStatement updateUserSaldoStmt = conn.con.prepareStatement(updateUserSaldoQuery);
            updateUserSaldoStmt.setDouble(1, nominal);
            updateUserSaldoStmt.setString(2, userId);
            updateUserSaldoStmt.executeUpdate();

            // Tambahkan saldo ke BlueSaving
            String updateBlueSavingQuery = "UPDATE bluesaving SET saldoSaving = saldoSaving + ? WHERE tabungan_id = ?";
            PreparedStatement updateBlueSavingStmt = conn.con.prepareStatement(updateBlueSavingQuery);
            updateBlueSavingStmt.setDouble(1, nominal);
            updateBlueSavingStmt.setString(2, tabunganId);
            updateBlueSavingStmt.executeUpdate();

            conn.con.commit(); // Commit transaksi
            return true;

        } catch (SQLException ex) {
            try {
                conn.con.rollback(); // Rollback jika terjadi kesalahan
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            ex.printStackTrace();
            return false;
        }
    }

    public static boolean tarikSaldo(String userId, double nominal, String tabunganId) {
        conn.connect();

        try {
            conn.con.setAutoCommit(false); // Mulai transaksi

            // Periksa saldo BlueSaving
            String cekSaldoSavingQuery = "SELECT saldoSaving FROM bluesaving WHERE tabungan_id = ?";
            PreparedStatement cekSaldoSavingStmt = conn.con.prepareStatement(cekSaldoSavingQuery);
            cekSaldoSavingStmt.setString(1, tabunganId);
            ResultSet rs = cekSaldoSavingStmt.executeQuery();

            if (!rs.next()) {
                conn.con.rollback();
                return false; // BlueSaving tidak ditemukan
            }

            double saldoSaving = rs.getDouble("saldoSaving");
            if (saldoSaving < nominal) {
                conn.con.rollback();
                return false; // Saldo BlueSaving tidak cukup
            }

            // Kurangi saldo BlueSaving
            String updateBlueSavingQuery = "UPDATE bluesaving SET saldoSaving = saldoSaving - ? WHERE tabungan_id = ?";
            PreparedStatement updateBlueSavingStmt = conn.con.prepareStatement(updateBlueSavingQuery);
            updateBlueSavingStmt.setDouble(1, nominal);
            updateBlueSavingStmt.setString(2, tabunganId);
            updateBlueSavingStmt.executeUpdate();

            // Tambahkan saldo ke pengguna
            String updateUserSaldoQuery = "UPDATE users SET saldo = saldo + ? WHERE user_id = ?";
            PreparedStatement updateUserSaldoStmt = conn.con.prepareStatement(updateUserSaldoQuery);
            updateUserSaldoStmt.setDouble(1, nominal);
            updateUserSaldoStmt.setString(2, userId);
            updateUserSaldoStmt.executeUpdate();

            conn.con.commit(); // Commit transaksi
            return true;

        } catch (SQLException ex) {
            try {
                conn.con.rollback(); // Rollback jika terjadi kesalahan
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            ex.printStackTrace();
            return false;
        }
    }

    public static boolean isNamaTabunganAlreadyExist(String userId, String newName) {
        conn.connect();
        try {
            // Query untuk mengecek apakah nama tabungan sudah ada untuk user ini
            String query = "SELECT COUNT(*) FROM bluesaving WHERE user_id = ? AND namaTabungan = ?";
            PreparedStatement stmt = conn.con.prepareStatement(query);
            stmt.setString(1, userId); // Set user_id
            stmt.setString(2, newName); // Set nama tabungan baru

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0; // Jika count > 0, berarti sudah ada nama yang sama
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean gantiNamaBlueSaving(BlueSaving blueSaving, String userId) {
        conn.connect();

        try {
            // Cek apakah nama tabungan baru sudah ada untuk user yang sama
            if (isNamaTabunganAlreadyExist(userId, blueSaving.getNamaTabungan())) {
                JOptionPane.showMessageDialog(null, "Nama tabungan sudah digunakan, pilih nama lain.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return false; // Jika nama sudah ada, tidak lanjutkan proses penggantian nama
            }

            // Query untuk memperbarui nama tabungan
            String updateQuery = "UPDATE bluesaving SET namaTabungan = ? WHERE tabungan_id = ?";
            PreparedStatement stmt = conn.con.prepareStatement(updateQuery);
            stmt.setString(1, blueSaving.getNamaTabungan()); // Set nama tabungan baru
            stmt.setString(2, blueSaving.getTabungan_id()); // Kondisi berdasarkan tabungan_id

            // Eksekusi query
            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Nama Blue Saving berhasil diperbarui!");
            } else {
                System.out.println("Gagal memperbarui nama Blue Saving.");
                return false;
            }

            return true;
        } catch (SQLException e) {
            System.out.println("Terjadi kesalahan saat memperbarui nama Blue Saving: " + e.getMessage());
            return false;
        }
    }

    public static boolean tutupBlueSaving(BlueSaving blueSaving) {
        conn.connect();

        try {
            // Hapus Blue Saving dari database berdasarkan tabungan_id
            String query = "DELETE FROM bluesaving WHERE tabungan_id = ?";
            PreparedStatement stmt = conn.con.prepareStatement(query);
            stmt.setString(1, blueSaving.getTabungan_id());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Blue Saving berhasil ditutup.");
                return true;
            } else {
                System.out.println("Gagal menutup Blue Saving.");
                return false;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

}
