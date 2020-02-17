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
    private MenuItem profileManagmentMI;

    @FXML
    private MenuItem exitMI;

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
        Main.changeScene("editor.fxml");
    }

    @FXML
    void openEditExistingTopic(ActionEvent event) {

    }

    @FXML
    void openStartQuiz(ActionEvent event) { Main.changeScene("quizMenu.fxml"); }

    @FXML
    void openProfileManagment(ActionEvent event) { Main.changeScene("profileManagment.fxml"); }

    @FXML
    void exit(ActionEvent event) { System.exit(0); }
}
