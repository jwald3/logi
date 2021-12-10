package logi.controllers;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import logi.models.Trip;
import logi.models.Truck;
import logi.util.TruckConnector;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TruckInfoController implements Initializable {

    @FXML
    public BorderPane truckInfoRootID;
    @FXML
    public TextField truckIdTextField;
    @FXML
    public TextField capacityTextField;

    @FXML
    public TableView <Trip>  tvTrips;
    public TableColumn <Trip, String> colTruck;
    public TableColumn <Trip, String> colOriginFacility;
    public TableColumn <Trip, String> colDestinationFacility;
    public TableColumn <Trip, String> colStartDate;

    private TruckConnector truckConnector;

    public String originalTruckID;
    private int originalCapacity;

    private Truck truck;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        truckConnector = new TruckConnector();
    }

    public void setTruckIdTextField(Truck truck) {
        originalTruckID = truck.getId();
        truckIdTextField.setText(String.valueOf(truck));
    }

    public void setCapacityTextField(Truck truck) {
        int originalCapacity = truck.getCapacity();
        capacityTextField.setText(String.valueOf(truck.getCapacity()));
    }

    @FXML
    private void clickEdit (ActionEvent event) throws IOException {
        Stage stage = (Stage) truckInfoRootID.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/logi/view-truck.fxml"));
        Parent root = loader.load();

        Truck truck = new Truck(truckIdTextField.getText(),
                Integer.parseInt(capacityTextField.getText()));

        ViewSingleTruckController controller = loader.getController();
        controller.setTruckIdTextField(truck);
        controller.setCapacityTextField(truck);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void cancel() throws IOException {
        Stage stage = (Stage) truckInfoRootID.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/logi/view-trucks.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void showTrips(Truck truck) {
        ObservableList<Trip> list = truckConnector.getRelatedRecords(truck);

        colTruck.setCellValueFactory(new PropertyValueFactory<Trip, String>("truck"));
        colOriginFacility.setCellValueFactory(new PropertyValueFactory<Trip, String>("originFacility"));
        colDestinationFacility.setCellValueFactory(new PropertyValueFactory<Trip, String>("destinationFacility"));
        colStartDate.setCellValueFactory(new PropertyValueFactory<Trip, String>("startDate"));

        tvTrips.setItems(list);
    }
}
