package com.myproject.dao;

import com.myproject.model.Workshop;
import com.myproject.util.DBUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class WorkshopDAO {

    // Get ALL workshops
    public static ObservableList<Workshop> getAll() {
        ObservableList<Workshop> list = FXCollections.observableArrayList();

        String sql = "SELECT id, title, description, date, time, fee FROM workshops ORDER BY id ASC";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String description = rs.getString("description");
                String date = rs.getString("date");
                String time = rs.getString("time");
                double fee = rs.getDouble("fee");

                Workshop w = new Workshop(id, title, description, date, time, fee);
                list.add(w);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // Insert new workshop
    public static boolean insert(Workshop w) {
        String sql = "INSERT INTO workshops(title, description, date, time, fee) VALUES (?,?,?,?,?)";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, w.getTitle());
            ps.setString(2, w.getDescription());
            ps.setString(3, w.getDate());
            ps.setString(4, w.getTime());
            ps.setDouble(5, w.getFee());

            ps.executeUpdate();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Update existing workshop
    public static boolean update(Workshop w) {

        String sql =
                "UPDATE workshops SET " +
                "title = ?, " +
                "description = ?, " +
                "date = ?, " +
                "time = ?, " +
                "fee = ? " +
                "WHERE id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, w.getTitle());
            ps.setString(2, w.getDescription());
            ps.setString(3, w.getDate());
            ps.setString(4, w.getTime());
            ps.setDouble(5, w.getFee());
            ps.setInt(6, w.getId());

            ps.executeUpdate();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
