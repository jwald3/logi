package logi.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import logi.models.Truck;
import logi.util.TruckConnector;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class AddTruckController implements Initializable {

    @FXML
    public TextField trailerIdTextField;
    public TextField capacityTextField;
    public BorderPane rootID;

    private TruckConnector truckConnector;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        truckConnector = new TruckConnector();
    }

    @FXML
    private void submitTruckForm() {
        if (!trailerIdTextField.getText().isEmpty() && !capacityTextField.getText().isEmpty()) {
            try {
                Truck truck = new Truck(trailerIdTextField.getText(),
                        Integer.parseInt(capacityTextField.getText()));

                truckConnector.insertRecord(truck);
                resetValues();

            } catch (NumberFormatException ignored) {

            }
        } else {
            generateError();
        }
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

    private void generateError() {
        ArrayList<String> issues = new ArrayList<>();

        if (trailerIdTextField.getText().isEmpty()) { issues.add("Truck ID was not provided"); }
        if (capacityTextField.getText().isEmpty()) { issues.add("Truck capacity was not provided"); }


        StringBuilder stringBuilder = new StringBuilder();

        for (String string: issues) {
            stringBuilder.append(string).append("\n");
        }

        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setHeaderText("Input not valid");
        errorAlert.setContentText(stringBuilder.toString());
        errorAlert.showAndWait();
    }

    private void resetValues() {
        trailerIdTextField.setText("");
        capacityTextField.setText("");
    }
}