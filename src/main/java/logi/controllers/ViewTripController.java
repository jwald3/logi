package logi.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import logi.models.Facility;
import logi.models.Trip;
import logi.models.Truck;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ViewTripController implements Initializable {

    @FXML
    public BorderPane rootID;

    @FXML
    public TableView<Trip> tvTrips;
    public TableColumn<Trip, String>  colTruck;
    public TableColumn<Trip, String> colOriginFacility;
    public TableColumn<Trip, String> colDestinationFacility;
    public TableColumn<Trip, String> colStartDate;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showTrips();
    }

    @FXML
    private void addTrip() throws IOException {
        Stage stage = (Stage) rootID.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/logi/add-trip.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
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

    public ObservableList<Trip> getTripsList() {
        ObservableList<Trip> tripsList = FXCollections.observableArrayList();
        Connection conn = getConnection();
        String query = "SELECT * FROM trips";
        Statement st;
        ResultSet rs;

        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
            Trip trip;
            while (rs.next()) {
                trip = new Trip(
                        new Truck(rs.getString("truckId"), 0),
                        new Facility(rs.getString("originFacilityId"), ""),
                        new Facility(rs.getString("destinationFacilityId"), ""),
                        LocalDate.parse(rs.getString("startDate")),
                        rs.getString("tripID")
                );
                tripsList.add(trip);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tripsList;
    }

    public void showTrips() {
        ObservableList<Trip> list = getTripsList();

        colTruck.setCellValueFactory(new PropertyValueFactory<Trip, String>("truck"));
        colOriginFacility.setCellValueFactory(new PropertyValueFactory<Trip, String>("originFacility"));
        colDestinationFacility.setCellValueFactory(new PropertyValueFactory<Trip, String>("destinationFacility"));
        colStartDate.setCellValueFactory(new PropertyValueFactory<Trip, String>("startDate"));

        tvTrips.setItems(list);
    }

    @FXML
    private void clickDelete(ActionEvent event) {

        Trip selectedItem = tvTrips.getSelectionModel().getSelectedItem();
        String query = "DELETE FROM trips WHERE truckId ='" + selectedItem.getTruck().toString()
                + "' AND originFacilityID = '" + selectedItem.getOriginFacility().toString()
                + "' AND destinationFacilityID = '" + selectedItem.getDestinationFacility().toString() + "';";
        executeQuery(query);
        showTrips();
    }

    @FXML
    private void clickView (ActionEvent event) throws IOException {
        Stage stage = (Stage) rootID.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/logi/view-trip.fxml"));
        Parent root = loader.load();

        ViewSingleTripController controller = loader.getController();
        controller.setTruckChoiceBox(tvTrips.getSelectionModel().getSelectedItem().getTruck());
        controller.setOriginFacilityChoiceBox(tvTrips.getSelectionModel().getSelectedItem().getOriginFacility());
        controller.setDestinationFacilityChoiceBox(tvTrips.getSelectionModel().getSelectedItem().getDestinationFacility());
        controller.setCalendarInput(tvTrips.getSelectionModel().getSelectedItem().getStartDate());
        controller.setTripID(tvTrips.getSelectionModel().getSelectedItem().getFacilityID());

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
