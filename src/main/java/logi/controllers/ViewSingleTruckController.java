package logi.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import logi.models.Truck;
import logi.util.TruckConnector;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ResourceBundle;

public class ViewSingleTruckController implements Initializable {
    @FXML
    public BorderPane viewSingleTruckRootID;
    public TextField truckIdTextField;
    public TextField capacityTextField;
    private String originalTruckID;

    private TruckConnector truckConnector;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        truckConnector = new TruckConnector();
    }


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
        Truck newTruck = new Truck(truckIdTextField.getText(), Integer.parseInt(capacityTextField.getText()));

        Stage stage = (Stage) viewSingleTruckRootID.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/logi/truck-info.fxml"));
        Parent root = loader.load();

        TruckInfoController controller = loader.getController();
        controller.setTruckIdLabelText(newTruck);
        controller.setTruckCapacityLabelText(newTruck);
        controller.showTrips(newTruck);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void updateRecord() throws IOException {
        Truck truck = new Truck(truckIdTextField.getText(),
                Integer.parseInt(capacityTextField.getText()));

        truckConnector.updateRecord(truck, originalTruckID);
        viewTrucks();
    }

    private void executeQuery(String query) {
        Connection conn = truckConnector.getConnection();
        Statement st;
        try {
            st = conn.createStatement();
            st.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void clickDelete() throws IOException {
        String query = "DELETE FROM trucks WHERE truckID = '" + originalTruckID + "';";
        executeQuery(query);
        viewTrucks();
    }

}