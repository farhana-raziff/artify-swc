package com.myproject.artify;

import com.myproject.dao.BookingDAO;
import com.myproject.model.User;
import com.myproject.util.Session;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.List;

public class ProfileController {

    @FXML private Label lblUsername;
    @FXML private Label lblJoinDate;
    @FXML private Label lblTotalBookings;
    @FXML private VBox listContainer;

    private User user;

    @FXML
    public void initialize() {
        user = Session.getCurrentUser();

        if (user == null) {
            return;
        }

        lblUsername.setText("Logged in as: " + user.getUsername());

        // Fake join date since DB does not store one
        lblJoinDate.setText("Joined: " + LocalDate.now().minusDays(30));

        int count = BookingDAO.countBookings(user.getId());
        lblTotalBookings.setText("Total Bookings: " + count);

        List<String> last5 = BookingDAO.getLastFive(user.getId());

        listContainer.getChildren().clear();

        if (last5.isEmpty()) {
            listContainer.getChildren().add(new Label("No recent bookings."));
            return;
        }

        for (String entry : last5) {
            Label item = new Label("• " + entry);
            item.setStyle("-fx-text-fill: #7a4b61; -fx-font-size: 14px;");
            listContainer.getChildren().add(item);
        }
    }

    @FXML
    private void handleBack() {
        try {
            Stage stage = (Stage) lblUsername.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/myproject/artify/dashboard.fxml")
            );
            Scene scene = new Scene(loader.load(), 800, 500);
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
