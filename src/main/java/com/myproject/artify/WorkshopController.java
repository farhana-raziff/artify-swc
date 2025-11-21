package com.myproject.artify;

import com.myproject.dao.WorkshopDAO;
import com.myproject.model.Workshop;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class WorkshopController {

    @FXML private BorderPane root;

    @FXML private Button btnWatercolor;
    @FXML private Button btnDigitalArt;
    @FXML private Button btnAcrylic;

    @FXML private VBox staticCards;
    @FXML private VBox dynamicCards;

    private ObservableList<Workshop> allDynamicWorkshops = FXCollections.observableArrayList();

    @FXML
    public void initialize() {

        if (dynamicCards == null) return;

        WorkshopDAO.getAll().forEach(w -> {
            if (w.getId() <= 3) return; // skip static 3
            allDynamicWorkshops.add(w);
        });

        refreshDynamicCards();
    }

    private void refreshDynamicCards() {
        dynamicCards.getChildren().clear();

        for (Workshop w : allDynamicWorkshops) {
            VBox card = createCard(w);
            dynamicCards.getChildren().add(card);
        }
    }

    @FXML
    private void bookWatercolor() {
        Workshop w = new Workshop(1, "Watercolor Basics",
                "Every Saturday", "10AM - 12PM", 50.00, 20);
        openBookingForm(w);
    }

    @FXML
    private void bookDigitalArt() {
        Workshop w = new Workshop(2, "Digital Art for Beginners",
                "Every Sunday", "2PM - 4PM", 60.00, 20);
        openBookingForm(w);
    }

    @FXML
    private void bookAcrylic() {
        Workshop w = new Workshop(3, "Acrylic Painting",
                "Every Tuesday", "1PM - 3PM", 55.00, 20);
        openBookingForm(w);
    }

    private VBox createCard(Workshop w) {

        Label title = new Label("🎨 " + w.getTitle());
        title.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #d6336c;");

        Label desc = new Label(
                w.getDescription() != null && !w.getDescription().isEmpty()
                        ? w.getDescription()
                        : " ");
        desc.setWrapText(true);
        desc.setStyle("-fx-font-size: 12px; -fx-text-fill: #7a4b61;");

        Label schedule = new Label("🗓 " + w.getDate() + " | " + w.getTime());
        schedule.setStyle("-fx-font-size: 12px; -fx-text-fill: #a05278;");

        Label fee = new Label(String.format("💰 RM %.2f", w.getFee()));
        fee.setStyle("-fx-font-size: 12px; -fx-text-fill: #a05278;");

        Button btn = new Button("Book Now");
        btn.setStyle("-fx-background-color: #ff9aae; -fx-text-fill: white; "
                + "-fx-font-weight: bold; -fx-background-radius: 6; "
                + "-fx-pref-width: 120;");
        btn.setOnAction(e -> openBookingForm(w));

        VBox box = new VBox(10, title, desc, schedule, fee, btn);
        box.setPadding(new Insets(15));
        box.setStyle("-fx-background-color: white;"
                + "-fx-border-color: #ff9aae;"
                + "-fx-border-radius: 8;"
                + "-fx-background-radius: 8;"
                + "-fx-effect: dropshadow(gaussian, #f8c2d1, 6, 0.5, 0, 2);");

        return box;
    }

    private void openBookingForm(Workshop selected) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/myproject/artify/booking.fxml"));
            Scene scene = new Scene(loader.load(), 800, 500);

            BookingController controller = loader.getController();
            controller.setWorkshop(selected);

            Stage stage = (Stage) root.getScene().getWindow();
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleBack() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/myproject/artify/dashboard.fxml"));
            Scene scene = new Scene(loader.load(), 800, 500);

            Stage stage = (Stage) root.getScene().getWindow();
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
