package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class StartWindowController {

    @FXML
    private MenuBar menuBar;

    @FXML
    private MenuItem createNewTopicMI;

    @FXML
    private MenuItem editExistingTopicMI;

    @FXML
    private MenuItem showProfileMI;

    @FXML
    private MenuItem editExistingProfileMI;

    @FXML
    private MenuItem deleteProfileMI;

    @FXML
    private Button startQuizBTN;

    @FXML
    private Button exitGameBTN;

    @FXML
    void closeGame(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    void openCreateNewTopic() {
        Main.changeScene("topicCreation.fxml");
    }

    @FXML
    void openDeleteProfile(ActionEvent event) {

    }

    @FXML
    void openEditExistingTopic(ActionEvent event) {

    }

    @FXML
    void openExistingProfile(ActionEvent event) {

    }

    @FXML
    void openShowProfile(ActionEvent event) {

    }

    @FXML
    void openStartQuiz(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("quizMenu.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(new Scene(root1));
            stage.show();
            final Node source = (Node) event.getSource();
            final Stage currentStage = (Stage) source.getScene().getWindow();
            currentStage.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
