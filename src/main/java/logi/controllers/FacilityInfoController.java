package logi.controllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import logi.models.Facility;
import logi.models.Trip;
import logi.util.FacilityConnector;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class FacilityInfoController implements Initializable {
    public BorderPane facilityInfoRootID;

    @FXML
    public TableView <Trip>  tvTrips;
    public TableColumn <Trip, String> colTruck;
    public TableColumn <Trip, String> colOriginFacility;
    public TableColumn <Trip, String> colDestinationFacility;
    public TableColumn <Trip, String> colStartDate;

    public Label facilityNameLabelValue;
    public Label facilityAddressLabelValue;

    private FacilityConnector facilityConnector;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        facilityConnector = new FacilityConnector();
    }


    public void setFacilityNameLabelValue(Facility facility) {
        facilityNameLabelValue.setText(facility.getID());
    }

    public void setFacilityAddressLabelValue(Facility facility) {
        facilityAddressLabelValue.setText(facility.getAddress());
    }

    public void showTrips(Facility facility) {
        ObservableList<Trip> list = facilityConnector.getRelatedRecords(facility);

        colTruck.setCellValueFactory(new PropertyValueFactory<>("truck"));
        colOriginFacility.setCellValueFactory(new PropertyValueFactory<>("originFacility"));
        colDestinationFacility.setCellValueFactory(new PropertyValueFactory<>("destinationFacility"));
        colStartDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));

        tvTrips.setItems(list);
    }

    @FXML
    private void clickEdit () throws IOException {
        Stage stage = (Stage) facilityInfoRootID.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/logi/view-facility.fxml"));
        Parent root = loader.load();

        Facility facility = new Facility(facilityNameLabelValue.getText(), facilityAddressLabelValue.getText());

        ViewSingleFacilityController controller = loader.getController();
        controller.setFacilityNameTextField(facility);
        controller.setFacilityAddressTextField(facility);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void cancel() throws IOException {
        Stage stage = (Stage) facilityInfoRootID.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/logi/view-facilities.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
