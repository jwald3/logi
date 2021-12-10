package logi.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import logi.models.Facility;
import logi.models.Trip;
import logi.models.Truck;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TripInfoController implements Initializable {
    public BorderPane viewSingleTripRootID;
    public TextField truckIdTextField;
    public TextField truckCapacityTextField;
    public TextField originFacilityIDTextField;
    public TextField originFacilityAddressTextField;
    public TextField destinationFacilityIDTextField;
    public TextField destinationFacilityAddressTextField;
    public TextField startDateTextField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void setTruckIdTextField(Truck truck) {
        truckIdTextField.setText(truck.getId());
    }

    public void setTruckCapacityTextField(Truck truck) {
        truckCapacityTextField.setText(String.valueOf(truck.getCapacity()));
    }

    public void setOriginFacilityIDTextField(Facility facility) {
        originFacilityIDTextField.setText(facility.getID());
    }

    public void setOriginFacilityAddressTextField(Facility facility) {
        originFacilityAddressTextField.setText(facility.getAddress());
    }

    public void setDestinationFacilityIDTextField(Facility facility) {
        destinationFacilityIDTextField.setText(facility.getID());
    }

    public void setDestinationFacilityAddressTextField(Facility facility) {
        destinationFacilityAddressTextField.setText(facility.getAddress());
    }

    public void setStartDateTextField(Trip trip) {
        startDateTextField.setText(String.valueOf(trip.getStartDate()));
    }

    @FXML
    private void clickView (ActionEvent event) throws IOException {
        Stage stage = (Stage) viewSingleTripRootID.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/logi/view-trip.fxml"));
        Parent root = loader.load();

        ViewSingleTripController controller = loader.getController();
        controller.setTruckChoiceBox(truckIdTextField.getText());
        controller.setOriginFacilityChoiceBox(originFacilityIDTextField.getText());
        controller.setDestinationFacilityChoiceBox(destinationFacilityIDTextField.getText());

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
