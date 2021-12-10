package logi.controllers;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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

    public TextField facilityAddressTextField;
    public TextField facilityNameTextField;

    private FacilityConnector facilityConnector;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        facilityConnector = new FacilityConnector();
    }

    public void setFacilityNameTextField(Facility facility) {
        facilityNameTextField.setText(facility.getID());
    }

    public void setFacilityAddressTextField(Facility facility) {
        facilityAddressTextField.setText(facility.getAddress());
    }

    public void showTrips(Facility facility) {
        ObservableList<Trip> list = facilityConnector.getRelatedRecords(facility);

        colTruck.setCellValueFactory(new PropertyValueFactory<Trip, String>("truck"));
        colOriginFacility.setCellValueFactory(new PropertyValueFactory<Trip, String>("originFacility"));
        colDestinationFacility.setCellValueFactory(new PropertyValueFactory<Trip, String>("destinationFacility"));
        colStartDate.setCellValueFactory(new PropertyValueFactory<Trip, String>("startDate"));

        tvTrips.setItems(list);
    }

    @FXML
    private void clickEdit (ActionEvent event) throws IOException {
        Stage stage = (Stage) facilityInfoRootID.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/logi/view-facility.fxml"));
        Parent root = loader.load();

        Facility facility = new Facility(facilityNameTextField.getText(), facilityAddressTextField.getText());

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
