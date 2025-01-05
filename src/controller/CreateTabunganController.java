package controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import model.BlueSaving;
import model.Nasabah;
import model.BlueDeposito;
import model.BlueGether;
import controller.BlueGetherController;

public class CreateTabunganController {

    static DatabaseHandler conn = new DatabaseHandler();

    public boolean createBlueSaving(BlueSaving blueSaving) {
        conn.connect();
        try {
            conn.connect();
            String query = "INSERT INTO BlueSaving (tabungan_id, user_id, namaTabungan, saldoAwal, dateCreated, saldoSaving, jangkaWaktu, targetSaldo, tabunganHarian) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.con.prepareStatement(query);

            String tabungan_id = generateTabunganIdBlueSaving();
            if (tabungan_id == null) {
                JOptionPane.showMessageDialog(null, "Failed to generate Tabungan ID.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return false;
            }

            double tabunganHarian = BlueSaving.hitungTabunganHarian(blueSaving.getSaldoAwal(),
                    blueSaving.getTargetSaldo(), blueSaving.getJangkaWaktu());

            stmt.setString(1, tabungan_id);
            stmt.setString(2, blueSaving.getuser_id());
            stmt.setString(3, blueSaving.getNamaTabungan());
            stmt.setDouble(4, blueSaving.getSaldoAwal());
            stmt.setTimestamp(5, blueSaving.getStart_date());
            stmt.setDouble(6, blueSaving.getSaldoSaving());
            stmt.setInt(7, blueSaving.getJangkaWaktu());
            stmt.setDouble(8, blueSaving.getTargetSaldo());
            stmt.setDouble(9, tabunganHarian);

            blueSaving.setTabungan_id(tabungan_id);
            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public String generateTabunganIdBlueSaving() {
        conn.connect();
        String prefix = "s-";
        int nextId = 1;

        try {
            // Query untuk mencari ID terakhir
            String query = "SELECT tabungan_id FROM bluesaving ORDER BY tabungan_id DESC LIMIT 1";
            PreparedStatement stmt = conn.con.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // Ambil angka setelah prefix "s-" dan tambahkan 1
                String lastId = rs.getString("tabungan_id");
                nextId = Integer.parseInt(lastId.split("-")[1]) + 1;
            }

            return prefix + nextId;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean createBlueGether(BlueGether blueGether) {
        conn.connect();
        try {
            String query = "INSERT INTO bluegether (tabungan_id, user_id, namaTabungan, saldoAwal, dateCreated, saldoGether, jangkaWaktu, targetSaldo, tabunganHarian) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.con.prepareStatement(query);

            String tabungan_id = generateTabunganIdBlueGether();
            if (tabungan_id == null) {
                JOptionPane.showMessageDialog(null, "Failed to generate Tabungan ID.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return false;
            }

            double tabunganHarian = BlueGether.hitungTabunganHarian(blueGether.getSaldoAwal(),
                    blueGether.getTargetSaldo(), blueGether.getJangkaWaktu());

            stmt.setString(1, tabungan_id);
            stmt.setString(2, blueGether.getuser_id());
            stmt.setString(3, blueGether.getNamaTabungan());
            stmt.setDouble(4, blueGether.getSaldoAwal());
            stmt.setTimestamp(5, blueGether.getStart_date());
            stmt.setDouble(6, blueGether.getSaldoGether());
            stmt.setInt(7, blueGether.getJangkaWaktu());
            stmt.setDouble(8, blueGether.getTargetSaldo());
            stmt.setDouble(9, tabunganHarian);

            blueGether.setTabungan_id(tabungan_id);

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                // Tambahkan semua nasabah di listNasabah ke database
                for (Nasabah nasabah : blueGether.getListNasabah()) {
                    boolean success = BlueGetherController.tambahNasabahToListNasabah(tabungan_id,
                            nasabah.getUser_id());

                    if (!success) {
                        JOptionPane.showMessageDialog(null,
                                "Failed to add nasabah with user ID: " + nasabah.getUser_id(),
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public String generateTabunganIdBlueGether() {
        conn.connect();
        String prefix = "g-";
        int nextId = 1;

        try {
            // Query untuk mencari ID terakhir
            String query = "SELECT tabungan_id FROM bluegether ORDER BY tabungan_id DESC LIMIT 1";
            PreparedStatement stmt = conn.con.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String lastId = rs.getString("tabungan_id");
                nextId = Integer.parseInt(lastId.split("-")[1]) + 1;
            }

            return prefix + nextId;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // public boolean createBlueDeposito(BlueDeposito blueDeposito) {
    // conn.connect();
    // try {
    // // Check if the user already has an active deposit
    // String checkQuery = "SELECT COUNT(*) AS depositCount FROM blue_deposito WHERE
    // user_id = ? AND complete = false";
    // PreparedStatement checkStmt = conn.con.prepareStatement(checkQuery);
    // checkStmt.setString(1, blueDeposito.getUserId());
    // ResultSet rs = checkStmt.executeQuery();

    // if (rs.next() && rs.getInt("depositCount") > 0) {
    // // Active deposit already exists
    // JOptionPane.showMessageDialog(null, "User already has an active deposit.",
    // "Error",
    // JOptionPane.ERROR_MESSAGE);
    // return false;
    // }

    // // If no active deposit exists, proceed to create a new deposit
    // String query = "INSERT INTO blue_deposito (tabungan_id, deposito_type,
    // saldo_awal, saldo_akhir, end_date, complete, user_id) "
    // + "VALUES (?, ?, ?, ?, ?, ?, ?)";
    // PreparedStatement insertStmt = conn.con.prepareStatement(query);

    // String tabunganId = generateTabunganId(); // Generate a unique tabungan_id
    // if (tabunganId == null) {
    // JOptionPane.showMessageDialog(null, "Failed to generate Tabungan ID.",
    // "Error",
    // JOptionPane.ERROR_MESSAGE);
    // return false;
    // }

    // // Set parameters for the deposit
    // insertStmt.setString(1, tabunganId);
    // insertStmt.setString(2, blueDeposito.getDepositoType());
    // insertStmt.setDouble(3, blueDeposito.getSaldoAwal());
    // insertStmt.setDouble(4, blueDeposito.getSaldoAkhir()); // This may initially
    // be the same as saldo_awal
    // insertStmt.setTimestamp(5, blueDeposito.getEndDate());
    // insertStmt.setBoolean(6, blueDeposito.isComplete()); // false for new
    // deposits
    // insertStmt.setString(7, blueDeposito.getUserId());

    // int rowsInserted = insertStmt.executeUpdate();
    // return rowsInserted > 0;
    // } catch (Exception e) {
    // e.printStackTrace();
    // return false;
    // }
    // }

}