package com.myproject.artify;

import com.myproject.dao.UserDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class RegisterController {

    @FXML private TextField txtUsername;
    @FXML private PasswordField txtPassword;
    @FXML private PasswordField txtConfirm;
    @FXML private Label lblMessage;

    @FXML
    private void handleRegister() {

        String username = txtUsername.getText().trim();
        String password = txtPassword.getText();
        String confirm = txtConfirm.getText();

        if (username.isEmpty() || password.isEmpty() || confirm.isEmpty()) {
            lblMessage.setText("All fields are required.");
            return;
        }

        if (!password.equals(confirm)) {
            lblMessage.setText("Passwords do not match.");
            return;
        }

        boolean created = UserDAO.register(username, password);

        if (!created) {
            lblMessage.setText("Username already exists!");
        } else {
            lblMessage.setText("Account created! Please login.");
        }
    }

    @FXML
    private void goToLogin() {
        try {
            Stage stage = (Stage) txtUsername.getScene().getWindow();
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/com/myproject/artify/login.fxml")));
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
