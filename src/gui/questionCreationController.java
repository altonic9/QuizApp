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

        //populate ComboBox, cant do it in fxml or scenebuilder
        typeComboBox.setItems(FXCollections.observableArrayList("Multiple Choice", "Text Answer"));
    }

    public void changetypeComboBox() {
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
        vBox.setId("textQuestion");
        vBox.setAlignment(Pos.CENTER);

        HBox hbox = new HBox();
        hbox.setAlignment(Pos.CENTER);
        Label l = new Label("Aswer");
        TextField t = new TextField();
        t.setId("crrAnswer");
        hbox.getChildren().addAll(l, t);
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
        mcBox.setId("mc");
        mcBox.setAlignment(Pos.CENTER);

        HBox hbox = new HBox();
        hbox.setAlignment(Pos.CENTER);

        Label l = new Label("How many answers?  ");
        ComboBox<String> cb = new ComboBox();
        cb.setItems(FXCollections.observableArrayList("1", "2", "3"));
        cb.setOnAction((event) -> {
            int i = Integer.parseInt(cb.getValue());
            extendSceneforMultipleChoice2(mcBox, i);
        });


        hbox.getChildren().addAll(l, cb);
        mcBox.getChildren().addAll(hbox);
        rootVbox.getChildren().add(mcBox);
    }

    private void extendSceneforMultipleChoice2(VBox mcBox, int j) {
        //this function adds textfields for possible answers according to number af answers j.
        // also adds a comboBox for the correct answer

        //remove previously made changes to scene
        mcBox.getChildren().remove(mcBox.lookup("#answers"));

        VBox answerBox = new VBox();
        answerBox.setId("answers");
        answerBox.setAlignment(Pos.CENTER);
        
        for (int i=0; i<j; i++) {
            HBox hBox = new HBox();
            hBox.setAlignment(Pos.CENTER);

            Label l = new Label("Answer " + i + " ");
            TextField t = new TextField();
            t.setId("answer"+i);
            hBox.getChildren().addAll(l, t);
            answerBox.getChildren().add(hBox);
        }

        // add correct answer combobox
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        Label l = new Label("correct Answer");

        ComboBox<String> cb = new ComboBox();
        cb.setId("crrAnswer");
        String[] possCrrAsnwer = Arrays.copyOfRange(new String[] {"1", "2", "3"}, 0, j);
        cb.setItems(FXCollections.observableArrayList(possCrrAsnwer));

        hBox.getChildren().addAll(l, cb);
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

        // How to pass topics name?
        // passing information between scenes?

    }
}
