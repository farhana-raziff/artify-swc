package com.myproject.artify;

import com.myproject.dao.WorkshopDAO;
import com.myproject.model.Workshop;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class AdminWorkshopListController {

    @FXML private TableView<Workshop> tableWorkshop;
    @FXML private TableColumn<Workshop, Integer> colId;
    @FXML private TableColumn<Workshop, String> colDescription;
    @FXML private TableColumn<Workshop, String> colTitle;
    @FXML private TableColumn<Workshop, String> colDate;
    @FXML private TableColumn<Workshop, String> colTime;
    @FXML private TableColumn<Workshop, Double> colFee;
    @FXML private TableColumn<Workshop, Void> colEdit;

    @FXML
    public void initialize() {

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("time"));
        colFee.setCellValueFactory(new PropertyValueFactory<>("fee"));

        addEditButton();

        tableWorkshop.setItems(WorkshopDAO.getAll());
    }

    private void addEditButton() {
        colEdit.setCellFactory(col -> new TableCell<Workshop, Void>() {
            private final Button btn = new Button("Edit");

            {
                btn.setStyle("-fx-background-color: #ff9aae; -fx-text-fill: white; -fx-background-radius: 6;");
                btn.setOnAction(e -> {
                    Workshop w = getTableView().getItems().get(getIndex());
                    openEditForm(w);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : btn);
            }
        });
    }

    private void openEditForm(Workshop w) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/myproject/artify/editWorkshop.fxml"));
            Scene scene = new Scene(loader.load(), 800, 500);

            EditWorkshopController controller = loader.getController();
            controller.setWorkshop(w);

            Stage stage = (Stage) tableWorkshop.getScene().getWindow();
            stage.setScene(scene);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void handleBack(ActionEvent e) {
        try {
            Stage stage = (Stage) tableWorkshop.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/myproject/artify/adminDashboard.fxml"));
            Scene scene = new Scene(loader.load(), 800, 500);
            stage.setScene(scene);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
