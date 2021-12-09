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
import java.sql.Connection;
import java.sql.Statement;
import java.util.ResourceBundle;

public class ViewSingleFacilityController implements Initializable {
    public BorderPane viewSingleFacilityRootID;
    public TextField facilityNameTextField;
    public TextField facilityAddressTextField;

    private String originalFacilityName;
    private String originalFacilityAddress;

    private FacilityConnector facilityConnector;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        facilityConnector = new FacilityConnector();
    }

    public void setFacilityNameTextField(Facility facility) {
        originalFacilityName = facility.toString();
        facilityNameTextField.setText(String.valueOf(facility));
    }

    public void setFacilityAddressTextField(Facility facility) {
        originalFacilityAddress = facility.getAddress();
        facilityAddressTextField.setText(facility.getAddress());
    }

    @FXML
    private void viewFacilities() throws IOException {
        Stage stage = (Stage) viewSingleFacilityRootID.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/logi/view-facilities.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void updateRecord() {
        Facility facility = new Facility(facilityNameTextField.getText(), facilityAddressTextField.getText());

        facilityConnector.updateRecord(facility, facility.getID());
    }

    @FXML
    private void clickDelete() throws IOException {
        String query = "DELETE FROM facilities WHERE facilityName ='" + originalFacilityName + "';";
        executeQuery(query);
        viewFacilities();
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
