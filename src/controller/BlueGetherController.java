package controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import model.BlueGether;
import model.StatusType;
import model.TabunganType;
import model.TopUpType;
import model.TransaksiType;

public class BlueGetherController {
    static DatabaseHandler conn = new DatabaseHandler();

    public static List<BlueGether> getBlueGethersByUserId(String userId) {
        conn.connect(); // Pastikan ini menginisialisasi koneksi database
        List<BlueGether> blueGethers = new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            String query = "SELECT bg.tabungan_id, bg.nasabah_id, bg.namaTabungan, bg.saldoAwal, bg.dateCreated, bg.saldoGether, bg.jangkaWaktu, bg.targetSaldo, bg.tabunganHarian "
                    + "FROM bluegether bg "
                    + "LEFT JOIN listnasabah ln ON bg.tabungan_id = ln.tabungan_id "
                    + "WHERE bg.nasabah_id = ? OR ln.nasabah_id = ?"
                    + "GROUP BY bg.tabungan_id;";

            stmt = conn.con.prepareStatement(query);
            stmt.setString(1, userId);
            stmt.setString(2, userId);
            rs = stmt.executeQuery();

            while (rs.next()) {
                // Mengambil data dari ResultSet dan membuat objek BlueGether
                BlueGether blueGether = new BlueGether(
                        rs.getString("tabungan_id"),
                        rs.getString("nasabah_id"),
                        rs.getString("namaTabungan"),
                        TabunganType.BLUEGETHER, // Sesuaikan jika jenis tabungan tetap
                        rs.getDouble("saldoAwal"),
                        rs.getTimestamp("dateCreated"),
                        rs.getDouble("saldoGether"),
                        rs.getInt("jangkaWaktu"),
                        rs.getDouble("targetSaldo"),
                        rs.getDouble("tabunganHarian"),
                        new ArrayList<>() // Daftar nasabah dapat diisi jika diperlukan
                );

                blueGethers.add(blueGether);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (stmt != null)
                    stmt.close();
                if (conn.con != null)
                    conn.con.close(); // Tutup koneksi database
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return blueGethers;
    }

    public static boolean cekNomorRekeningDiDatabase(int nomorRekening) {
        conn.connect(); // Pastikan conn.connect() menginisialisasi koneksi ke database dengan benar

        String query = "SELECT COUNT(*) AS jumlah FROM users WHERE nomor_rekening = ?";

        try (PreparedStatement cekRekeningUser = conn.con.prepareStatement(query)) {
            cekRekeningUser.setInt(1, nomorRekening);

            try (ResultSet rs = cekRekeningUser.executeQuery()) {
                if (rs.next() && rs.getInt("jumlah") > 0) {
                    return true; // Nomor rekening ditemukan
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false; // Nomor rekening tidak ditemukan
    }

    public static boolean tambahNasabahToListNasabah(String tabunganId, String userId) {
        conn.connect();
        try {
            String query = "INSERT INTO listnasabah (tabungan_id, nasabah_id) VALUES (?, ?)";
            PreparedStatement stmt = conn.con.prepareStatement(query);
            stmt.setString(1, tabunganId);
            stmt.setString(2, userId);

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0; // Mengembalikan true jika berhasil ditambahkan
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static String getUserIdNasabah(int noRekening) {
        conn.connect();
        try {
            String query = "SELECT user_id FROM users WHERE nomor_rekening = ?";
            PreparedStatement stmt = conn.con.prepareStatement(query);
            stmt.setInt(1, noRekening);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String userId = rs.getString("user_id");
                return userId;
            } else {
                JOptionPane.showMessageDialog(null, "User tidak ditemukan di sistem.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return null; // Nomor rekening tidak ditemukan
            }
        } catch (SQLException e) {
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    public static double getTotalDanaGetherByUserId(String userId) {
        conn.connect();
        double totalDana = 0.0;

        try {
            String query = "SELECT saldoGether FROM bluegether WHERE nasabah_id = ?";
            PreparedStatement stmt = conn.con.prepareStatement(query);
            stmt.setString(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                totalDana += rs.getDouble("saldoGether");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return totalDana;
    }

    public static boolean cekOwnerBlueGether(String tabungan_id, String nasabah_id) {
        conn.connect();
        String query = "SELECT nasabah_id FROM bluegether WHERE tabungan_id = ?";

        try (PreparedStatement stmt = conn.con.prepareStatement(query)) {
            stmt.setString(1, tabungan_id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String userId = rs.getString("nasabah_id");
                    return userId.equals(nasabah_id);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
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

    public static boolean pindahSaldoGether(String userId, double nominal, BlueGether blueGether) {

        conn.connect();

        try {
            conn.con.setAutoCommit(false); // Mulai transaksi

            if (nominal != 0) {
                boolean transaksi = createTransaksi(TransaksiType.BLUEGETHER, null, nominal, 0, userId, 0.0, 0,
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
            String updateBlueGetherQuery = "UPDATE bluegether SET saldoGether = saldoGether + ? WHERE tabungan_id = ?";
            PreparedStatement updateBlueGetherStmt = conn.con.prepareStatement(updateBlueGetherQuery);
            updateBlueGetherStmt.setDouble(1, nominal);
            updateBlueGetherStmt.setString(2, blueGether.getTabungan_id());
            updateBlueGetherStmt.executeUpdate();

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

    public static boolean cekSaldoGether(double nominal, BlueGether blueGether) {

        conn.connect();

        try {
            conn.con.setAutoCommit(false); // Mulai transaksi

            // Periksa saldo BlueSaving
            String cekSaldoGetherQuery = "SELECT saldoGether FROM bluegether WHERE tabungan_id = ?";
            PreparedStatement cekSaldoGetherStmt = conn.con.prepareStatement(cekSaldoGetherQuery);
            cekSaldoGetherStmt.setString(1, blueGether.getTabungan_id());
            ResultSet rs = cekSaldoGetherStmt.executeQuery();

            if (!rs.next()) {
                conn.con.rollback();
                JOptionPane.showMessageDialog(null, "bluegether tidak ditemukan.", "BlueSaving",
                        JOptionPane.ERROR_MESSAGE);
                return false; // tidak ditemukan
            }

            double saldoGether = rs.getDouble("saldoGether");
            if (saldoGether < nominal) {
                conn.con.rollback();
                JOptionPane.showMessageDialog(null, "Saldo bluegether tidak cukup.", "BlueSaving",
                        JOptionPane.ERROR_MESSAGE);
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

    public static boolean tarikSaldoGether(String userId, double nominal, BlueGether blueGether) {
        conn.connect();

        try {
            conn.con.setAutoCommit(false); // Mulai transaksi

            // Periksa saldo BlueSaving
            String cekSaldoGetherQuery = "SELECT saldoGether FROM bluegether WHERE tabungan_id = ?";
            PreparedStatement cekSaldoGetherStmt = conn.con.prepareStatement(cekSaldoGetherQuery);
            cekSaldoGetherStmt.setString(1, blueGether.getTabungan_id());
            ResultSet rs = cekSaldoGetherStmt.executeQuery();

            if (!rs.next()) {
                conn.con.rollback();
                return false; // tidak ditemukan
            }

            double saldoGether = rs.getDouble("saldoGether");
            if (saldoGether < nominal) {
                conn.con.rollback();
                return false; // Saldo tidak cukup
            }

            if (nominal != 0) {
                boolean transaksi = createTransaksi(TransaksiType.BLUEGETHER, null, 0, nominal, userId, 0.0, 0,
                        null);
                if (!transaksi) {
                    System.out.println("Create Transaksi gagal");
                    return false;
                }
            }

            // Kurangi saldo BlueGether
            String updateBlueGetherQuery = "UPDATE bluegether SET saldoGether = saldoGether - ? WHERE tabungan_id = ?";
            PreparedStatement updateBlueGetherStmt = conn.con.prepareStatement(updateBlueGetherQuery);
            updateBlueGetherStmt.setDouble(1, nominal);
            updateBlueGetherStmt.setString(2, blueGether.getTabungan_id());
            updateBlueGetherStmt.executeUpdate();

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

    public static void updateBlueGetherInDatabase(BlueGether blueGether) {
        try {
            conn.connect(); // Pastikan koneksi aktif

            // Hitung tabungan harian
            double tabunganHarian = BlueGether.hitungTabunganHarian(blueGether.getSaldoGether(),
                    blueGether.getTargetSaldo(), blueGether.getJangkaWaktu());
            blueGether.setTabunganHarian(tabunganHarian); // Set nilai tabungan harian ke objek

            // Query untuk memperbarui targetSaldo, jangkaWaktu, dan tabunganHarian
            String query = "UPDATE bluegether SET targetSaldo = ?, jangkaWaktu = ?, tabunganHarian = ? WHERE tabungan_id = ?";
            PreparedStatement stmt = conn.con.prepareStatement(query);
            stmt.setDouble(1, blueGether.getTargetSaldo()); // Update targetSaldo
            stmt.setInt(2, blueGether.getJangkaWaktu()); // Update jangkaWaktu
            stmt.setDouble(3, blueGether.getTabunganHarian()); // Update tabunganHarian
            stmt.setString(4, blueGether.getTabungan_id()); // Kondisi berdasarkan tabungan_id

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

    public static boolean isNamaTabunganAlreadyExist(String userId, String newName) {
        conn.connect();
        try {
            // Query untuk mengecek apakah nama tabungan sudah ada untuk user ini
            String query = "SELECT COUNT(*) FROM bluegether WHERE nasabah_id = ? AND namaTabungan = ?";
            PreparedStatement stmt = conn.con.prepareStatement(query);
            stmt.setString(1, userId); // Set nasabah_id
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

    public static boolean gantiNamaBlueGether(BlueGether blueGether, String userId) {
        conn.connect();

        try {
            // Cek apakah nama tabungan baru sudah ada untuk user yang sama
            if (isNamaTabunganAlreadyExist(userId, blueGether.getNamaTabungan())) {
                JOptionPane.showMessageDialog(null, "Nama tabungan sudah digunakan, pilih nama lain.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return false; // Jika nama sudah ada, tidak lanjutkan proses penggantian nama
            }

            // Query untuk memperbarui nama tabungan
            String updateQuery = "UPDATE bluegether SET namaTabungan = ? WHERE tabungan_id = ?";
            PreparedStatement stmt = conn.con.prepareStatement(updateQuery);
            stmt.setString(1, blueGether.getNamaTabungan()); // Set nama tabungan baru
            stmt.setString(2, blueGether.getTabungan_id()); // Kondisi berdasarkan tabungan_id

            // Eksekusi query
            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Nama Blue Gether berhasil diperbarui!");
            } else {
                System.out.println("Gagal memperbarui nama Blue Gether.");
                return false;
            }

            return true;
        } catch (SQLException e) {
            System.out.println("Terjadi kesalahan saat memperbarui nama Blue Gether: " + e.getMessage());
            return false;
        }
    }

    public static boolean tutupBlueGether(BlueGether blueGether) {
        conn.connect();

        try {
            // Hapus Blue Gether dari database berdasarkan tabungan_id
            String query = "DELETE FROM bluegether WHERE tabungan_id = ?";
            PreparedStatement stmt = conn.con.prepareStatement(query);
            stmt.setString(1, blueGether.getTabungan_id());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Blue Gether berhasil ditutup.");
                return true;
            } else {
                System.out.println("Gagal menutup Blue Gether.");
                return false;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public static Boolean createTransaksi(TransaksiType tipeTransaksi, String kodePromo,
            double saldoTerpotong, double saldoDitambah, String userId, Double biayaAdmin, int norekTujuan,
            TopUpType topUpType) {

        conn.connect(); // Memastikan koneksi berhasil

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
