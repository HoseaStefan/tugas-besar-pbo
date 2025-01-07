package controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import model.BlueDeposito;
import model.BlueGether;
import model.BlueSaving;
import model.Nasabah;

public class CreateTabunganController {

    static DatabaseHandler conn = new DatabaseHandler();

    public boolean createBlueSaving(BlueSaving blueSaving) {
        conn.connect();
        try {
            String query = "INSERT INTO BlueSaving (tabungan_id, user_id, namaTabungan, saldoAwal, dateCreated, saldoSaving, jangkaWaktu, targetSaldo, tabunganHarian) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.con.prepareStatement(query);

            String tabungan_id = generateTabunganId();
            if (tabungan_id == null) {
                JOptionPane.showMessageDialog(null, "Failed to generate Tabungan ID.", "Error", JOptionPane.ERROR_MESSAGE);
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

    public boolean createBlueDeposito(BlueDeposito blueDeposito) {
        conn.connect();
        try {
            // Check if the user already has an active deposit
            String checkQuery = "SELECT COUNT(*) AS depositCount FROM blue_deposito WHERE user_id = ? AND complete = false";
            PreparedStatement checkStmt = conn.con.prepareStatement(checkQuery);
            checkStmt.setString(1, blueDeposito.getuser_id());
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next() && rs.getInt("depositCount") > 0) {
                JOptionPane.showMessageDialog(null, "User already has an active deposit.", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }

            String getBalanceQuery = "SELECT saldo FROM users WHERE user_id = ?";
            PreparedStatement balanceStmt = conn.con.prepareStatement(getBalanceQuery);
            balanceStmt.setString(1, blueDeposito.getuser_id());
            ResultSet balanceRs = balanceStmt.executeQuery();

            if (!balanceRs.next()) {
                JOptionPane.showMessageDialog(null, "User not found.", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }

            double currentSaldo = balanceRs.getDouble("saldo");
            double saldoAwal = blueDeposito.getSaldoAwal();

            // Check if the user has enough balance
            if (currentSaldo < saldoAwal) {
                JOptionPane.showMessageDialog(null, "Insufficient balance.", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }

            // Deduct saldoAwal from user's current balance
            String updateSaldoQuery = "UPDATE users SET saldo = saldo - ? WHERE user_id = ?";
            PreparedStatement updateSaldoStmt = conn.con.prepareStatement(updateSaldoQuery);
            updateSaldoStmt.setDouble(1, saldoAwal);
            updateSaldoStmt.setString(2, blueDeposito.getuser_id());
            int rowsUpdated = updateSaldoStmt.executeUpdate();

            if (rowsUpdated <= 0) {
                JOptionPane.showMessageDialog(null, "Failed to update user's balance.", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }

            // Generate a unique tabungan ID
            String tabunganId = generateTabunganIdDeposito();
            if (tabunganId == null) {
                JOptionPane.showMessageDialog(null, "Failed to generate Tabungan ID.", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }

            // Insert the new deposit record
            String query = "INSERT INTO blue_deposito (tabungan_id, user_id, nama_tabungan, tabungan_type, saldo_awal, deposito_type, start_date, saldo_akhir, end_date, complete) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement insertStmt = conn.con.prepareStatement(query);

            insertStmt.setString(1, tabunganId);
            insertStmt.setString(2, blueDeposito.getuser_id());
            insertStmt.setString(3, blueDeposito.getNamaTabungan());
            insertStmt.setString(4, blueDeposito.getTabunganType().name());
            insertStmt.setDouble(5, saldoAwal);
            insertStmt.setString(6, blueDeposito.getDepositoType().name());
            insertStmt.setTimestamp(7, blueDeposito.getStart_date());
            insertStmt.setDouble(8, blueDeposito.getSaldoAkhir());
            insertStmt.setTimestamp(9, blueDeposito.getEndDate());
            insertStmt.setBoolean(10, blueDeposito.getComplete());

            int rowsInserted = insertStmt.executeUpdate();
            return rowsInserted > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public String generateTabunganIdDeposito() {
        conn.connect();
        String prefix = "d-";
        try {
            String query = "SELECT COUNT(*) AS total FROM blue_deposito";
            PreparedStatement stmt = conn.con.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int nextId = rs.getInt("total") + 1;
                return prefix + nextId;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String generateTabunganId() {
        conn.connect();
        String prefix = "s-";
        int nextId = 1;

        try {
            String query = "SELECT COUNT(*) AS total FROM blue_saving";
            PreparedStatement stmt = conn.con.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

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
                JOptionPane.showMessageDialog(null, "Failed to generate Tabungan ID.", "Error", JOptionPane.ERROR_MESSAGE);
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
                    boolean success = BlueGetherController.tambahNasabahToListNasabah(tabungan_id, nasabah.getUser_id());

                    if (!success) {
                        JOptionPane.showMessageDialog(null, "Failed to add nasabah with user ID: " + nasabah.getUser_id(), "Error", JOptionPane.ERROR_MESSAGE);
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
}
