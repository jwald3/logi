package logi.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
import java.sql.Connection;
import java.sql.Statement;
import java.util.ResourceBundle;

public class ViewSingleFacilityController implements Initializable {
    public BorderPane viewSingleFacilityRootID;
    public TextField facilityNameTextField;
    public TextField facilityAddressTextField;
    public ComboBox<String> facilityStateChoiceBox;
    public TextField facilityCityTextField;
    public TextField zipCodeTextField;

    private String originalFacilityName;

    private FacilityConnector facilityConnector;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        facilityConnector = new FacilityConnector();
        StateConnector stateConnector = new StateConnector();

        for (State state: stateConnector.getRecords()) {
            facilityStateChoiceBox.getItems().add(state.getStateAbbrev());
        }
    }

    public void setFacilityNameTextField(Facility facility) {
        originalFacilityName = facility.toString();
        facilityNameTextField.setText(String.valueOf(facility));
    }

    public void setFacilityAddressTextField(Facility facility) {
        String[] addressParts = facility.getAddress().split(", ");
        String address = addressParts[0];
        String city = addressParts[1];
        String stateAndZip = addressParts[2];

        String state = stateAndZip.split(" ")[0];
        String zip = stateAndZip.split(" ")[1];

        facilityAddressTextField.setText(address);
        facilityCityTextField.setText(city);
        facilityStateChoiceBox.setValue(state);
        zipCodeTextField.setText(zip);
    }

    @FXML
    private void viewFacilities() throws IOException {
        String address = facilityAddressTextField.getText() + ", " +
                facilityCityTextField.getText() + ", " + facilityStateChoiceBox.getValue() +
                " " + zipCodeTextField.getText();

        Facility newFacility = new Facility(facilityNameTextField.getText(), address);

        Stage stage = (Stage) viewSingleFacilityRootID.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/logi/facility-info.fxml"));
        Parent root = loader.load();

        FacilityInfoController controller = loader.getController();
        controller.setFacilityNameLabelValue(newFacility);
        controller.setFacilityAddressLabelValue(newFacility);
        controller.showTrips(newFacility);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void viewAllFacilities() throws IOException {
        Stage stage = (Stage) viewSingleFacilityRootID.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/logi/view-facilities.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void updateRecord() throws IOException {
        String address = facilityAddressTextField.getText() + ", " +
                facilityCityTextField.getText() + ", " + facilityStateChoiceBox.getValue() +
                " " + zipCodeTextField.getText();

        Facility facility = new Facility(facilityNameTextField.getText(), address);

        facilityConnector.updateRecord(facility, originalFacilityName);
        viewFacilities();
    }

    @FXML
    private void clickDelete() throws IOException {
        String query = "DELETE FROM facilities WHERE facilityName ='" + originalFacilityName + "';";
        executeQuery(query);
        viewAllFacilities();
    }

    private void executeQuery(String query) {
        Connection conn = facilityConnector.getConnection();
        Statement st;
        try {
            st = conn.createStatement();
            st.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}