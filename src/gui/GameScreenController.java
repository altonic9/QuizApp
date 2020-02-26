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
import java.util.HashMap;


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
    private boolean playAllTopics;

    private Button[] answerBtns;
    private int questionCounter;
    private ArrayList<Question> questions;
    private HashMap<String, String> questionsIdToTopicId;  // used if playAlltopics is checked

    private Question currentQuestion;

    
    public void initialize() {
        // preserve global vars and set to null
        chosenProfile = Helper.gameProfile;
        chosenTopic = Helper.gameTopic;
        questionAmount = Helper.questionAmount;
        randomized = Helper.randomized;
        playAllTopics = Helper.playAllTopics;

        Helper.gameTopic = null;
        Helper.gameProfile = null;
        Helper.questionAmount = -1;
        Helper.randomized = null;
        Helper.playAllTopics = null;

        answerBtns = new Button[] {answerBtn1, answerBtn2, answerBtn3, answerBtn4};

        // prepare questions and set currentQuestion to first one
        prepareQuestions();
        //display first question
        setScreen(currentQuestion);

    }

    private void prepareQuestions() {
        // prepares questions

        if (playAllTopics) {
            questions = new ArrayList<>();

            // remember topic Id
            questionsIdToTopicId = new HashMap<>();
            for (Topic t : Topic.getAllTopics()) {
                for (Question q : t.getAllQuestions()) {
                    questions.add(q);
                    questionsIdToTopicId.put(q.getId(), t.getId());
                }
            }
        }
        else {
            questions = chosenTopic.getAllQuestions();
        }

        //shuffle
        if (randomized)
            Collections.shuffle(questions);
        //get specified amount
        questions = new ArrayList<Question>(questions.subList(0, questionAmount));

        // currentQuestion is globally available
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
            String[] possAnswers = q.getPossibleAnswers();
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
            // clear last answer
            answerTF.setText(null);
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
        else { // it's a text question
            // check if answer text box contains something
            if (answerTF.getText()==null || answerTF.getText().trim().equals("")) {
                GuiUtil.showAlert("Missing Information", "Please enter an Answer!");
                return;
            }

            crr = currentQuestion.isCrrAnswer(answerTF.getText());
        }

        // update profile statistics
        if (playAllTopics) {
            // get topic ID first
            String topicId = questionsIdToTopicId.get(currentQuestion.getId());
            chosenProfile.addToHistory(topicId, currentQuestion.getId(), crr);
        }
        else {
            chosenProfile.addToHistory(chosenTopic.getId(), currentQuestion.getId(), crr);
        }

        //show result
        if (crr) {
            GuiUtil.showAlert("Result", "RICHTIG");
        }
        else {
            GuiUtil.showAlert("Result", "FALSCH!\n\nRichtige Antwort ist:\n" + currentQuestion.getCrrAnswerString());
        }

        nextQuestion();

    }

    public void nextQuestion() {
        questionCounter++;
        if (questionCounter < questions.size()) {
            currentQuestion = questions.get(questionCounter);
            setScreen(currentQuestion);
        }
        else {
            GuiUtil.showAlert("End", "You finished the game. \nA game summary is planned \nYou can go to \"Profiles\" to see your overall statistics");
            Main.changeScene("startScreen.fxml");
        }

    }

    public void cancel() {
        Main.changeScene("startScreen.fxml");
    }
}
