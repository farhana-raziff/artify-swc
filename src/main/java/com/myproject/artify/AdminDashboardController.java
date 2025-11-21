package com.myproject.artify;

import com.myproject.util.Session;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AdminDashboardController {

    @FXML
    private void openAddWorkshop(ActionEvent event) {
        navigate("/com/myproject/artify/addWorkshop.fxml", event);
    }

    @FXML
    private void openWorkshopList(ActionEvent event) {
        navigate("/com/myproject/artify/adminWorkshopList.fxml", event);
    }

    @FXML
    private void logout(ActionEvent event) {
        Session.clear();
        navigate("/com/myproject/artify/login.fxml", event);
    }

    private void navigate(String fxmlPath, ActionEvent event) {
        try {
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Scene scene = new Scene(loader.load(), 800, 500);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
