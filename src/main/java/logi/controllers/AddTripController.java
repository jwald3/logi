package logi.controllers;

import javafx.animation.KeyValue;
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
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import logi.models.Facility;
import logi.models.Trip;
import logi.models.TripsList;
import logi.models.Truck;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class AddTripController implements Initializable {

    @FXML
    public BorderPane rootID;
    public ChoiceBox<String> truckChoiceBox;
    public ChoiceBox<String> originFacilityChoiceBox;
    public ChoiceBox<String> destinationFacilityChoiceBox;
    public DatePicker calendarInput;

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

    @FXML
    private void submitTripForm() {
        insertRecord();
    }

    private void insertRecord() {
        String query = "INSERT INTO trips VALUES ('"
                + truckChoiceBox.getValue() + "', '"
                + originFacilityChoiceBox.getValue() + "', '"
                + destinationFacilityChoiceBox.getValue() + "', '"
                + calendarInput.getValue() + "', tripID = NULL);";

        executeQuery(query);
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

    @FXML
    private void viewTrips() throws IOException {
        Stage stage = (Stage) rootID.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/logi/view-trips.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void onAddNewTruckMenuItemClick(ActionEvent event) throws IOException {
        Stage stage = (Stage) rootID.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/logi/add-truck.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void onAddNewFacilityMenuItemClick(ActionEvent event) throws IOException {
        Stage stage = (Stage) rootID.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/logi/add-facility.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void onAddNewTripMenuItemClick(ActionEvent event) throws IOException {
        Stage stage = (Stage) rootID.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/logi/add-trip.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void onViewTrucksMenuItemClick(ActionEvent event) throws IOException {
        Stage stage = (Stage) rootID.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/logi/view-trucks.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void onViewFacilitiesMenuItemClick(ActionEvent event) throws IOException {
        Stage stage = (Stage) rootID.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/logi/view-facilities.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void onViewTripsMenuItemClick(ActionEvent event) throws IOException {
        Stage stage = (Stage) rootID.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/logi/view-trips.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
