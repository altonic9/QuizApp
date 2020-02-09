package gui;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.event.ActionEvent;

public class questionCreationController {

    @FXML
    private ComboBox<String> typeComboBox;
    @FXML
    private VBox rootVbox;

    //populate choiceBox, cant do it in fxml or scenebuilder
    public void initialize() {
        typeComboBox.setItems(FXCollections.observableArrayList("Multiple Choice", "Text Answer"));
    }

    public void changetypeComboBox() {
        String type = typeComboBox.getValue();
        if (type == "Text Answer")
            extendSceneforTextQuestion();
        else
            extendSceneforMultipleChoice();
    }

    private void extendSceneforTextQuestion() {
        //remove previously made changes to scene
        rootVbox.getChildren().remove(rootVbox.lookup("#textQuestion"));
        rootVbox.getChildren().remove(rootVbox.lookup("#mc"));

        //all changes are children of this Hbox
        HBox hbox = new HBox();
        hbox.setId("textQuestion");
        hbox.setAlignment(Pos.CENTER);

        Label l = new Label("Aswer");
        TextField t = new TextField();
        hbox.getChildren().addAll(l, t);
        rootVbox.getChildren().add(hbox);
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
            extendMcAnswers(mcBox, i);
        });


        hbox.getChildren().addAll(l, cb);
        mcBox.getChildren().addAll(hbox);
        rootVbox.getChildren().add(mcBox);
    }

    private void extendMcAnswers(VBox mcBox, int j) {
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
            hBox.getChildren().addAll(l, t);
            answerBox.getChildren().add(hBox);
        }

        mcBox.getChildren().add(answerBox);

    }


}
