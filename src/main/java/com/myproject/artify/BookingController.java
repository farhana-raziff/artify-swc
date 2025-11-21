package com.myproject.artify;

import com.myproject.dao.BookingDAO;
import com.myproject.model.User;
import com.myproject.model.Workshop;
import com.myproject.util.Session;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class BookingController {

    @FXML private Label lblWorkshopTitle;
    @FXML private Label lblBaseFee;

    @FXML private RadioButton rbMorning;
    @FXML private RadioButton rbAfternoon;
    @FXML private CheckBox chkMaterials;
    @FXML private CheckBox chkCertificate;

    @FXML private TextField txtTotal;
    @FXML private Button btnConfirm;
    @FXML private Button btnCancel;

    private ToggleGroup sessionGroup;
    private Workshop workshop;

    @FXML
    public void initialize() {
        sessionGroup = new ToggleGroup();
        rbMorning.setToggleGroup(sessionGroup);
        rbAfternoon.setToggleGroup(sessionGroup);

        rbMorning.setOnAction(e -> updateTotal());
        rbAfternoon.setOnAction(e -> updateTotal());
        chkMaterials.setOnAction(e -> updateTotal());
        chkCertificate.setOnAction(e -> updateTotal());
    }

    public void setWorkshop(Workshop w) {
        this.workshop = w;
        lblWorkshopTitle.setText(w.getTitle());
        lblBaseFee.setText(String.format("Base Fee: RM %.2f", w.getFee()));
        updateTotal();
    }

    private void updateTotal() {
        if (workshop == null) return;

        double total = workshop.getFee();
        if (chkMaterials.isSelected()) total += 20;
        if (chkCertificate.isSelected()) total += 10;

        txtTotal.setText(String.format("%.2f", total));
    }

    @FXML
    private void handleConfirm() {
        if (workshop == null) return;

        User user = Session.getCurrentUser();
        if (user == null) {
            new Alert(Alert.AlertType.ERROR, "Session expired. Please login again.").showAndWait();
            goToLogin();
            return;
        }

        RadioButton selectedSessionBtn = (RadioButton) sessionGroup.getSelectedToggle();
        if (selectedSessionBtn == null) {
            new Alert(Alert.AlertType.WARNING, "Please select a session.").showAndWait();
            return;
        }

        String session = selectedSessionBtn.getText();
        String materials = "";
        if (chkMaterials.isSelected()) materials += "Materials ";
        if (chkCertificate.isSelected()) materials += "Certificate ";

        double total = Double.parseDouble(txtTotal.getText());

        BookingDAO.createBooking(user.getId(), workshop.getId(), session, materials.trim(), total);

        new Alert(Alert.AlertType.INFORMATION, "Booking successful!").showAndWait();
        goToDashboard();
    }

    @FXML
    private void handleCancel() {
        goToDashboard();
    }

    private void goToDashboard() {
        try {
            Stage stage = (Stage) btnCancel.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/myproject/artify/workshops.fxml"));
            Scene scene = new Scene(loader.load(), 800, 500);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void goToLogin() {
        try {
            Stage stage = (Stage) btnCancel.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/myproject/artify/login.fxml"));
            Scene scene = new Scene(loader.load(), 800, 500);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
