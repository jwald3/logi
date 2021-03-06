package logi;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Objects;

public class Main extends Application {

    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("add-truck.fxml")));
        primaryStage.setTitle("Logistics Manager");
        primaryStage.setScene(new Scene(root));
        primaryStage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("truck-clipart.jpg"))));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
