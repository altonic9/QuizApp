package gui;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import quiz.Question;
import quiz.Topic;

import java.util.ArrayList;
import java.util.Arrays;

public class questionCreationController2 {

    @FXML
    private VBox inputArea;
    @FXML
    private Label title;
    @FXML
    private ComboBox<String> typeCB;
    @FXML
    private TextField questionTxt;
    @FXML
    private TextField crrAnswerTxt;
    @FXML
    private ComboBox<String> amountAnswersCB;
    @FXML
    private TextField mcAnswer1;
    @FXML
    private TextField mcAnswer2;
    @FXML
    private TextField mcAnswer3;
    @FXML
    private TextField mcAnswer4;
    @FXML
    private ComboBox<String> crrAnswerCB;

    private String questionType;
    private Topic currentTopic;


    public void initialize() {
        //get current topic and set title
        currentTopic = Topic.getById(Helper.topicUUID);
        title.setText("Add Question to \"" + currentTopic.getName() + "\"");

        //populate ComboBoxes, can't do it in fxml or scenebuilder
        typeCB.setItems(FXCollections.observableArrayList("Multiple Choice", "Text Answer"));
        amountAnswersCB.setItems(FXCollections.observableArrayList("1", "2", "3", "4"));
    }

    public void onChangeTypeComboBox() {
        // disable/enable according input fields

        questionType = typeCB.getValue().equals("Text Answer") ? "txt" : "mc";
        if (questionType.equals("txt"))
            toggleQuestionType(false);
        else
            toggleQuestionType(true);
    }

    public void toggleQuestionType(Boolean mcOn) {
        crrAnswerTxt.setDisable(mcOn);
        amountAnswersCB.setDisable(!mcOn);
        mcAnswer1.setDisable(!mcOn);
        mcAnswer2.setDisable(!mcOn);
        mcAnswer3.setDisable(!mcOn);
        mcAnswer4.setDisable(!mcOn);
        crrAnswerCB.setDisable(!mcOn);

        //reset amount answers combobox
        amountAnswersCB.setValue(null);
        crrAnswerCB.setValue(null);

    }

    public void onChangeAmountAnswers() {

        // catch error when amountAnswersCB is resetted
        if (amountAnswersCB.getValue() == null)
            return;

        // change content of crrAnswer ComboBox
        int amount = Integer.parseInt(amountAnswersCB.getValue());
        String[] pssAnswers = Arrays.copyOfRange(new String[] {"1", "2", "3", "4"}, 0, amount);
        crrAnswerCB.setItems(FXCollections.observableArrayList(pssAnswers));

        // disable all answer textfields
        mcAnswer1.setDisable(true);
        mcAnswer2.setDisable(true);
        mcAnswer3.setDisable(true);
        mcAnswer4.setDisable(true);
        // re-enable those needed
        for (int i=1; i<=amount; i++) {
            inputArea.lookup("#mcAnswer"+i).setDisable(false);
        }

    }

    public void cancelButton() {
        Main.changeScene("editor.fxml");
    }

    public void addButton() {
        Question q = new Question();
        q.setText(questionTxt.getText());
        q.setType(questionType);

        if (questionType.equals("txt")) {
            // get the answer textfield by id
            q.setCrrAnswer(crrAnswerTxt.getText());
        }
        else {

            // gather all possible answers
            ArrayList<String> possAnswers = new ArrayList<String>();
            int amount = Integer.parseInt(amountAnswersCB.getValue());
            for (int i=1; i<=amount; i++) {
                TextField t = (TextField) inputArea.lookup("#mcAnswer"+i);
                possAnswers.add(t.getText());
            }

            // add possible answers to question object
            q.setAnswers(possAnswers.toArray(new String[0]));

            // add correct answer to question object
            q.setCrrAnswer(Integer.parseInt(crrAnswerCB.getValue()));
        }

        currentTopic.addQuestion(q);
        currentTopic.saveToFile();

        Main.changeScene("editor.fxml");
    }

}
