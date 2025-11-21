package com.myproject.artify;

import com.myproject.dao.UserDAO;
import com.myproject.model.User;
import com.myproject.util.Session;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class LoginController {

    @FXML private TextField txtUsername;
    @FXML private PasswordField txtPassword;
    @FXML private Button btnLogin;
    @FXML private Label lblMessage;

    @FXML
    private void handleLogin(ActionEvent event) {

        String username = txtUsername.getText().trim();
        String password = txtPassword.getText().trim();

        // Validate
        if (username.isEmpty() || password.isEmpty()) {
            lblMessage.setText("Please enter username and password.");
            return;
        }

        // Query database
        User user = UserDAO.login(username, password);

        if (user == null) {
            lblMessage.setText("Invalid username or password.");
            return;
        }

        // Save user session
        Session.setCurrentUser(user);

        try {
            Stage stage = (Stage) btnLogin.getScene().getWindow();
            FXMLLoader loader;

            // -----------------------------------------
            // ROLE CHECK → Admin or User Dashboard
            // -----------------------------------------
            if ("admin".equalsIgnoreCase(user.getRole())) {
                loader = new FXMLLoader(getClass().getResource("/com/myproject/artify/adminDashboard.fxml"));
            } else {
                loader = new FXMLLoader(getClass().getResource("/com/myproject/artify/dashboard.fxml"));
            }

            Scene scene = new Scene(loader.load(), 800, 500);
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
            lblMessage.setText("Error loading dashboard.");
        }
    }

    // -----------------------------------------
    // GO TO REGISTER PAGE
    // -----------------------------------------
    @FXML
    private void goToRegister() {
        try {
            Stage stage = (Stage) btnLogin.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/myproject/artify/register.fxml"));
            Scene scene = new Scene(loader.load(), 800, 500);
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
            lblMessage.setText("Error opening registration page.");
        }
    }
}
