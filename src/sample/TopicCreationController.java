package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class TopicCreationController {

    @FXML
    private TextField topicNameTF;

    @FXML
    private Button yesQuestionBTN;

    @FXML
    private Button noQuestionBTN;

    @FXML
    private TextField questionTF;

    @FXML
    private Button answerMultipleChoiceBTN;

    @FXML
    private Button answerTextBTN;

    @FXML
    private TextField multipleChoiceAnswer1TF;

    @FXML
    private TextField multipleChoiceAnswer2TF;

    @FXML
    private TextField multipleChoiceAnswer3TF;

    @FXML
    private RadioButton multipleChoiceAnwser1RB;

    @FXML
    private RadioButton multipleChoiceAnwser2RB;

    @FXML
    private RadioButton multipleChoiceAnwser3RB;

    @FXML
    private TextField textAnswer;

    @FXML
    private Button saveTopicBTN;

    @FXML
    private Button returnToStartBTN;

    @FXML
    private Button exitBTN;

    @FXML
    void answerMultipleChoice(ActionEvent event) {

    }

    @FXML
    void answerText(ActionEvent event) {

    }

    @FXML
    void exit(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    void multipleChoiceAnswer1(ActionEvent event) {

    }

    @FXML
    void multipleChoiceAnswer2(ActionEvent event) {

    }

    @FXML
    void multipleChoiceAnswer3(ActionEvent event) {

    }

    @FXML
    void noQuestion(ActionEvent event) {

    }

    @FXML
    void question(ActionEvent event) {

    }

    @FXML
    void returnToStart(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("startWindow.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(new Scene(root1));
            stage.show();
            Stage cstage = (Stage) returnToStartBTN.getScene().getWindow();
            cstage.close();
            /*final Node source = (Node) event.getSource();
            final Stage currentStage = (Stage) source.getScene().getWindow();
            currentStage.close();*/
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void saveTopic(ActionEvent event) {

    }

    @FXML
    void yesQuestionBTN(ActionEvent event) {

    }
}
