package controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.*;

public class TransaksiController {

    static DatabaseHandler conn = new DatabaseHandler();

    public static Boolean verifyNomorRekeningTujuan(int rekening) {
        try {
            conn.connect();
            String query = "SELECT * FROM users WHERE nomor_rekening = ?";
            PreparedStatement stmt = conn.con.prepareStatement(query);
            stmt.setInt(1, rekening);

            ResultSet rs = stmt.executeQuery();
            System.out.println(rs.next());
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static Boolean createTransaksi(TransaksiType tipeTransaksi, double amount, String kodePromo, Nasabah nasabah,
            int norekTujuan, Double biayaAdmin, TopUpType topUpType) {
        try {
            conn.connect();
            conn.con.setAutoCommit(false); // Disable auto-commit

            double saldoDitambah = 0;
            double saldoTerpotong = 0;

            if (kodePromo != null && !kodePromo.isEmpty()) {
                biayaAdmin = 0.0;
            }

            if (tipeTransaksi == TransaksiType.SETOR) {
                saldoDitambah = amount - biayaAdmin;
            } else if (tipeTransaksi == TransaksiType.TOPUP) {
                saldoTerpotong = amount + biayaAdmin;
            } else if (tipeTransaksi == TransaksiType.TRANSFER) {
                saldoTerpotong = amount + biayaAdmin;
                saldoDitambah = amount;
            }

            String transaksiId = java.util.UUID.randomUUID().toString();

            String query = "INSERT INTO transaksi (transaksi_id, nasabah_id, nomor_rekening_tujuan, transaksi_type, biaya_admin, transaksi_date, kode_promo, jumlah_saldo_terpotong, jumlah_saldo_ditambah, status_type, topup_type) VALUES (?, ?, ?, ?, ?, NOW(), ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.con.prepareStatement(query);

            stmt.setString(1, transaksiId);
            stmt.setString(2, nasabah.getUser_id());
            stmt.setInt(3, norekTujuan);
            stmt.setString(4, tipeTransaksi.name());
            stmt.setDouble(5, biayaAdmin);
            stmt.setString(6, kodePromo);
            stmt.setDouble(7, saldoTerpotong);
            stmt.setDouble(8, saldoDitambah);
            stmt.setString(9, StatusType.BERHASIL.name());
            stmt.setString(10, topUpType != null ? topUpType.name() : null);

            int rows = stmt.executeUpdate();
            System.out.println("Inserted transaksi rows: " + rows);

            if (rows > 0) {
                String updateQuery = "UPDATE users SET saldo = saldo + ? WHERE user_id = ?";
                PreparedStatement updateStmt = conn.con.prepareStatement(updateQuery);

                if (tipeTransaksi == TransaksiType.SETOR) {
                    updateStmt.setDouble(1, saldoDitambah);
                } else if (tipeTransaksi == TransaksiType.TOPUP) {
                    updateStmt.setDouble(1, -saldoTerpotong);
                } else if (tipeTransaksi == TransaksiType.TRANSFER) {
                    updateStmt.setDouble(1, -saldoTerpotong);
                }

                updateStmt.setString(2, nasabah.getUser_id());
                int updateRows = updateStmt.executeUpdate();
                System.out.println("Updated saldo for nasabah rows: " + updateRows);

                if (tipeTransaksi == TransaksiType.TRANSFER) {
                    String updateTargetQuery = "UPDATE users SET saldo = saldo + ? WHERE nomor_rekening = ?";
                    PreparedStatement updateTargetStmt = conn.con.prepareStatement(updateTargetQuery);
                    updateTargetStmt.setDouble(1, saldoDitambah);
                    updateTargetStmt.setInt(2, norekTujuan);
                    int updateTargetRows = updateTargetStmt.executeUpdate();
                    System.out.println("Updated saldo for target rekening rows: " + updateTargetRows);
                }

                conn.con.commit(); 
                return true;
            }
        } catch (SQLException e) {
            try {
                conn.con.rollback(); 
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                conn.con.setAutoCommit(true); 
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    public static Boolean verifyKodePromo(String kodePromo, TransaksiType tipeTransaksi) {
        if (kodePromo == null || kodePromo.isEmpty()) {
            return false;
        }

        try {
            conn.connect();
            String query = "SELECT * FROM promo WHERE kode_promo = ? AND promo_type = ?";
            PreparedStatement stmt = conn.con.prepareStatement(query);
            stmt.setString(1, kodePromo);
            stmt.setString(2, tipeTransaksi.name());

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
