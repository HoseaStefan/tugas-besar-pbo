package controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.Admin;
import model.CurrentUser;
import model.Nasabah;
import model.User;
import model.UserType;

public class UserController {

    private static UserController instance;
    static DatabaseHandler conn = new DatabaseHandler();

    public static UserController getInstance() {
        if (instance == null) {
            synchronized (UserController.class) {

                if (instance == null) {
                    instance = new UserController();
                }
            }
        }
        return instance;
    }

    public static boolean changePassword(String email, String newPassword) {
        if (!isEmailInDatabase(email)) {
            return false;
        }

        try {
            conn.connect();

            String query = "UPDATE users SET password = ? WHERE email = ?";
            PreparedStatement stmt = conn.con.prepareStatement(query);
            stmt.setString(1, newPassword);
            stmt.setString(2, email);

            int rowsUpdated = stmt.executeUpdate();

            if (rowsUpdated > 0) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            conn.disconnect();
        }
    }

    public static boolean isEmailInDatabase(String email) {
        try {
            conn.connect();
            String query = "SELECT * FROM users WHERE email = ?";
            PreparedStatement stmt = conn.con.prepareStatement(query);
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return false;
    }

    public boolean verifyPIN(Nasabah nasabah, String inputPin) {
        try {
            conn.connect();
            String query = "SELECT pin FROM users WHERE user_id = ?";
            PreparedStatement stmt = conn.con.prepareStatement(query);
            stmt.setString(1, nasabah.getUser_id());
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int storedPin = rs.getInt("pin");
                return storedPin == Integer.parseInt(inputPin);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return false;
    }

    public static void insertNewPIN(Nasabah nasabah, String newPIN) {
        try {
            conn.connect();
            String query = "UPDATE users SET pin = ? WHERE user_id = ?";
            PreparedStatement stmt = conn.con.prepareStatement(query);
            stmt.setString(1, newPIN);
            stmt.setString(2, nasabah.getUser_id());

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("PIN updated successfully for user ID: " + nasabah.getUser_id());
            } else {
                System.out.println("Failed to update PIN. User ID not found: " + nasabah.getUser_id());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
    }

    public static User verifyUser(String username, String password) {
        try {
            conn.connect();
            String query = "SELECT * FROM users WHERE (username = ? OR email = ?) AND password = ?";
            PreparedStatement stmt = conn.con.prepareStatement(query);
            stmt.setString(1, username);
            stmt.setString(2, username);
            stmt.setString(3, password);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String userType = rs.getString("user_type");
                String userId = rs.getString("user_id");
                String status = rs.getString("status");

                User loggedInUser = null;
                Nasabah loggedInNasabah = null;
                Admin loggedInAdmin = null;
                if ("NASABAH".equals(userType) && status.equals("ALLOWED")) {
                    loggedInUser = fetchNasabah(userId, rs);
                    loggedInNasabah = fetchNasabah(userId, rs);
                } else if ("ADMIN".equals(userType)) {
                    loggedInUser = fetchAdmin(userId, rs);
                    loggedInAdmin = fetchAdmin(userId, rs);
                }

                if (loggedInUser != null) {
                    CurrentUser.getInstance().setUser(loggedInUser);
                    CurrentUser.getInstance().setNasabah(loggedInNasabah);
                    CurrentUser.getInstance().setAdmin(loggedInAdmin);
                }
                return loggedInUser;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Nasabah fetchNasabah(String userId, ResultSet userRs) throws SQLException {
        return new Nasabah(
                userId,
                userRs.getString("name"),
                userRs.getString("username"),
                userRs.getString("email"),
                UserType.NASABAH,
                userRs.getString("full_name"),
                userRs.getInt("pin"),
                userRs.getInt("nomor_rekening"),
                userRs.getDouble("saldo"),
                null,
                userRs.getString("status"));
    }

    public static Admin fetchAdmin(String userId, ResultSet userRs) throws SQLException {
        return new Admin(
                userId,
                userRs.getString("name"),
                userRs.getString("username"),
                userRs.getString("email"),
                UserType.ADMIN);
    }

    public static boolean verifyRegister(String username, String email, String password) {
        try {
            conn.connect();

            String checkQuery = "SELECT * FROM users WHERE username = ? OR email = ?";
            PreparedStatement checkStmt = conn.con.prepareStatement(checkQuery);
            checkStmt.setString(1, username);
            checkStmt.setString(2, email);

            ResultSet rs = checkStmt.executeQuery();
            if (rs.next()) {
                return false;
            }

            String insertQuery = "INSERT INTO users (user_id, name, username, email, user_type, password, saldo, nomor_rekening, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement insertStmt = conn.con.prepareStatement(insertQuery);
            String userUniqueId = generateUniqueId();
            insertStmt.setString(1, "USR" + userUniqueId);
            insertStmt.setString(2, username);
            insertStmt.setString(3, username);
            insertStmt.setString(4, email);
            insertStmt.setString(5, "NASABAH");
            insertStmt.setString(6, password);
            insertStmt.setDouble(7, 0);
            insertStmt.setString(8, userUniqueId);
            insertStmt.setString(9, "ALLOWED");

            int rowsInserted = insertStmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String generateUniqueId() {
        return "" + (System.currentTimeMillis() % 1000000);
    }

    public static boolean updateUserSaldo(String userId, double nominal) {
        conn.connect();
        try {
            String query = "UPDATE users SET saldo = saldo + ? WHERE user_id = ?";
            PreparedStatement stmt = conn.con.prepareStatement(query);
            stmt.setDouble(1, nominal);
            stmt.setString(2, userId);

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
