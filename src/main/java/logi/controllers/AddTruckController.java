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

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ResourceBundle;


public class AddTruckController implements Initializable {

    @FXML
    public TextField trailerIdTextField;
    public TextField capacityTextField;
    public BorderPane rootID;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    private void submitTruckForm() {
        insertRecord();
    }

    @FXML
    private void viewTrucks() throws IOException {
        Stage stage = (Stage) rootID.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/logi/view-trucks.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
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

    private void insertRecord() {
        String query = "INSERT INTO trucks VALUES ('"
                + trailerIdTextField.getText() + "', "
                + capacityTextField.getText() + ")";
        executeQuery(query);
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


    public void onAddNewTruckMenuItemClick(ActionEvent event) throws IOException {
        Stage stage = (Stage) rootID.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/logi/add-truck.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void onAddNewFacilityMenuItemClick(ActionEvent event) throws IOException {
        Stage stage = (Stage) rootID.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/logi/add-facility.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void onAddNewTripMenuItemClick(ActionEvent event) throws IOException {
        Stage stage = (Stage) rootID.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/logi/add-trip.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void onViewTrucksMenuItemClick(ActionEvent event) throws IOException {
        Stage stage = (Stage) rootID.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/logi/view-trucks.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void onViewFacilitiesMenuItemClick(ActionEvent event) throws IOException {
        Stage stage = (Stage) rootID.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/logi/view-facilities.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void onViewTripsMenuItemClick(ActionEvent event) throws IOException {
        Stage stage = (Stage) rootID.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/logi/view-trips.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
