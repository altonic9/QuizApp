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
    private String mode; // create new or edit question
    private Question currentQuestion; // either a new question or the one to be edited


    public void initialize() {
        // set mode
        mode = Helper.questionToEdit == null ? "new" : "edit";

        //get current topic
        currentTopic = Topic.getById(Helper.topicUUID);

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


        if (mode.equals("new")) {
            title.setText("Add Question to \"" + currentTopic.getName() + "\"");
        }
        else { // edit
            currentQuestion = Helper.questionToEdit;
            Helper.questionToEdit = null;
            title.setText("Edit Question \"" + currentQuestion.getText() + "\"");
            fillInputFields();
        }

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

    public void saveButton() {
        if (!isfilledOut()) {
            GuiUtility.showAlert("Missing Information", "Please fill out all Input Fields");
            return;
        }

        Question q = new Question();

        // in edit mode, save unique question ID
        if (mode.equals("edit")) {
            q.setId(currentQuestion.getId());
            currentTopic.deleteQuestion(currentQuestion.getId());
        }

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
            return !(questionTxt.getText()==null || crrAnswerTxt.getText()==null || questionTxt.getText().trim().equals("") || crrAnswerTxt.getText().trim().equals(""));
        }
        else {  //multiple choice

            // check every active answer field
            for (TextField t : mcAnswers) {
                if ( !t.isDisabled() && (t.getText()==null || t.getText().trim().equals("")) )
                    return false;
            }

            // check crrAnswer ComboBox
            return crrAnswerCB.getValue() != null;
        }

    }

    private void fillInputFields() {
        // fill question txt
        questionTxt.setText(currentQuestion.getText());

        // set type combobox
        String type = currentQuestion.getType().equals("txt") ? "Text Answer" : "Multiple Choice";
        typeCB.setValue(type);
        onChangeTypeComboBox();

        if (currentQuestion.getType().equals("txt")) {
            //set correct answer text if it is a text question
            crrAnswerTxt.setText(currentQuestion.getTextCrrAnswer());
        }
        else {
            // get poss. answers, set amound combobox
            String[] possAnswers = currentQuestion.getPossibleAnswers();
            amountAnswersCB.setValue(String.valueOf(possAnswers.length));
            onChangeAmountAnswers();

            // display poss. answers
            for (int i=0; i<possAnswers.length; i++) {
                mcAnswers[i].setText(possAnswers[i]);
            }

            // display correct answer
            crrAnswerCB.setValue(String.valueOf(currentQuestion.getMcCrrAnswer()));
        }
    }

}
