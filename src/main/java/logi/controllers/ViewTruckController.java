package logi.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import logi.models.Truck;
import logi.util.TruckConnector;

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

    private TruckConnector truckConnector;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        truckConnector = new TruckConnector();
        showTrucks();
    }

    @FXML
    private void clickDelete(ActionEvent event) {
        if (tvTrucks.getSelectionModel().getSelectedItem() != null) {
            Truck selectedItem = tvTrucks.getSelectionModel().getSelectedItem();
            truckConnector.deleteRecord(selectedItem, selectedItem.getId());
            showTrucks();
        }
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
        ObservableList<Truck> list = truckConnector.getRecords();

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

    public ObservableMap<String, Truck> getTrucksMap() {
        ObservableMap<String, Truck> trucksMap = FXCollections.observableHashMap();
        Connection conn = getConnection();
        String query = "SELECT * FROM trucks";
        Statement st;
        ResultSet rs;

        try {
            st = conn.createStatement(); // connect to database
            rs = st.executeQuery(query);
            Truck truck;
            while (rs.next()) {
                truck = new Truck(
                        rs.getString("truckID"),
                        rs.getInt("capacity")
                );
                trucksMap.put(truck.getId(), truck);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return trucksMap;
    }

    public void showMappedTrucks() {
        ObservableList<Truck> list = truckConnector.getRecords();

        colTruckID.setCellValueFactory(new PropertyValueFactory<Truck, String>("id"));
        colCapacity.setCellValueFactory(new PropertyValueFactory<Truck, Integer>("capacity"));

        tvTrucks.setItems(list);
    }
}