package gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import quiz.Question;
import quiz.Topic;

import java.util.ArrayList;
import java.util.Optional;

public class TopicCreationController {

    @FXML
    private ListView topicsListView;
    @FXML
    private ListView questionsListView;
    @FXML
    private Button addTopicButton;
    @FXML
    private Button addQuestonButton;

    private ArrayList<Topic> topics;
    private Topic selectedTopic;


    public void initialize() {
        loadTopicsList();
        initButtonsWithDialogInput();
    }

    public void loadTopicsList() {
        topicsListView.getItems().clear();

        // get all topic names and add to listView
        topics = Topic.getAllTopics();

        for (Topic t : topics) {
            topicsListView.getItems().add(t.getName());
        }
    }

    public void initButtonsWithDialogInput() {
        // set action for buttons with textinput dialog
        // cant do it in fxml file, because textinputdialog is opened twice then
        addTopicButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String name = showInputTextDialog("New Topic", "Enter topic's title");
                addTopicButton(name);
            }
        });

//        addQuestonButton.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                String name = showInputTextDialog("New Question", "Enter question's text");
//                addQuestionButton(name);
//            }
//        });
    }

    public void loadButton() {
        // get index of selected item
        int selectedItem = topicsListView.getSelectionModel().getSelectedIndex();
        selectedTopic = topics.get(selectedItem);

        for (Question q : selectedTopic.getAllQuestions()) {
            questionsListView.getItems().add(q.getText());
        }
    }

    public void saveButton() {
        for (Topic t : topics) {
            t.saveToFile();
        }
    }

    public void closeButton() {
        Main.changeScene("startWindow.fxml");
    }

    public void addTopicButton(String name) {
        Topic newTopic = new Topic(name);
        newTopic.saveToFile();
        loadTopicsList();
    }

    public void addQuestionButton() {
        createQuestionScene();
    }

    public void deleteTopicButton() {
        // get index of selected object
        int selectedItem = topicsListView.getSelectionModel().getSelectedIndex();
        Topic selectedTopic = topics.get(selectedItem);
        selectedTopic.delete();

        // update topics list
        loadTopicsList();

    }

    public void deleteQuestionButton() {
        // get index of selected item
        int selectedItem = questionsListView.getSelectionModel().getSelectedIndex();

        Question q = selectedTopic.getAllQuestions().get(selectedItem);
        selectedTopic.deleteQuestion(q);
        selectedTopic.saveToFile();

        //update listview
        questionsListView.getItems().clear();
        for (Question a : selectedTopic.getAllQuestions()) {
            questionsListView.getItems().add(a.getText());
        }

    }

    private static String showInputTextDialog(String title, String text) {

        TextInputDialog dialog = new TextInputDialog();

        dialog.setTitle(title);
        dialog.setHeaderText(text);
        dialog.setContentText("Name:");

        Optional<String> result = dialog.showAndWait();

        return result.get();

    }

    private static void createQuestionScene() {
        VBox vbox = new VBox();

        // question text
        HBox hbox1 = new HBox();
        Label questionTxt = new Label("Question");
        TextField textField = new TextField("Enter question");
        textField.setMinWidth(120);
        hbox1.getChildren().addAll(questionTxt, textField);
        vbox.getChildren().add(hbox1);

        // question type
        HBox hbox2 = new HBox();
        Label questionType = new Label("Choose Question Type");
        ObservableList<String> types= FXCollections.observableArrayList("Multiple Choice", "Text Answer");
        ChoiceBox<String> choiceBox = new ChoiceBox<String>(types);
        hbox2.getChildren().addAll(questionType, choiceBox);
        vbox.getChildren().add(hbox2);


        Main.changeScene(new Scene(vbox, 800, 600));
    }
}
