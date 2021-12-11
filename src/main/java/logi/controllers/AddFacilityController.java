package logi.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import logi.models.Facility;
import logi.util.FacilityConnector;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddFacilityController implements Initializable {
    @FXML
    public BorderPane rootID;
    public TextField facilityNameTextField;
    public TextField facilityAddressTextField;

    private FacilityConnector facilityConnector;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        facilityConnector = new FacilityConnector();
    }

    @FXML
    private void submitFacilityForm() {
        insertRecord();
    }

    private void insertRecord() {
        if (!facilityNameTextField.getText().isEmpty() && !facilityAddressTextField.getText().isEmpty()) {
            Facility facility = new Facility(facilityNameTextField.getText(),
                    facilityAddressTextField.getText());

            facilityConnector.insertRecord(facility);
            facilityNameTextField.setText("");
            facilityAddressTextField.setText("");
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
}