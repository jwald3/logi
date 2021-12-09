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
import logi.models.Truck;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ViewSingleFacilityController implements Initializable {
    public BorderPane viewSingleFacilityRootID;
    public TextField facilityNameTextField;
    public TextField facilityAddressTextField;

    private String originalFacilityName;
    private String originalFacilityAddress;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

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
        ArrayList<String> queries = new ArrayList<>();


        queries.add("UPDATE facilities " + "SET facilityName = '" + facilityNameTextField.getText() + "' WHERE facilityName = '" + originalFacilityName + "';");
        queries.add("UPDATE facilities " + "SET facilityAddress = '" + facilityAddressTextField.getText() + "' WHERE facilityName = '" + originalFacilityName + "';");


        for (String query: queries) {
            executeQuery(query);
        }
    }

    @FXML
    private void clickDelete(ActionEvent event) throws IOException {
        String query = "DELETE FROM facilities WHERE facilityName ='" + originalFacilityName + "';";
        executeQuery(query);
        viewFacilities();
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


}
