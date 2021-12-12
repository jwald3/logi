package logi.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class NavMenuController {
    public MenuBar naviMenu;

    public void onAddNewTruckMenuItemClick() throws IOException {
        Stage stage = (Stage) naviMenu.getScene().getWindow();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/logi/add-truck.fxml")));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void onAddNewFacilityMenuItemClick() throws IOException {
        Stage stage = (Stage) naviMenu.getScene().getWindow();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/logi/add-facility.fxml")));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void onAddNewTripMenuItemClick() throws IOException {
        Stage stage = (Stage) naviMenu.getScene().getWindow();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/logi/add-trip.fxml")));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void onViewTrucksMenuItemClick() throws IOException {
        Stage stage = (Stage) naviMenu.getScene().getWindow();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/logi/view-trucks.fxml")));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void onViewFacilitiesMenuItemClick() throws IOException {
        Stage stage = (Stage) naviMenu.getScene().getWindow();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/logi/view-facilities.fxml")));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void onViewTripsMenuItemClick() throws IOException {
        Stage stage = (Stage) naviMenu.getScene().getWindow();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/logi/view-trips.fxml")));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
