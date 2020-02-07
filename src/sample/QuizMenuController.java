package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class QuizMenuController {

    @FXML
    private Button startWithNewProfileBTN;

    @FXML
    private Button loadProfileBTN;

    @FXML
    private Button returnToMainMenu;

    @FXML
    private Button exitBTN;

    @FXML
    void exit(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    void openLoadProfile(ActionEvent event) {

    }

    @FXML
    void openStartWindow(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("startWindow.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(new Scene(root1));
            stage.show();
            Stage cstage = (Stage) returnToMainMenu.getScene().getWindow();
            cstage.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void openStartWithNewProfile(ActionEvent event) {

    }

}
