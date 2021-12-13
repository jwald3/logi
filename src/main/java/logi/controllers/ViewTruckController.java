package logi.controllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import logi.models.Truck;
import logi.util.TruckConnector;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ViewTruckController implements Initializable {
    public BorderPane rootID;
    public TableView<Truck> tvTrucks;
    @FXML
    public TableColumn<Truck, String> colTruckID;
    @FXML
    public TableColumn<Truck, Integer> colCapacity;

    private TruckConnector truckConnector;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        truckConnector = new TruckConnector();
        tvTrucks.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        showTrucks();
    }

    @FXML
    private void clickDelete() {
        if (tvTrucks.getSelectionModel().getSelectedItem() != null) {
            Truck selectedItem = tvTrucks.getSelectionModel().getSelectedItem();
            truckConnector.deleteRecord(selectedItem, selectedItem.getId());
            showTrucks();
        }
    }

    @FXML
    private void addTruck() throws IOException {
        Stage stage = (Stage) rootID.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/logi/add-truck.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void showTrucks() {
        ObservableList<Truck> list = truckConnector.getRecords();

        colTruckID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colCapacity.setCellValueFactory(new PropertyValueFactory<>("capacity"));

        tvTrucks.setItems(list);
    }

    @FXML
    private void clickView () throws IOException {
        if (!tvTrucks.getSelectionModel().isEmpty()) {
            Stage stage = (Stage) rootID.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/logi/truck-info.fxml"));
            Parent root = loader.load();

            TruckInfoController controller = loader.getController();
            controller.setTruckIdLabelText(tvTrucks.getSelectionModel().getSelectedItem());
            controller.setTruckCapacityLabelText(tvTrucks.getSelectionModel().getSelectedItem());
            controller.showTrips(tvTrucks.getSelectionModel().getSelectedItem());

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }
}