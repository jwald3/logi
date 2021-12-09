package logi.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import logi.util.TripConnector;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ViewSingleTripController implements Initializable {
    
    public ChoiceBox<String> truckChoiceBox;
    public ChoiceBox<String> originFacilityChoiceBox;
    public ChoiceBox<String> destinationFacilityChoiceBox;
    public DatePicker calendarInput;
    public BorderPane viewSingleTripRootID;

    private String originalTruckChoiceBox;
    private String originalOriginFacilityChoiceBox;
    private String originalDestinationFacilityChoiceBox;
    private LocalDate originalStartDateChoiceBox;
    private String tripID;

    private TripConnector tripConnector;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tripConnector = new TripConnector();

        for (Truck truck: getTrucksList()) {
            truckChoiceBox.getItems().add(String.valueOf(truck));
        }
        for (Facility facility: getFacilitiesList()) {
            originFacilityChoiceBox.getItems().add(String.valueOf(facility));
        }

        for (Facility facility: getFacilitiesList()) {
            destinationFacilityChoiceBox.getItems().add(String.valueOf(facility));
        }
    }

    public ObservableList<Truck> getTrucksList() {
        ObservableList<Truck> trucksList = FXCollections.observableArrayList();
        Connection conn = tripConnector.getConnection();
        String query = "SELECT * FROM trucks";
        Statement st;
        ResultSet rs;

        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
            Truck truck;
            while (rs.next()) {
                truck = new Truck(
                        rs.getString("truckID"),
                        rs.getInt("capacity")
                );
                trucksList.add(truck);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return trucksList;
    }

    public ObservableList<Facility> getFacilitiesList() {
        ObservableList<Facility> facilitiesList = FXCollections.observableArrayList();
        Connection conn = tripConnector.getConnection();
        String query = "SELECT * FROM facilities";
        Statement st;
        ResultSet rs;

        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
            Facility facility;
            while (rs.next()) {
                facility = new Facility(
                        rs.getString("facilityName"),
                        rs.getString("facilityAddress")
                );
                facilitiesList.add(facility);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return facilitiesList;
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

    public void updateRecord() {
        Trip trip = new Trip(
                new Truck(truckChoiceBox.getValue(), 0),
                new Facility(originFacilityChoiceBox.getValue(), ""),
                new Facility(destinationFacilityChoiceBox.getValue(), ""),
                calendarInput.getValue());

        tripConnector.updateRecord(trip,"");
    }

    @FXML
    private void clickDelete(ActionEvent event) throws IOException {
        String query = "DELETE FROM trips WHERE tripID = " + tripID + ";";
        executeQuery(query);
        viewTrips();
    }

    @FXML
    private void viewTrips() throws IOException {
        Stage stage = (Stage) viewSingleTripRootID.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/logi/view-trips.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void setTripID(String string) {
        tripID = string;
    }

    public void setTruckChoiceBox(Truck truck) {
        originalTruckChoiceBox = truck.toString();
        truckChoiceBox.setValue(String.valueOf(truck));
    }

    public void setOriginFacilityChoiceBox(Facility facility) {
        originalOriginFacilityChoiceBox = facility.toString();
        originFacilityChoiceBox.setValue(String.valueOf(facility));
    }

    public void setDestinationFacilityChoiceBox(Facility facility) {
        originalDestinationFacilityChoiceBox = String.valueOf(facility);
        destinationFacilityChoiceBox.setValue(String.valueOf(facility));
    }

    public void setCalendarInput(LocalDate date) {
       originalStartDateChoiceBox = date;
       calendarInput.setValue(date);
    }
}
