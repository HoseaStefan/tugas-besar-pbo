package controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import model.Report;
import model.StatusReport;

public class ReportController {
    static DatabaseHandler conn = new DatabaseHandler();

    public static String validateUserData(String username, String email, String password) {
        conn.connect();
        try {
            String query = "SELECT * FROM users WHERE username = ? AND email = ? AND password = ?";
            PreparedStatement checkStmt = conn.con.prepareStatement(query);
            checkStmt.setString(1, username);
            checkStmt.setString(2, email);
            checkStmt.setString(3, password);

            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                String userId = rs.getString("user_id");
                return userId;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean createReport(Report report) {
        conn.connect();
        try {
            conn.connect();
            String query = "INSERT INTO report (report_id, nasabah_id, email_verifikasi, message_report, status_report) "
                    + "VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.con.prepareStatement(query);

            String report_id = generateIdReport();
            if (report_id == null) {
                JOptionPane.showMessageDialog(null, "Failed to generate Report ID.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return false;
            }

            stmt.setString(1, report_id);
            stmt.setString(2, report.getNasabah_id());
            stmt.setString(3, report.getEmailVerifikasi());
            stmt.setString(4, report.getDeskripsi());
            stmt.setNString(5, report.getStatusReport().name());

            report.setReport_id(report_id);
            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public String generateIdReport() {
        conn.connect();
        String prefix = "r-";
        int nextId = 1;

        try {
            // Query untuk mencari ID terakhir
            String query = "SELECT report_id FROM report ORDER BY report_id DESC LIMIT 1";
            PreparedStatement stmt = conn.con.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // Ambil angka setelah prefix "s-" dan tambahkan 1
                String lastId = rs.getString("report_id");
                nextId = Integer.parseInt(lastId.split("-")[1]) + 1;
            }

            return prefix + nextId;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<Report> getReportsByUserId(String pilihTypeReport) {
        conn.connect();
        List<Report> reports = new ArrayList<>();

        // Tentukan query berdasarkan pilihan
        String query;
        List<String> params = new ArrayList<>();

        if (pilihTypeReport.equals("ALL")) {
            query = "SELECT * FROM report"; // Semua laporan
        } else if (pilihTypeReport.equals("BELUMTERSELESAIKAN")) {
            query = "SELECT * FROM report WHERE status_report IN (?, ?)";
            params.add("DIABAIKAN");
            params.add("DIPROSES");
        } else {
            query = "SELECT * FROM report WHERE status_report = ?";
            params.add(pilihTypeReport);
        }

        try {
            PreparedStatement stmt = conn.con.prepareStatement(query);

            // Isi parameter jika ada
            for (int i = 0; i < params.size(); i++) {
                stmt.setString(i + 1, params.get(i));
            }

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                // Konversi status_report ke enum
                StatusReport status = StatusReport.valueOf(rs.getString("status_report"));

                // Bangun objek Report
                Report report = new Report(
                        rs.getString("report_id"),
                        rs.getString("nasabah_id"),
                        rs.getString("email_verifikasi"),
                        rs.getString("message_report"),
                        status);
                reports.add(report);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reports;
    }

    public static boolean updateStatusReport(Report report, String selectedStatus) {
        conn.connect();

        try {
            String updateQuery = "UPDATE report SET status_report = ? WHERE report_id = ?";
            PreparedStatement stmt = conn.con.prepareStatement(updateQuery);
            stmt.setString(1, selectedStatus);
            stmt.setString(2, report.getReport_id());

            // Eksekusi query
            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
            } else {
                return false;
            }

            return true;
        } catch (SQLException e) {
            System.out.println("Terjadi kesalahan saat memperbarui status report: " + e.getMessage());
            return false;
        }
    }
}
