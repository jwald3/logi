package logi.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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

public class ViewTripController implements Initializable {

    @FXML
    public BorderPane rootID;

    @FXML
    public TableView<Trip> tvTrips;
    public TableColumn<Trip, String>  colTruck;
    public TableColumn<Trip, String> colOriginFacility;
    public TableColumn<Trip, String> colDestinationFacility;
    public TableColumn<Trip, String> colStartDate;

    private TripConnector tripConnector;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tripConnector = new TripConnector();
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

    public ObservableList<Trip> getTripsList() {
        ObservableList<Trip> tripsList = FXCollections.observableArrayList();
        Connection conn = tripConnector.getConnection();
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
        if (tvTrips.getSelectionModel().getSelectedItem() != null) {
            Trip selectedItem = tvTrips.getSelectionModel().getSelectedItem();
            tripConnector.deleteRecord(selectedItem, "");
            showTrips();
        }
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
