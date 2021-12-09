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
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import logi.models.Facility;
import logi.util.FacilityConnector;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class ViewFacilityController implements Initializable {

    @FXML
    public BorderPane rootID;
    public TableView<Facility> tvFacilities;
    public TableColumn<Facility, String> colName;
    public TableColumn<Facility, String> colAddress;

    private FacilityConnector facilityConnector;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        facilityConnector = new FacilityConnector();
        showFacilities();
    }

    @FXML
    private void addFacility() throws IOException {
        Stage stage = (Stage) rootID.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/logi/add-facility.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public ObservableList<Facility> getFacilitiesList() {
        ObservableList<Facility> facilitiesList = FXCollections.observableArrayList();
        Connection conn = getConnection();
        String query = "SELECT * FROM facilities";
        Statement st;
        ResultSet rs;

        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
            Facility facility;
            while (rs.next()) {
                facility = new Facility(
                        rs.getString("facilityName"),
                        rs.getString("facilityAddress")
                );
                facilitiesList.add(facility);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return facilitiesList;
    }

    public void showFacilities() {
        ObservableList<Facility> list = getFacilitiesList();

        colName.setCellValueFactory(new PropertyValueFactory<Facility, String>("ID"));
        colAddress.setCellValueFactory(new PropertyValueFactory<Facility, String>("address"));

        tvFacilities.setItems(list);
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
    private void clickDelete(ActionEvent event) {
        if (tvFacilities.getSelectionModel().getSelectedItem() != null) {
            Facility selectedItem = tvFacilities.getSelectionModel().getSelectedItem();
            facilityConnector.deleteRecord(selectedItem, selectedItem.getID());
            showFacilities();
        }
    }

    @FXML
    private void clickView (ActionEvent event) throws IOException {
        Stage stage = (Stage) rootID.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/logi/view-facility.fxml"));
        Parent root = loader.load();

        ViewSingleFacilityController controller = loader.getController();
        controller.setFacilityNameTextField(tvFacilities.getSelectionModel().getSelectedItem());
        controller.setFacilityAddressTextField(tvFacilities.getSelectionModel().getSelectedItem());

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
