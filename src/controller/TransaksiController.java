package controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.*;

public class TransaksiController {

    static DatabaseHandler conn = new DatabaseHandler();

    public static Boolean createTransaksi(TransaksiType tipeTransaksi, double amount, String kodePromo, Nasabah nasabah,
            String norekTujuan, Double biayaAdmin, TopUpType topUpType) {
        try {
            conn.connect();

            double saldoDitambah = 0;
            double saldoTerpotong = 0;

            if (kodePromo != null && !kodePromo.isEmpty()) {
                biayaAdmin = 0.0;
            }

            saldoDitambah = tipeTransaksi == TransaksiType.SETOR ? amount - biayaAdmin : 0;
            saldoTerpotong = tipeTransaksi != TransaksiType.SETOR ? amount + biayaAdmin : 0;

            String transaksiId = java.util.UUID.randomUUID().toString();

            String query = "INSERT INTO transaksi (transaksi_id, nasabah_id, transaksi_type, biaya_admin, transaksi_date, kode_promo, jumlah_saldo_terpotong, jumlah_saldo_ditambah, status_type, topup_type) VALUES (?, ?, ?, ?, NOW(), ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.con.prepareStatement(query);

            stmt.setString(1, transaksiId);
            stmt.setString(2, nasabah.getUser_id());
            stmt.setString(3, tipeTransaksi.name());
            stmt.setDouble(4, biayaAdmin);
            stmt.setString(5, kodePromo);
            stmt.setDouble(6, saldoTerpotong);
            stmt.setDouble(7, saldoDitambah);
            stmt.setString(8, StatusType.BERHASIL.name());
            stmt.setString(9, topUpType != null ? topUpType.name() : null);

            int rows = stmt.executeUpdate();

            if (rows > 0) {
                String updateQuery = "UPDATE users SET saldo = saldo + ? WHERE user_id = ?";
                PreparedStatement updateStmt = conn.con.prepareStatement(updateQuery);

                if (tipeTransaksi == TransaksiType.SETOR) {
                    updateStmt.setDouble(1, saldoDitambah);
                } else {
                    updateStmt.setDouble(1, -saldoTerpotong);
                }

                updateStmt.setString(2, nasabah.getUser_id());
                updateStmt.executeUpdate();

                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
