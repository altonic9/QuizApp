package gui;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import quiz.Question;
import quiz.Topic;

import java.util.ArrayList;
import java.util.Arrays;

public class questionCreationController {

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
    private TextField[] mcAnswers;


    public void initialize() {
        //get current topic and set title
        currentTopic = Topic.getById(Helper.topicUUID);
        title.setText("Add Question to \"" + currentTopic.getName() + "\"");

        //populate ComboBoxes, can't do it in fxml or scenebuilder
        typeCB.setItems(FXCollections.observableArrayList("Multiple Choice", "Text Answer"));
        amountAnswersCB.setItems(FXCollections.observableArrayList("1", "2", "3", "4"));

        mcAnswers = new TextField[] {mcAnswer1, mcAnswer2, mcAnswer3, mcAnswer4};

        // disable all "changeable fields" at beginning
        crrAnswerTxt.setDisable(true);
        amountAnswersCB.setDisable(true);
        crrAnswerCB.setDisable(true);
        for (TextField t: mcAnswers)
            t.setDisable(true);

    }

    public void onChangeTypeComboBox() {
        // set global questionType
        questionType = typeCB.getValue().equals("Text Answer") ? "txt" : "mc";

        // disable/enable according input fields
        if (questionType.equals("txt")) {
            crrAnswerTxt.setDisable(false);
            amountAnswersCB.setDisable(true);
        }
        else {
            crrAnswerTxt.setDisable(true);
            amountAnswersCB.setDisable(false);
        }

        // always disable these fields on type change
        crrAnswerCB.setDisable(true);
        for (TextField t: mcAnswers)
            t.setDisable(true);

        //reset amount answers & correct answer combobox
        amountAnswersCB.setValue(null);
        crrAnswerCB.setValue(null);
    }

    public void onChangeAmountAnswers() {

        // catch error when amountAnswersCB is resetted
        if (amountAnswersCB.getValue() == null)
            return;

        // activate correct answer Combobox
        crrAnswerCB.setDisable(false);

        // change content of crrAnswer ComboBox
        int amount = Integer.parseInt(amountAnswersCB.getValue());
        String[] pssAnswers = Arrays.copyOfRange(new String[] {"1", "2", "3", "4"}, 0, amount);
        crrAnswerCB.setItems(FXCollections.observableArrayList(pssAnswers));

        // disable all answer textfields
        for (TextField t: mcAnswers)
            t.setDisable(true);
        // re-enable those needed
        for (int i=1; i<=amount; i++) {
            inputArea.lookup("#mcAnswer"+i).setDisable(false);
        }

    }

    public void cancelButton() {
        Main.changeScene("editor.fxml");
    }

    public void addButton() {
        if (!isfilledOut()) {
            showAlert("Missing Information", "Please fill out all Input Fields");
            return;
        }

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

    private boolean isfilledOut() {
        // check if type is set first, otherwise following if-statement will fail
        if (questionType==null)
            return false;

        if (questionType.equals("txt")) {
            return !(questionTxt.getText().trim().equals("") || crrAnswerTxt.getText().trim().equals(""));
        }
        else {  //multiple choice

            // check every active answer field
            for (TextField t : mcAnswers) {
                if ( !t.isDisabled() && t.getText().trim().equals("") )
                    return false;
            }

            // check crrAnswer ComboBox
            return crrAnswerCB.getValue() != null;
        }

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
