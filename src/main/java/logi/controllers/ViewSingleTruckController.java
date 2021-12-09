package logi.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import logi.models.Truck;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;

public class ViewSingleTruckController {
    @FXML
    public BorderPane viewSingleTruckRootID;
    public TextField truckIdTextField;
    public TextField capacityTextField;
    private String originalTruckID;


    public void setTruckIdTextField(Truck truck) {
        originalTruckID = truck.toString();
        truckIdTextField.setText(String.valueOf(truck));
    }

    public void setCapacityTextField(Truck truck) {
        int originalCapacity = truck.getCapacity();
        capacityTextField.setText(String.valueOf(truck.getCapacity()));
    }

    @FXML
    private void viewTrucks() throws IOException {
        Stage stage = (Stage) viewSingleTruckRootID.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/logi/view-trucks.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void updateRecord() {
        ArrayList<String> queries = new ArrayList<>();


        queries.add("UPDATE trucks " + "SET truckID = '" + truckIdTextField.getText() + "' WHERE truckID = '" + originalTruckID + "';");
        queries.add("UPDATE trucks " + "SET capacity = " + Integer.parseInt(capacityTextField.getText()) + " WHERE truckID = '" + originalTruckID + "';");


        for (String query: queries) {
            executeQuery(query);
        }
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

    @FXML
    private void clickDelete(ActionEvent event) throws IOException {
        String query = "DELETE FROM trucks WHERE truckID = '" + originalTruckID + "';";
        executeQuery(query);
        viewTrucks();
    }

    public void onAddNewTruckMenuItemClick(ActionEvent event) throws IOException {
        Stage stage = (Stage) viewSingleTruckRootID.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/logi/add-truck.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void onAddNewFacilityMenuItemClick(ActionEvent event) throws IOException {
        Stage stage = (Stage) viewSingleTruckRootID.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/logi/add-facility.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void onAddNewTripMenuItemClick(ActionEvent event) throws IOException {
        Stage stage = (Stage) viewSingleTruckRootID.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/logi/add-trip.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void onViewTrucksMenuItemClick(ActionEvent event) throws IOException {
        Stage stage = (Stage) viewSingleTruckRootID.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/logi/view-trucks.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void onViewFacilitiesMenuItemClick(ActionEvent event) throws IOException {
        Stage stage = (Stage) viewSingleTruckRootID.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/logi/view-facilities.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void onViewTripsMenuItemClick(ActionEvent event) throws IOException {
        Stage stage = (Stage) viewSingleTruckRootID.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/logi/view-trips.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
