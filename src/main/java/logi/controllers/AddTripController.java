package logi.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import logi.models.Facility;
import logi.models.Trip;
import logi.models.Truck;
import logi.util.FacilityConnector;
import logi.util.TripConnector;
import logi.util.TruckConnector;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddTripController implements Initializable {

    @FXML
    public BorderPane rootID;
    public ChoiceBox<String> truckChoiceBox;
    public ChoiceBox<String> originFacilityChoiceBox;
    public ChoiceBox<String> destinationFacilityChoiceBox;
    public DatePicker calendarInput;

    private TripConnector tripConnector;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tripConnector = new TripConnector();
        FacilityConnector facilityConnector = new FacilityConnector();
        TruckConnector truckConnector = new TruckConnector();

        for (Truck truck: truckConnector.getRecords()) {
            truckChoiceBox.getItems().add(String.valueOf(truck));
        }
        for (Facility facility: facilityConnector.getRecords()) {
            originFacilityChoiceBox.getItems().add(String.valueOf(facility));
        }

        for (Facility facility: facilityConnector.getRecords()) {
            destinationFacilityChoiceBox.getItems().add(String.valueOf(facility));
        }
    }

    @FXML
    private void submitTripForm() {
        insertRecord();
    }

    private void insertRecord() {
        Trip trip = new Trip(
                new Truck(truckChoiceBox.getValue(), 0),
                new Facility(originFacilityChoiceBox.getValue(), ""),
                new Facility(destinationFacilityChoiceBox.getValue(), ""),
                calendarInput.getValue());

        tripConnector.insertRecord(trip);
    }

    @FXML
    private void viewTrips() throws IOException {
        Stage stage = (Stage) rootID.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/logi/view-trips.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}