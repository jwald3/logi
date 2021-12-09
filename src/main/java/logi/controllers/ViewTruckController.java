package logi.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import logi.models.Trip;
import logi.models.Truck;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class ViewTruckController implements Initializable {
    public BorderPane rootID;
    public TableView<Truck> tvTrucks;
    @FXML
    public TableColumn<Truck, String> colTruckID;
    @FXML
    public TableColumn<Truck, Integer> colCapacity;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showTrucks();
    }

    @FXML
    private void clickDelete(ActionEvent event) {
        Truck selectedItem = tvTrucks.getSelectionModel().getSelectedItem();
        String query = "DELETE FROM trucks WHERE truckId ='" + selectedItem.getId() + "';";
        executeQuery(query);
        showTrucks();
    }

    @FXML
    private void addTruck() throws IOException {
        Stage stage = (Stage) rootID.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/logi/add-truck.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void showTrucks() {
        ObservableList<Truck> list = getTrucksList();

        colTruckID.setCellValueFactory(new PropertyValueFactory<Truck, String>("id"));
        colCapacity.setCellValueFactory(new PropertyValueFactory<Truck, Integer>("capacity"));

        tvTrucks.setItems(list);
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

    public ObservableList<Truck> getTrucksList() {
        ObservableList<Truck> trucksList = FXCollections.observableArrayList();
        Connection conn = getConnection();
        String query = "SELECT * FROM trucks";
        Statement st;
        ResultSet rs;

        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
            Truck truck;
            while (rs.next()) {
                truck = new Truck(
                        rs.getString("truckID"),
                        rs.getInt("capacity")
                );
                trucksList.add(truck);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return trucksList;
    }

    @FXML
    private void clickView (ActionEvent event) throws IOException {
        Stage stage = (Stage) rootID.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/logi/view-truck.fxml"));
        Parent root = loader.load();

        ViewSingleTruckController controller = loader.getController();
        controller.setTruckIdTextField(tvTrucks.getSelectionModel().getSelectedItem());
        controller.setCapacityTextField(tvTrucks.getSelectionModel().getSelectedItem());

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void clickDelete() {
        if (tvTrucks.getSelectionModel().getSelectedItem() != null) {
            Truck selectedItem = tvTrucks.getSelectionModel().getSelectedItem();
            String query = "DELETE FROM trips WHERE truckId ='" + selectedItem.getId() +"';";
            executeQuery(query);
            showTrucks();
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
