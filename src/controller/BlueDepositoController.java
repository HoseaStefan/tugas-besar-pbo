// package controller;

// import model.BlueDeposito; // Define this model class similar to BlueSaving
// import java.sql.*;
// import java.util.ArrayList;
// import java.util.List;

// public class BlueDepositoController {
// static DatabaseHandler conn = new DatabaseHandler();

// // Retrieve all deposits for a specific user
// public static List<BlueDeposito> getDepositsByUserId(String userId) {
// conn.connect();
// List<BlueDeposito> deposits = new ArrayList<>();

// try {
// String query = "SELECT * FROM blue_deposito WHERE user_id = ?";
// PreparedStatement stmt = conn.con.prepareStatement(query);
// stmt.setString(1, userId);
// ResultSet rs = stmt.executeQuery();

// while (rs.next()) {
// BlueDeposito deposito = new BlueDeposito(
// rs.getString("tabungan_id"),
// rs.getString("deposito_type"),
// rs.getDouble("saldo_awal"),
// rs.getDouble("saldo_akhir"),
// rs.getTimestamp("end_date"),
// rs.getBoolean("complete"),
// rs.getString("user_id")
// );

// deposits.add(deposito);
// }
// } catch (SQLException e) {
// e.printStackTrace();
// }
// return deposits;
// }

// // Calculate the total saldo_akhir for a specific user
// public static double getTotalSaldoAkhirByUserId(String userId) {
// conn.connect();
// double totalSaldoAkhir = 0.0;

// try {
// String query = "SELECT saldo_akhir FROM blue_deposito WHERE user_id = ?";
// PreparedStatement stmt = conn.con.prepareStatement(query);
// stmt.setString(1, userId);
// ResultSet rs = stmt.executeQuery();

// while (rs.next()) {
// totalSaldoAkhir += rs.getDouble("saldo_akhir");
// }
// } catch (SQLException e) {
// e.printStackTrace();
// }
// return totalSaldoAkhir;
// }
// }
