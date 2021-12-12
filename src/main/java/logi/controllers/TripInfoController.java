package logi.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import logi.models.Facility;
import logi.models.Trip;
import logi.models.Truck;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class TripInfoController implements Initializable {
    public BorderPane viewSingleTripRootID;

    public Label truckNameLabelText;
    public Label truckCapacityLabelText;
    public Label originFacilityNameText;
    public Label originFacilityAddressLabelText;
    public Label destinationFacilityNameLabelText;
    public Label destinationFacilityAddressLabelText;
    public Label startDateLabelText;
    public Label endDateLabelText;


    public int tripId;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void setTruckIdTextField(Truck truck) {
        truckNameLabelText.setText(truck.getId());
    }

    public void setTruckCapacityTextField(Truck truck) {
        truckCapacityLabelText.setText(String.valueOf(truck.getCapacity()));
    }

    public void setOriginFacilityIDTextField(Facility facility) {
        originFacilityNameText.setText(facility.getID());
    }

    public void setOriginFacilityAddressTextField(Facility facility) {
        originFacilityAddressLabelText.setText(facility.getAddress());
    }

    public void setDestinationFacilityIDTextField(Facility facility) {
        destinationFacilityNameLabelText.setText(facility.getID());
    }

    public void setDestinationFacilityAddressTextField(Facility facility) {
        destinationFacilityAddressLabelText.setText(facility.getAddress());
    }

    public void setStartDateTextField(Trip trip) {
        startDateLabelText.setText(String.valueOf(trip.getStartDate()));
    }

    public void setEndDateTextField(Trip trip) {
        endDateLabelText.setText(String.valueOf(trip.getEndDate()));
    }

    public void setTripId(int id) {
        this.tripId = id;
    }

    @FXML
    private void clickView () throws IOException {
        Stage stage = (Stage) viewSingleTripRootID.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/logi/view-trip.fxml"));
        Parent root = loader.load();

        ViewSingleTripController controller = loader.getController();
        controller.setTruckChoiceBox(truckNameLabelText.getText());
        controller.setOriginFacilityChoiceBox(originFacilityNameText.getText());
        controller.setDestinationFacilityChoiceBox(destinationFacilityNameLabelText.getText());
        controller.setStartDateInput(LocalDate.parse(startDateLabelText.getText()));
        controller.setEndDateInput(LocalDate.parse(endDateLabelText.getText()));
        controller.setTripID(tripId);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void cancel() throws IOException {
        Stage stage = (Stage) viewSingleTripRootID.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/logi/view-trips.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}