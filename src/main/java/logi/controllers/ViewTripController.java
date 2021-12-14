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
import logi.models.Trip;
import logi.util.TripConnector;

import java.io.IOException;
import java.net.URL;
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
    public TableColumn<Trip, String> colEndDate;
    public TableColumn<Trip, String> colTransitTime;


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

    public void showTrips() {
        ObservableList<Trip> list = tripConnector.getRecords();

        colTruck.setCellValueFactory(new PropertyValueFactory<>("truck"));
        colOriginFacility.setCellValueFactory(new PropertyValueFactory<>("originFacility"));
        colDestinationFacility.setCellValueFactory(new PropertyValueFactory<>("destinationFacility"));
        colStartDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        colEndDate.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        colTransitTime.setCellValueFactory(new PropertyValueFactory<>("transitTime"));

        tvTrips.setItems(list);
    }

    @FXML
    private void clickDelete() {
        if (tvTrips.getSelectionModel().getSelectedItem() != null) {
            Trip selectedItem = tvTrips.getSelectionModel().getSelectedItem();
            tripConnector.deleteRecord(selectedItem, "");
            showTrips();
        }
    }

    @FXML
    private void clickView () throws IOException {
        if (tvTrips.getSelectionModel().getSelectedItem() != null) {
            System.out.println(tvTrips.getSelectionModel().getSelectedItem().getTripId());

            Stage stage = (Stage) rootID.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/logi/trip-info.fxml"));
            Parent root = loader.load();

            TripInfoController controller = loader.getController();
            controller.setTruckIdTextField(tvTrips.getSelectionModel().getSelectedItem().getTruck());
            controller.setTruckCapacityTextField(tvTrips.getSelectionModel().getSelectedItem().getTruck());
            controller.setOriginFacilityIDTextField(tvTrips.getSelectionModel().getSelectedItem().getOriginFacility());
            controller.setOriginFacilityAddressTextField(tvTrips.getSelectionModel().getSelectedItem().getOriginFacility());
            controller.setDestinationFacilityIDTextField(tvTrips.getSelectionModel().getSelectedItem().getDestinationFacility());
            controller.setDestinationFacilityAddressTextField(tvTrips.getSelectionModel().getSelectedItem().getDestinationFacility());
            controller.setStartDateTextField(tvTrips.getSelectionModel().getSelectedItem());
            controller.setEndDateTextField(tvTrips.getSelectionModel().getSelectedItem());
            controller.setTripId(tvTrips.getSelectionModel().getSelectedItem().getTripId());
            controller.setTransitTimeTextLabel(tvTrips.getSelectionModel().getSelectedItem());

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }
}