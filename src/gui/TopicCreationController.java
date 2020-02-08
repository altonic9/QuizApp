package gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import quiz.Question;
import quiz.Topic;

import java.util.ArrayList;

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

    public void initialize() {
        loadTopicsList();
        initButtonsWithDialogInput();
    }

    public void loadTopicsList() {
        // get all topic names and add to listView
        topics = Topic.getAllTopics();
        topicsListView.getItems().clear();

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

        addQuestonButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String name = showInputTextDialog("New Question", "Enter question's text");
                addQuestionButton(name);
            }
        });
    }

    public void loadButton() {
        // get index of selected object
        int selectedItem = topicsListView.getSelectionModel().getSelectedIndex();
        Topic selectedTopic = topics.get(selectedItem);

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

    }

    public void addQuestionButton(String name) {

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

    }

    public static String showInputTextDialog(String title, String text) {

        TextInputDialog dialog = new TextInputDialog();

        dialog.setTitle(title);
        dialog.setHeaderText(text);
        dialog.setContentText("Name:");

        dialog.show();
        String result = dialog.getResult();

        return result;

    }



}
