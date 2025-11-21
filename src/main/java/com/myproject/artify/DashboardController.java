package com.myproject.artify;

import com.myproject.model.User;
import com.myproject.util.Session;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class DashboardController {

    @FXML
    private Label lblWelcome;

    @FXML
    private Label lblWelcome2;

    @FXML
    public void initialize() {
        User currentUser = Session.getCurrentUser();
        if (currentUser != null) {
            String msg = "Welcome, " + currentUser.getUsername() + "!";
            lblWelcome.setText(msg);
            lblWelcome2.setText(msg);
        }
    }

    @FXML
    private void handleLogout(ActionEvent event) {
        try {
            Stage stage = (Stage) lblWelcome.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/myproject/artify/login.fxml"));

            Scene scene = new Scene(loader.load(), 800, 500);
            stage.setScene(scene);
            stage.show();

            Session.clear();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void workshops(ActionEvent event) {
        openFixed("/com/myproject/artify/workshops.fxml");
    }

    @FXML
    private void history(ActionEvent event) {
        openFixed("/com/myproject/artify/history.fxml");
    }

    @FXML
    private void profile(ActionEvent event) {
        openFixed("/com/myproject/artify/profile.fxml");
    }

    private void openFixed(String fxmlPath) {
        try {
            Stage stage = (Stage) lblWelcome.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));

            Scene scene = new Scene(loader.load(), 800, 500);
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
