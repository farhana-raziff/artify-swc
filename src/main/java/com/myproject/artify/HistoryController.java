package com.myproject.artify;

import com.myproject.dao.BookingDAO;
import com.myproject.model.Booking;
import com.myproject.model.User;
import com.myproject.util.Session;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class HistoryController {

    @FXML private TableView<Booking> tblBookings;

    @FXML private TableColumn<Booking, Number> colId;
    @FXML private TableColumn<Booking, String> colWorkshopTitle;
    @FXML private TableColumn<Booking, String> colSession;
    @FXML private TableColumn<Booking, String> colMaterials;
    @FXML private TableColumn<Booking, Number> colTotal;
    @FXML private TableColumn<Booking, String> colDate;

    @FXML private TableColumn<Booking, Void> colDelete; // ✅ NEW DELETE COLUMN

    @FXML private TextField txtSearch;

    private FilteredList<Booking> filteredList;

    @FXML
    public void initialize() {

        User user = Session.getCurrentUser();
        if (user == null) {
            new Alert(Alert.AlertType.ERROR, "Session expired. Please login again.").showAndWait();
            goToLogin();
            return;
        }

        // Setup table columns
        colId.setCellValueFactory(data -> data.getValue().idProperty());
        colWorkshopTitle.setCellValueFactory(data -> data.getValue().workshopTitleProperty());
        colSession.setCellValueFactory(data -> data.getValue().sessionProperty());
        colMaterials.setCellValueFactory(data -> data.getValue().materialsSelectedProperty());
        colTotal.setCellValueFactory(data -> data.getValue().totalPaymentProperty());
        colDate.setCellValueFactory(data -> data.getValue().bookingDateProperty());

        // Load user bookings
        ObservableList<Booking> list = BookingDAO.getBookingsByUser(user.getId());

        filteredList = new FilteredList<>(list, b -> true);
        tblBookings.setItems(filteredList);

        // Set up Search Filter
        txtSearch.textProperty().addListener((obs, oldVal, newVal) -> {
            String q = newVal == null ? "" : newVal.toLowerCase();

            filteredList.setPredicate(b -> {
                if (q.isEmpty()) return true;

                return b.getWorkshopTitle().toLowerCase().contains(q)
                        || b.getSession().toLowerCase().contains(q)
                        || b.getBookingDate().toLowerCase().contains(q)
                        || b.getMaterialsSelected().toLowerCase().contains(q);
            });
        });

        setupDeleteColumn(); // ✅ Enable delete buttons
    }

    /** DELETE BUTTON COLUMN SETUP **/
    private void setupDeleteColumn() {
        colDelete.setCellFactory(col -> new TableCell<Booking, Void>() {

            private final Button btn = new Button("🗑");

            {
                btn.setStyle("-fx-background-color: #ff9aae; -fx-text-fill: white; -fx-background-radius: 6;");
                btn.setOnAction(e -> {
                    Booking selected = getTableView().getItems().get(getIndex());
                    deleteBooking(selected);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : btn);
            }
        });
    }

    /** DELETE HANDLER **/
    private void deleteBooking(Booking booking) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Delete booking for '" + booking.getWorkshopTitle() + "'?",
                ButtonType.YES, ButtonType.NO);

        alert.setHeaderText("Confirm Delete");
        alert.setTitle("Delete Booking");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {

                boolean ok = BookingDAO.deleteById(booking.getId());

                if (ok) {
                    filteredList.getSource().remove(booking); // remove from underlying list
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to delete booking.").show();
                }

            }
        });
    }

    @FXML
    private void handleBack() {
        goToDashboard();
    }

    private void goToDashboard() {
        try {
            Stage stage = (Stage) tblBookings.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/myproject/artify/dashboard.fxml"));
            Scene scene = new Scene(loader.load(), 800, 500);
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void goToLogin() {
        try {
            Stage stage = (Stage) tblBookings.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/myproject/artify/login.fxml"));
            Scene scene = new Scene(loader.load(), 800, 500);
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
