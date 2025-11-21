package com.myproject.dao;

import com.myproject.model.Booking;
import com.myproject.util.DBUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookingDAO {

    public static void createBooking(int userId, int workshopId, String session,
                                     String materials, double totalPayment) {

        String sql = "INSERT INTO bookings(user_id, workshop_id, session, materials_selected, total_payment) " +
                     "VALUES (?,?,?,?,?)";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setInt(2, workshopId);
            ps.setString(3, session);
            ps.setString(4, materials);
            ps.setDouble(5, totalPayment);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static boolean deleteById(int id) {
    String sql = "DELETE FROM bookings WHERE id = ?";

    try (Connection conn = DBUtil.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setInt(1, id);
        return stmt.executeUpdate() > 0;   // true if a row was deleted

    } catch (Exception e) {                // catch generic Exception, like other methods
        e.printStackTrace();
        return false;
    }
}


    public static ObservableList<Booking> getBookingsByUser(int userId) {
        ObservableList<Booking> list = FXCollections.observableArrayList();

        String sql = "SELECT b.*, w.title AS workshop_title " +
                     "FROM bookings b JOIN workshops w ON b.workshop_id = w.id " +
                     "WHERE b.user_id=? ORDER BY b.booking_date DESC";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new Booking(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getInt("workshop_id"),
                        rs.getString("workshop_title"),
                        rs.getString("session"),
                        rs.getString("materials_selected"),
                        rs.getDouble("total_payment"),
                        rs.getString("booking_date")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public static int countBookings(int userId) {
        String sql = "SELECT COUNT(*) FROM bookings WHERE user_id=?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0; // default
    }

     public static List<String> getLastFive(int userId) {
        List<String> list = new ArrayList<>();

        String sql = "SELECT w.title AS workshop_name, b.booking_date " +
                     "FROM bookings b JOIN workshops w ON b.workshop_id = w.id " +
                     "WHERE b.user_id=? ORDER BY b.booking_date DESC LIMIT 5";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String booking = rs.getString("workshop_name") +
                                 " — " +
                                 rs.getString("booking_date");

                list.add(booking);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}
