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
import logi.models.Truck;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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
        Connection conn = getConnection();
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
        Connection conn = getConnection();
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

    public Connection getConnection() {
        Connection conn;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/logistics_planner", "root", "password");
            return conn;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

    private void executeQuery(String query) {
        Connection conn = getConnection();
        Statement st;
        try {
            st = conn.createStatement();
            st.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateRecord() {
        ArrayList<String> queries = new ArrayList<>();

        queries.add("UPDATE trips " + "SET truckId = '" + truckChoiceBox.getValue() + "' WHERE tripID = " + tripID + ";");
        queries.add("UPDATE trips " + "SET originFacilityID = '" + originFacilityChoiceBox.getValue() + "' WHERE tripID = " + tripID + ";");
        queries.add("UPDATE trips " + "SET destinationFacilityID  = '" + destinationFacilityChoiceBox.getValue() + "' WHERE tripID = " + tripID + ";");
        queries.add("UPDATE trips " + "SET startDate  = '" + calendarInput.getValue() + "' WHERE tripID = " + tripID + ";");


        for (String query: queries) {
            executeQuery(query);
        }
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
