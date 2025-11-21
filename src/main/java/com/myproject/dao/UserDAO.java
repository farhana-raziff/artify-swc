package com.myproject.dao;

import com.myproject.model.User;
import com.myproject.util.DBUtil;

import java.sql.*;

public class UserDAO {

    // ----------------------------------------
    // LOGIN
    // ----------------------------------------
    public static User login(String username, String password) {

        String sql = "SELECT * FROM users WHERE username=? AND password=?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("role")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // ----------------------------------------
    // REGISTER NEW USER
    // ----------------------------------------
    public static boolean register(String username, String password) {

        String sql = "INSERT INTO users(username, password, role) VALUES (?, ?, 'user')";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, password);

            ps.executeUpdate();
            return true;  // success

        } catch (SQLIntegrityConstraintViolationException e) {
            // Username already exists
            return false;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ----------------------------------------
    // UPDATE PASSWORD
    // ----------------------------------------
    public static boolean updatePassword(int userId, String newPassword) {

        String sql = "UPDATE users SET password=? WHERE id=?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, newPassword);
            ps.setInt(2, userId);

            return ps.executeUpdate() > 0;   // returns true if rows updated

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
