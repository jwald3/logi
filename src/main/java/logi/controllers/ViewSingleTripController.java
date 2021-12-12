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
import java.sql.Connection;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ViewSingleTripController implements Initializable {

    public ChoiceBox<String> truckChoiceBox;
    public ChoiceBox<String> originFacilityChoiceBox;
    public ChoiceBox<String> destinationFacilityChoiceBox;

    public BorderPane viewSingleTripRootID;
    public DatePicker startDateInput;
    public DatePicker endDateInput;


    private int tripID;

    private TripConnector tripConnector;
    private FacilityConnector facilityConnector;
    private TruckConnector truckConnector;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tripConnector = new TripConnector();
        truckConnector = new TruckConnector();
        facilityConnector = new FacilityConnector();

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

    private void executeQuery(String query) {
        Connection conn = tripConnector.getConnection();
        Statement st;
        try {
            st = conn.createStatement();
            st.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateRecord() throws IOException {
        Trip trip = new Trip(
                new Truck(truckChoiceBox.getValue(), 0),
                new Facility(originFacilityChoiceBox.getValue(), ""),
                new Facility(destinationFacilityChoiceBox.getValue(), ""),
                startDateInput.getValue(),
                endDateInput.getValue());

        tripConnector.updateRecord(trip, String.valueOf(tripID));
        viewTrips();
    }

    @FXML
    private void clickDelete() throws IOException {
        String query = "DELETE FROM trips WHERE tripID = " + tripID + ";";
        executeQuery(query);
        viewTrips();
    }

    @FXML
    private void viewTrips() throws IOException {
        Stage stage = (Stage) viewSingleTripRootID.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/logi/trip-info.fxml"));
        Parent root = loader.load();

        Truck truck = truckConnector.getRecord(truckChoiceBox.getValue());
        Facility originFacility = facilityConnector.getRecord(originFacilityChoiceBox.getValue());
        Facility destinationFacility = facilityConnector.getRecord(destinationFacilityChoiceBox.getValue());

        Trip trip = new Trip(
                truck, originFacility, destinationFacility, startDateInput.getValue(), endDateInput.getValue()
        );

        TripInfoController controller = loader.getController();

        controller.setTruckIdTextField(truck);
        controller.setTruckCapacityTextField(truck);
        controller.setOriginFacilityIDTextField(originFacility);
        controller.setOriginFacilityAddressTextField(originFacility);
        controller.setDestinationFacilityIDTextField(destinationFacility);
        controller.setDestinationFacilityAddressTextField(destinationFacility);
        controller.setStartDateTextField(trip);
        controller.setEndDateTextField(trip);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }


    public void setTripID(int id) {
        tripID = id;
    }

    public void setTruckChoiceBox(String truck) {
        truckChoiceBox.setValue(String.valueOf(truck));
    }

    public void setOriginFacilityChoiceBox(String facility) {
        originFacilityChoiceBox.setValue(String.valueOf(facility));
    }

    public void setDestinationFacilityChoiceBox(String facility) {
        destinationFacilityChoiceBox.setValue(String.valueOf(facility));
    }

    public void setStartDateInput(LocalDate date) {
        startDateInput.setValue(date);
    }

    public void setEndDateInput(LocalDate date) {
        endDateInput.setValue(date);
    }
}