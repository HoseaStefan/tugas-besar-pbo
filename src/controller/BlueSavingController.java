package controller;

import model.BlueSaving;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BlueSavingController {
    static DatabaseHandler conn = new DatabaseHandler();

    public static List<BlueSaving> getBlueSavingsByUserId(String userId) {
        conn.connect();
        List<BlueSaving> blueSavings = new ArrayList<>();

        try {
            String query = "SELECT * FROM BlueSaving WHERE user_id = ?";
            PreparedStatement stmt = conn.con.prepareStatement(query);
            stmt.setString(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                BlueSaving blueSaving = new BlueSaving(
                        rs.getString("tabungan_id"),
                        rs.getString("user_id"),
                        rs.getString("namaTabungan"),
                        null, // TabunganType jika diperlukan, sesuaikan dengan model Anda
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
            String query = "SELECT saldoSaving FROM BlueSaving WHERE user_id = ?";
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

}
