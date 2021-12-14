package logi.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import logi.models.Facility;
import logi.models.State;
import logi.util.FacilityConnector;
import logi.util.StateConnector;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AddFacilityController implements Initializable {
    @FXML
    public BorderPane rootID;
    public TextField facilityNameTextField;
    public TextField facilityAddressTextField;
    public TextField facilityCityTextField;
    public ComboBox<String> facilityStateChoiceBox;
    public TextField zipCodeTextField;

    private FacilityConnector facilityConnector;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        facilityConnector = new FacilityConnector();
        StateConnector stateConnector = new StateConnector();

        for (State state: stateConnector.getRecords()) {
            facilityStateChoiceBox.getItems().add(state.getStateAbbrev());
        }
    }

    @FXML
    private void submitFacilityForm() {
        insertRecord();
    }

    private void insertRecord() {
        if (!facilityNameTextField.getText().isEmpty()
                && !facilityAddressTextField.getText().isEmpty()
                && !facilityCityTextField.getText().isEmpty()
                && facilityStateChoiceBox.getValue() != null
                && !zipCodeTextField.getText().isEmpty()
        ) {

           String address = facilityAddressTextField.getText() + ", " +
                   facilityCityTextField.getText() + ", " + facilityStateChoiceBox.getValue() +
                   " " + zipCodeTextField.getText();


            Facility facility = new Facility(facilityNameTextField.getText(),
                    address);


            facilityConnector.insertRecord(facility);
            resetValues();

        } else {
            generateError();
        }
    }

    @FXML
    private void viewFacilities() throws IOException {
        Stage stage = (Stage) rootID.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/logi/view-facilities.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private void resetValues() {
        facilityNameTextField.setText("");
        facilityAddressTextField.setText("");
        facilityCityTextField.setText("");
        facilityStateChoiceBox.setValue(null);
        zipCodeTextField.setText("");
    }

    private void generateError() {
        ArrayList<String> issues = new ArrayList<>();

        if (facilityNameTextField.getText().isEmpty()) { issues.add("Facility name was not provided"); }
        if (facilityAddressTextField.getText().isEmpty()) { issues.add("Facility address was not provided"); }


        StringBuilder stringBuilder = new StringBuilder();

        for (String string: issues) {
            stringBuilder.append(string).append("\n");
        }

        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setHeaderText("Input not valid");
        errorAlert.setContentText(stringBuilder.toString());
        errorAlert.showAndWait();
    }
}