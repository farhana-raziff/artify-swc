package com.myproject.artify;

import com.myproject.dao.WorkshopDAO;
import com.myproject.model.Workshop;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddWorkshopController {

    @FXML private TextField txtTitle;
    @FXML private TextArea txtDescription;
    @FXML private TextField txtDate;
    @FXML private TextField txtTime;
    @FXML private TextField txtFee;
    @FXML private Label lblMessage;

    @FXML
    private void handleSave() {

        String title = txtTitle.getText().trim();
        String desc  = txtDescription.getText().trim();
        String date  = txtDate.getText().trim();
        String time  = txtTime.getText().trim();
        String feeStr = txtFee.getText().trim();

        if (title.isEmpty() || desc.isEmpty() || date.isEmpty()
                || time.isEmpty() || feeStr.isEmpty()) {
            lblMessage.setStyle("-fx-text-fill: #d6336c;");
            lblMessage.setText("⚠ Please fill in all fields.");
            return;
        }

        double fee;
        try {
            fee = Double.parseDouble(feeStr);
        } catch (NumberFormatException e) {
            lblMessage.setStyle("-fx-text-fill: #d6336c;");
            lblMessage.setText("⚠ Fee must be a number.");
            return;
        }

        // ✔ Use correct constructor
        Workshop newWorkshop = new Workshop(0, title, desc, date, time, fee);

        boolean success = WorkshopDAO.insert(newWorkshop);

        if (success) {
            lblMessage.setStyle("-fx-text-fill: green;");
            lblMessage.setText("✔ Workshop added successfully!");

            txtTitle.clear();
            txtDescription.clear();
            txtDate.clear();
            txtTime.clear();
            txtFee.clear();
        } else {
            lblMessage.setStyle("-fx-text-fill: #d6336c;");
            lblMessage.setText("✖ Failed to save workshop.");
        }
    }

    @FXML
    private void handleBack() {
        try {
            Stage stage = (Stage) txtTitle.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/myproject/artify/adminDashboard.fxml"));
            Scene scene = new Scene(loader.load(), 800, 500);
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
