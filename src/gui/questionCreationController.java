package gui;

import java.util.ArrayList;
import java.util.Arrays;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.event.ActionEvent;

import quiz.Question;
import quiz.Topic;

public class questionCreationController {

    @FXML
    private ComboBox<String> typeComboBox;
    @FXML
    private VBox rootVbox;
    @FXML
    private TextField questionTxt;
    // globally available question type
    private String type;
    private Topic currentTopic;

    public void initialize() {
        //get current topic
        currentTopic = Topic.getById(Helper.topUUID);

        //populate ComboBox, can't do it in fxml or scenebuilder
        typeComboBox.setItems(FXCollections.observableArrayList("Multiple Choice", "Text Answer"));
    }

    public void onChangeTypeComboBox() {
        type = typeComboBox.getValue().equals("Text Answer") ? "txt" : "mc";
        if (type.equals("txt"))
            extendSceneforTextQuestion();
        else
            extendSceneforMultipleChoice();
    }

    private void extendSceneforTextQuestion() {
        //remove previously made changes to scene
        rootVbox.getChildren().remove(rootVbox.lookup("#textQuestion"));
        rootVbox.getChildren().remove(rootVbox.lookup("#mc"));

        //all changes are children of this Vbox
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(15);
        vBox.setId("textQuestion");

        HBox hbox = getHboxWithTextField("correct Answer", "crrAnswer");
        vBox.getChildren().addAll(hbox);

        Button btn = new Button("Add");
        btn.setOnAction((event) -> handleAddButton(event));
        vBox.getChildren().add(btn);

        rootVbox.getChildren().add(vBox);
    }

    private void extendSceneforMultipleChoice() {
        //remove previously made changes to scene
        rootVbox.getChildren().remove(rootVbox.lookup("#textQuestion"));
        rootVbox.getChildren().remove(rootVbox.lookup("#mc"));

        //all changes are children of this Vbox
        VBox mcBox = new VBox();
        mcBox.setAlignment(Pos.CENTER);
        mcBox.setSpacing(15);
        mcBox.setId("mc");

        HBox hbox = getHboxWithComboBox("How many answers?  ","amount", new String[]{"1", "2", "3"});
        ComboBox<String> cb = (ComboBox) hbox.lookup("#amount");
        cb.setOnAction((event) -> {
            int i = Integer.parseInt(cb.getValue());
            extendSceneforMultipleChoice2(mcBox, i);
        });

        mcBox.getChildren().add(hbox);
        rootVbox.getChildren().add(mcBox);
    }

    private void extendSceneforMultipleChoice2(VBox mcBox, int j) {
        //this function adds textfields for possible answers according to number af answers j.
        // also adds a comboBox for the correct answer

        //remove previously made changes to scene
        mcBox.getChildren().remove(mcBox.lookup("#answers"));

        VBox answerBox = new VBox();
        answerBox.setAlignment(Pos.CENTER);
        answerBox.setSpacing(15);
        answerBox.setId("answers");

        for (int i=0; i<j; i++) {
            HBox hBox = getHboxWithTextField("Answer " + i, "answer"+i);
            answerBox.getChildren().add(hBox);
        }

        // add correct combobox for correct answer
        String[] possCrrAsnwer = Arrays.copyOfRange(new String[] {"1", "2", "3"}, 0, j);
        HBox hBox = getHboxWithComboBox("correct Answer", "crrAnswer", possCrrAsnwer);
        answerBox.getChildren().add(hBox);

        // add "add" button
        Button btn = new Button("Add");
        btn.setOnAction((event) ->  handleAddButton(event));
        answerBox.getChildren().add(btn);

        mcBox.getChildren().add(answerBox);

    }

    public void handleAddButton(ActionEvent event) {
        Question q = new Question();
        q.setText(questionTxt.getText());
        q.setType(type);

        if (type.equals("txt")) {
            // get the answer textfield by id
            TextField t = (TextField) rootVbox.lookup("#crrAnswer");
            q.setCrrAnswer(t.getText());
        }
        else {
            // get answer comboBox by id
            ComboBox<String> cb = (ComboBox) rootVbox.lookup("#crrAnswer");
            q.setCrrAnswer(Integer.parseInt(cb.getValue()));

            // gather all possible answers
            ArrayList<String> possAnswers = new ArrayList<String>();
            int i = 0;
            TextField t = (TextField) rootVbox.lookup("#answer"+i);
            while (t != null) {
                possAnswers.add(t.getText());
                i++;
                t = (TextField) rootVbox.lookup("#answer"+i);
            }

            // add possible answers to newly created question
            q.setAnswers(possAnswers.toArray(new String[0]));
        }

        currentTopic.addQuestion(q);
        currentTopic.saveToFile();

        Main.changeScene("editor.fxml");

    }

    private HBox getHboxWithTextField(String label, String id) {
        // little method to create a styled HBox with label and textfield in it
        // adds id to textfield

        HBox hbox = new HBox();
        hbox.setAlignment(Pos.CENTER);
        hbox.setSpacing(30);
        Label l = new Label(label);

        TextField input = new TextField();
        input.setId(id);
        hbox.getChildren().addAll(l, input);

        return hbox;
    }

    private HBox getHboxWithComboBox(String label, String id, String[] items) {
        // little method to create a styled HBox with label and comboBox in it
        // adds id to comboBox

        HBox hbox = new HBox();
        hbox.setAlignment(Pos.CENTER);
        hbox.setSpacing(30);
        Label l = new Label(label);

        ComboBox<String> input = new ComboBox<String>();
        input.setItems(FXCollections.observableArrayList(items));
        input.setId(id);
        hbox.getChildren().addAll(l, input);

        return hbox;
    }


}
