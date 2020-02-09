package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextInputDialog;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;

public class Main extends Application {

    private static Stage primaryStage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        this.primaryStage = primaryStage;

        URL xmlUrl = getClass().getResource("startWindow.fxml");

        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent root = fxmlLoader.load(xmlUrl);

        //loader.setController(new Controller());
        //Controller controller = loader.getController();

        primaryStage.setTitle("Quiz-App");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    }


    public static void changeScene(String fxml) {
        // change scene by pointing current scene to new fxml file
        try {
            URL xmlUrl = Main.class.getResource(fxml);
            Parent pane = FXMLLoader.load(xmlUrl);
            primaryStage.getScene().setRoot(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void changeScene(Scene scene) {
        //change scene by setting new scene
        primaryStage.setScene(scene);
    }

}
