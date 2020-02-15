package gui;

import java.util.ArrayList;
import java.util.Optional;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import quiz.Question;
import quiz.Topic;



public class EditorController {

    @FXML
    private ListView topicsListView;
    @FXML
    private ListView questionsListView;

    private ArrayList<Topic> topics;
    private Topic selectedTopic; // the loaded topic


    public void initialize() {
        loadTopicsList();
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

    public void addTopicButton() {
        String name = showInputTextDialog("New Topic", "Enter topic's title");
        if (name != null) {
            Topic newTopic = new Topic(name);
            newTopic.saveToFile();
            loadTopicsList();
        }
    }

    public void deleteTopicButton() {
        // get index of selected object
        int selectedItem = topicsListView.getSelectionModel().getSelectedIndex();

        if (selectedItem == -1) {
            showAlert("Info", "No Topic selected");
            return;
        }

        Topic selectedTopic = topics.get(selectedItem);
        if (showConfirmationDialog("Confirmation", "Do you really want to delete \"" + selectedTopic.getName() + "\"-Topic?"))
            selectedTopic.delete();

        // update topics list
        loadTopicsList();

    }

    public void loadButton() {
        // empty list
        questionsListView.getItems().clear();

        // get index of selected item
        int selectedItem = topicsListView.getSelectionModel().getSelectedIndex();
        selectedTopic = topics.get(selectedItem);

        // popuate question ListView
        for (Question q : selectedTopic.getAllQuestions()) {
            questionsListView.getItems().add(q.getText());
        }
    }

    public void addQuestionButton() {

        if (selectedTopic == null) {
            showAlert("Info", "First, Select and Load Topic");
            return;
        }

        Helper.topicUUID = selectedTopic.getId();
        Main.changeScene("questionCreation.fxml");
    }

    public void deleteQuestionButton() {
        // get index of selected item, returns -1 if none is selected
        int selectedItem = questionsListView.getSelectionModel().getSelectedIndex();

        if (selectedItem == -1) {
            showAlert("Info", "No Question selected!");
            return;
        }

        Question q = selectedTopic.getAllQuestions().get(selectedItem);
        if (showConfirmationDialog("Confirmation", "Do you really want to delete \n \"" + q.getText() + "\"-Question?")) {
            selectedTopic.deleteQuestion(q);
            selectedTopic.saveToFile();
        }

        //update listview
        questionsListView.getItems().clear();
        for (Question a : selectedTopic.getAllQuestions()) {
            questionsListView.getItems().add(a.getText());
        }

    }

    public void closeButton() {
        Main.changeScene("startWindow.fxml");
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

    private void showAlert(String title, String text) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);

        // Header Text: null
        alert.setHeaderText(null);
        alert.setContentText(text);

        alert.showAndWait();
    }

    private Boolean showConfirmationDialog(String title, String text) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(text);

        Optional<ButtonType> result = alert.showAndWait();

        return result.get() == ButtonType.OK;
    }

}
