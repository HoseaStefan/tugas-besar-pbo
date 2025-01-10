package controller;

import model.BlueSaving;
import model.StatusType;
import model.TabunganType;
import model.TopUpType;
import model.TransaksiType;

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
            String query = "SELECT * FROM bluesaving WHERE nasabah_id = ?";
            PreparedStatement stmt = conn.con.prepareStatement(query);
            stmt.setString(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                BlueSaving blueSaving = new BlueSaving(
                        rs.getString("tabungan_id"),
                        rs.getString("nasabah_id"),
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
            String query = "SELECT saldoSaving FROM bluesaving WHERE nasabah_id = ?";
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

    public static boolean cekSaldoUser(String userId, double nominal) {

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
                JOptionPane.showMessageDialog(null, "User tidak ditemukan.", "BlueSaving", JOptionPane.ERROR_MESSAGE);
                return false; // User tidak ditemukan
            }

            double saldoUser = rs.getDouble("saldo");
            if (saldoUser < nominal) {
                conn.con.rollback();
                JOptionPane.showMessageDialog(null, "Saldo user tidak cukup.", "BlueSaving", JOptionPane.ERROR_MESSAGE);
                return false; // Saldo tidak cukup
            }

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

    public static boolean pindahSaldo(String userId, double nominal, BlueSaving blueSaving) {

        conn.connect();

        try {
            conn.con.setAutoCommit(false); // Mulai transaksi

            if (nominal != 0) {
                boolean transaksi = createTransaksi(TransaksiType.BLUESAVING, null, nominal, 0, blueSaving, 0.0, 0,
                        null);
                if (!transaksi) {
                    System.out.println("Create Transaksi gagal");
                    return false;
                }
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
            updateBlueSavingStmt.setString(2, blueSaving.getTabungan_id());
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

    public static boolean cekSaldoSaving(BlueSaving blueSaving, double nominal) {

        conn.connect();
        try {
            conn.con.setAutoCommit(false); // Mulai transaksi

            // Periksa saldo BlueSaving
            String cekSaldoSavingQuery = "SELECT saldoSaving FROM bluesaving WHERE tabungan_id = ?";
            PreparedStatement cekSaldoSavingStmt = conn.con.prepareStatement(cekSaldoSavingQuery);
            cekSaldoSavingStmt.setString(1, blueSaving.getTabungan_id());
            ResultSet rs = cekSaldoSavingStmt.executeQuery();

            if (!rs.next()) {
                conn.con.rollback();
                JOptionPane.showMessageDialog(null, "bluesaving tidak ditemukan.", "BlueSaving",
                        JOptionPane.ERROR_MESSAGE);
                return false; // BlueSaving tidak ditemukan
            }

            double saldoSaving = rs.getDouble("saldoSaving");
            if (saldoSaving < nominal) {
                conn.con.rollback();
                JOptionPane.showMessageDialog(null, "Saldo bluesaving tidak cukup.", "BlueSaving",
                        JOptionPane.ERROR_MESSAGE);
                return false; // Saldo BlueSaving tidak cukup
            }

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

    public static boolean tarikSaldo(String userId, double nominal, BlueSaving blueSaving) {
        conn.connect();

        try {
            conn.con.setAutoCommit(false); // Mulai transaksi

            if (nominal != 0) {
                boolean transaksi = createTransaksi(TransaksiType.BLUESAVING, null, 0, nominal, blueSaving, 0.0, 0,
                        null);
                if (!transaksi) {
                    System.out.println("Create Transaksi gagal");
                    return false;
                }
            }

            // Kurangi saldo BlueSaving
            String updateBlueSavingQuery = "UPDATE bluesaving SET saldoSaving = saldoSaving - ? WHERE tabungan_id = ?";
            PreparedStatement updateBlueSavingStmt = conn.con.prepareStatement(updateBlueSavingQuery);
            updateBlueSavingStmt.setDouble(1, nominal);
            updateBlueSavingStmt.setString(2, blueSaving.getTabungan_id());
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
            String query = "SELECT COUNT(*) FROM bluesaving WHERE nasabah_id = ? AND namaTabungan = ?";
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

    public static Boolean createTransaksi(TransaksiType tipeTransaksi, String kodePromo,
            double saldoTerpotong, double saldoDitambah, BlueSaving blueSaving, Double biayaAdmin, int norekTujuan,
            TopUpType topUpType) {

        conn.connect(); // Memastikan koneksi berhasil

        try {
            String transaksiId = java.util.UUID.randomUUID().toString();
            String query = "INSERT INTO transaksi (transaksi_id, nasabah_id, nomor_rekening_tujuan, transaksi_type, biaya_admin, transaksi_date, kode_promo, jumlah_saldo_terpotong, jumlah_saldo_ditambah, status_type, topup_type) "
                    + "VALUES (?, ?, ?, ?, ?, NOW(), ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.con.prepareStatement(query);

            // Set parameter untuk query
            stmt.setString(1, transaksiId);
            stmt.setString(2, blueSaving.getuser_id());
            stmt.setInt(3, norekTujuan);
            stmt.setString(4, tipeTransaksi.name());
            stmt.setDouble(5, biayaAdmin);
            stmt.setString(6, kodePromo != null ? kodePromo : "");
            stmt.setDouble(7, saldoTerpotong);
            stmt.setDouble(8, saldoDitambah);
            stmt.setString(9, StatusType.BERHASIL.name());
            stmt.setString(10, topUpType != null ? topUpType.name() : null);

            // Eksekusi query
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

}
