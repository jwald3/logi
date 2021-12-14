package logi.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AddTripController implements Initializable {

    @FXML
    public BorderPane rootID;
    public ChoiceBox<String> truckChoiceBox;
    public ChoiceBox<String> originFacilityChoiceBox;
    public ChoiceBox<String> destinationFacilityChoiceBox;

    public DatePicker startDateDatePicker;
    public DatePicker endDateDatePicker;
    public TextField startTimeTextField;
    public TextField endTimeTextField;

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

    @FXML
    private void viewTrips() throws IOException {
        Stage stage = (Stage) rootID.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/logi/view-trips.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    private void insertRecord() {

        if (truckChoiceBox.getValue() != null
                && originFacilityChoiceBox.getValue() != null
                && destinationFacilityChoiceBox.getValue() != null
                && startDateDatePicker.getValue() != null
                && endDateDatePicker.getValue() != null
                && !startTimeTextField.getText().isEmpty()
                && !endTimeTextField.getText().isEmpty()
                && !originFacilityChoiceBox.getValue().equals(destinationFacilityChoiceBox.getValue())
        ) {
            if (startTimeTextField.getText().matches("^(?:\\d|[01]\\d|2[0-3]):[0-5]\\d$") &&
                    endTimeTextField.getText().matches("^(?:\\d|[01]\\d|2[0-3]):[0-5]\\d$")) {

                LocalTime startLocalTime = LocalTime.parse(startTimeTextField.getText());
                LocalDateTime startLocalDateTime = LocalDateTime.of(startDateDatePicker.getValue(), startLocalTime);

                LocalTime endLocalTime = LocalTime.parse(endTimeTextField.getText());
                LocalDateTime endLocalDateTime = LocalDateTime.of(endDateDatePicker.getValue(), endLocalTime);

                if (startLocalDateTime.isBefore(endLocalDateTime)) {
                    Trip trip = new Trip(
                            new Truck(truckChoiceBox.getValue(), 0),
                            new Facility(originFacilityChoiceBox.getValue(), ""),
                            new Facility(destinationFacilityChoiceBox.getValue(), ""),
                            startLocalDateTime,
                            endLocalDateTime
                    );

                    tripConnector.insertRecord(trip);

                    resetValues();
                }
            } else {
                generateError();
            }
        } else {
            generateError();
        }
    }

    private void generateError() {
        ArrayList<String> issues = new ArrayList<>();

        if (truckChoiceBox.getValue() == null) { issues.add("No truck was selected"); }
        if (originFacilityChoiceBox.getValue() == null) { issues.add("No origin facility was selected"); }
        if (destinationFacilityChoiceBox.getValue() == null) { issues.add("No destination facility was selected"); }
        if (startDateDatePicker.getValue()==null) { issues.add("No starting date was selected"); }

        if (startTimeTextField.getText().isEmpty()) {
            issues.add("No starting time was provided");
        } else if (!startTimeTextField.getText().matches("^(?:\\d|[01]\\d|2[0-3]):[0-5]\\d$")) {
            issues.add("Please ensure start time matches HH:MM format");
        }

        if (endDateDatePicker.getValue()==null) {
            issues.add("No ending date was selected");
        }

        if (endTimeTextField.getText().isEmpty()) {
            issues.add("No ending time was provided");
        } else if (!endTimeTextField.getText().matches("^(?:\\d|[01]\\d|2[0-3]):[0-5]\\d$")) {
            issues.add("Please ensure end time matches HH:MM format");
        }

        if (originFacilityChoiceBox.getValue().equals(destinationFacilityChoiceBox.getValue())) {
            issues.add("Destination facility needs to be different from origin facility");
        }

        StringBuilder stringBuilder = new StringBuilder();

        for (String string: issues) {
            stringBuilder.append(string).append("\n");
        }

        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setHeaderText("Input not valid");
        errorAlert.setContentText(stringBuilder.toString());
        errorAlert.showAndWait();
    }

    private void resetValues() {
        truckChoiceBox.setValue("");
        originFacilityChoiceBox.setValue("");
        destinationFacilityChoiceBox.setValue("");
        startDateDatePicker.setValue(LocalDate.now());
        endDateDatePicker.setValue(LocalDate.now());
        startTimeTextField.setText("");
        endTimeTextField.setText("");
    }
}