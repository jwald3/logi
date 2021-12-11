package logi.controllers;

import javafx.collections.ObservableList;
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

    public void showFacilities() {
        ObservableList<Facility> list = facilityConnector.getRecords();

        colName.setCellValueFactory(new PropertyValueFactory<>("ID"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));

        tvFacilities.setItems(list);
    }

    @FXML
    private void clickDelete() {
        if (tvFacilities.getSelectionModel().getSelectedItem() != null) {
            Facility selectedItem = tvFacilities.getSelectionModel().getSelectedItem();
            facilityConnector.deleteRecord(selectedItem, selectedItem.getID());
            showFacilities();
        }
    }

    @FXML
    private void clickView () throws IOException {
        if (tvFacilities.getSelectionModel().getSelectedItem() != null) {
            Stage stage = (Stage) rootID.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/logi/facility-info.fxml"));
            Parent root = loader.load();

            FacilityInfoController controller = loader.getController();
            controller.setFacilityNameLabelValue(tvFacilities.getSelectionModel().getSelectedItem());
            controller.setFacilityAddressLabelValue(tvFacilities.getSelectionModel().getSelectedItem());
            controller.showTrips(tvFacilities.getSelectionModel().getSelectedItem());

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }
}