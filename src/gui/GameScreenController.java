package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import quiz.Profile;
import quiz.Question;
import quiz.Topic;

import java.util.ArrayList;
import java.util.Collections;


public class GameScreenController {

    @FXML
    private Label questionText;
    @FXML
    private Button answerBtn1;
    @FXML
    private Button answerBtn2;
    @FXML
    private Button answerBtn3;
    @FXML
    private Button answerBtn4;
    @FXML
    private TextField answerTF;
    @FXML
    private HBox txtAnswerInput;
    @FXML
    private GridPane mcInput;

    private Profile chosenProfile;
    private Topic chosenTopic;
    private int questionAmount;
    private boolean randomized;

    private Button[] answerBtns;
    private int questionCounter;
    private ArrayList<Question> questions;
    private Question currentQuestion;

    
    public void initialize() {
        // preserve global vars and set to null
        chosenProfile = Helper.gameProfile;
        chosenTopic = Helper.gameTopic;
        questionAmount = Helper.questionAmount;
        randomized = Helper.randomized;

        Helper.gameTopic = null;
        Helper.gameProfile = null;
        Helper.questionAmount = -1;
        Helper.randomized = null;

        answerBtns = new Button[] {answerBtn1, answerBtn2, answerBtn3, answerBtn4};

        // prepare questions and set currentQuestion to first one
        prepareQuestions();
        //display first question
        setScreen(currentQuestion);

    }

    private void prepareQuestions() {
        // prepares questions and return first one

        questions = chosenTopic.getAllQuestions();
        //shuffle
        if (randomized)
            Collections.shuffle(questions);
        //get specified amount
        questions = new ArrayList<Question>(questions.subList(0, questionAmount));

        // currentQuestion globally available
        currentQuestion = questions.get(0);
        questionCounter = 0;
    }

    private void setScreen(Question q) {
        // fill screen with question content
        questionText.setText(q.getText());

        if (q.getType().equals("mc")) {
            //enable button array field
            mcInput.setVisible(true);
            // disable textfield
            txtAnswerInput.setVisible(false);

            //label and dis/enable needed answer buttons
            String[] possAnswers = q.getAnswers();
            for ( int i=0; i<4; i++) {
                try {
                    answerBtns[i].setVisible(true);
                    answerBtns[i].setText(possAnswers[i]);
                }
                catch (Exception ex) {
                    answerBtns[i].setVisible(false);
                }
            }
        }
        else {
            // enable textfield
            txtAnswerInput.setVisible(true);
            // disable button array field
            mcInput.setVisible(false);
        }

    }

    public void processAnswer(ActionEvent event) {

        Boolean crr;
        if (currentQuestion.getType().equals("mc")) {
            // which button was pressed?
            Button btn = (Button) event.getSource();
            String id = btn.getId();
            // get last char of pressed buttons id and convert to int
            int chosenAnswer = Character.getNumericValue(id.charAt(id.length() - 1));
            crr = currentQuestion.isCrrAnswer(chosenAnswer);
        }
        else {
            crr = currentQuestion.isCrrAnswer(answerTF.getText());
        }

        // update profile statistics
        chosenProfile.addToHistory(chosenTopic.getId(), currentQuestion.getId(), crr);

        //show result
        showAlert("Result", crr.toString());

        nextQuestion();

    }

    public void nextQuestion() {
        questionCounter++;
        currentQuestion = questions.get(questionCounter);
        setScreen(currentQuestion);
    }

    public void cancel() {
        Main.changeScene("startScreen.fxml");
    }

    private void showAlert(String title, String text) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);

        // Header Text: null
        alert.setHeaderText(null);
        alert.setContentText(text);

        alert.showAndWait();
    }
}
