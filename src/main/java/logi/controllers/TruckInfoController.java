package logi.controllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
    public TableView <Trip>  tvTrips;
    public TableColumn <Trip, String> colTruck;
    public TableColumn <Trip, String> colOriginFacility;
    public TableColumn <Trip, String> colDestinationFacility;
    public TableColumn <Trip, String> colStartDate;
    public TableColumn <Trip, String> colEndDate;
    public TableColumn <Trip, String> colTransitTime;
    public Label truckIdLabelText;
    public Label truckCapacityLabelText;



    private TruckConnector truckConnector;

    public String originalTruckID;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        truckConnector = new TruckConnector();
    }

    public void setTruckIdLabelText(Truck truck) {
        originalTruckID = truck.getId();
        truckIdLabelText.setText(String.valueOf(truck));
    }

    public void setTruckCapacityLabelText(Truck truck) {
        truckCapacityLabelText.setText(String.valueOf(truck.getCapacity()));
    }

    @FXML
    private void clickEdit () throws IOException {
        Stage stage = (Stage) truckInfoRootID.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/logi/view-truck.fxml"));
        Parent root = loader.load();

        Truck truck = new Truck(truckIdLabelText.getText(),
                Integer.parseInt(truckCapacityLabelText.getText()));

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

        colTruck.setCellValueFactory(new PropertyValueFactory<>("truck"));
        colOriginFacility.setCellValueFactory(new PropertyValueFactory<>("originFacility"));
        colDestinationFacility.setCellValueFactory(new PropertyValueFactory<>("destinationFacility"));
        colStartDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        colEndDate.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        colTransitTime.setCellValueFactory(new PropertyValueFactory<>("transitTime"));

        tvTrips.setItems(list);
    }
}
