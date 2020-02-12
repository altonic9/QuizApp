package gui;


import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import quiz.Question;
import quiz.Topic;

import java.util.ArrayList;
import java.util.Optional;


public class EditorController {

    @FXML
    private ListView topicsListView;
    @FXML
    private ListView questionsListView;
    @FXML
    private Button addTopicButton;

    private ArrayList<Topic> topics;
    private Topic selectedTopic; // the loaded topic


    public void initialize() {
        loadTopicsList();
        initButtonsWithDialogInput();
    }

    private void loadTopicsList() {
        // remove all items first
        topicsListView.getItems().clear();

        // get all topic names and add to listView
        topics = Topic.getAllTopics();

        for (Topic t : topics) {
            topicsListView.getItems().add(t.getName());
        }
    }

    private void initButtonsWithDialogInput() {
        // set action for buttons which trigger textinput dialog
        // cant do it in fxml file, because textinputdialog is opened twice then
        addTopicButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String name = showInputTextDialog("New Topic", "Enter topic's title");
                if (name != null)
                    addTopicButton(name);
            }
        });
    }

    private static String showInputTextDialog(String title, String text) {

        TextInputDialog dialog = new TextInputDialog();

        dialog.setTitle(title);
        dialog.setHeaderText(text);
        dialog.setContentText("Name:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent())
            return result.get();
        else // cancel button
            return null;
    }

    public void loadButton() {
        // empty list
        questionsListView.getItems().clear();

        // get index of selected item
        int selectedItem = topicsListView.getSelectionModel().getSelectedIndex();
        selectedTopic = topics.get(selectedItem);

        for (Question q : selectedTopic.getAllQuestions()) {
            questionsListView.getItems().add(q.getText());
        }
    }

    // public void saveButton() {
    /* not needed anymore
        for (Topic t : topics) {
            t.saveToFile();
        }
    }*/

    public void closeButton() {
        Main.changeScene("startWindow.fxml");
    }

    public void addTopicButton(String name) {
        Topic newTopic = new Topic(name);
        newTopic.saveToFile();
        loadTopicsList();
    }

    public void addQuestionButton() {

        if (selectedTopic == null) {
            showAlert("Info", "First, Select and Load Topic");
            return;
        }

        Helper.topicUUID = selectedTopic.getId();
        Main.changeScene("questionCreation.fxml");
    }

    public void deleteTopicButton() {
        // get index of selected object
        int selectedItem = topicsListView.getSelectionModel().getSelectedIndex();

        if (selectedItem == -1) {
            showAlert("Info", "No Topic selected");
            return;
        }

        Topic selectedTopic = topics.get(selectedItem);
        selectedTopic.delete();

        // update topics list
        loadTopicsList();

    }

    public void deleteQuestionButton() {
        // get index of selected item, returns -1 if none is selected
        int selectedItem = questionsListView.getSelectionModel().getSelectedIndex();

        if (selectedItem == -1) {
            showAlert("Info", "No Question selected!");
            return;
        }

        Question q = selectedTopic.getAllQuestions().get(selectedItem);
        selectedTopic.deleteQuestion(q);
        selectedTopic.saveToFile();

        //update listview
        questionsListView.getItems().clear();
        for (Question a : selectedTopic.getAllQuestions()) {
            questionsListView.getItems().add(a.getText());
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
