package gui;

import java.util.ArrayList;
import java.util.Optional;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import quiz.Question;
import quiz.Topic;


public class EditorController {

    @FXML
    private ListView<Topic> topicsListView;
    @FXML
    private ListView<Question> questionsListView;

    private ArrayList<Topic> topics;
    private Topic loadedTopic; // topic loaded by using the load button


    public void initialize() {
        loadTopicsList();

        // if you come form question creation, select last current topic in topicsListview and run loadButton()
        if (Helper.topicUUID != null) {
            topicsListView.getSelectionModel().select(Topic.getById(Helper.topicUUID));
            loadButton();

            // forget last topic you added a question to
            Helper.topicUUID = null;
        }
    }

    private void loadTopicsList() {
        // remove all items first
        topicsListView.getItems().clear();

        // get all topics and add to listView
        topics = Topic.getAllTopics();
        topicsListView.getItems().addAll(topics);
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
        Topic selectedTopic = topicsListView.getSelectionModel().getSelectedItem();

        if (selectedTopic == null) {
            showAlert("Info", "No Topic selected");
            return;
        }

        if (showConfirmationDialog("Confirmation", "Do you really want to delete \"" + selectedTopic.getName() + "\"-Topic?"))
            selectedTopic.delete();

        // update topics list
        loadTopicsList();

    }

    public void loadButton() {
        // empty list
        questionsListView.getItems().clear();

        // get selected item (loadedTopic global var)
        loadedTopic = topicsListView.getSelectionModel().getSelectedItem();

        // popuate question ListView
        questionsListView.getItems().addAll(loadedTopic.getAllQuestions());
    }

    public void addQuestionButton() {

        if (loadedTopic == null) {
            showAlert("Info", "First, Select and Load Topic");
            return;
        }

        Helper.topicUUID = loadedTopic.getId();
        Main.changeScene("questionCreation.fxml");
    }

    public void editQuestonButton() {
        // get selected item
        Question q = questionsListView.getSelectionModel().getSelectedItem();

        if (q == null) {
            showAlert("Info", "No Question selected!");
            return;
        }

        Helper.topicUUID = loadedTopic.getId();
        Helper.questionToEdit = q;
        Main.changeScene("questionCreation.fxml");
    }

    public void deleteQuestionButton() {
        // get index of selected item, returns -1 if none is selected
        Question q = questionsListView.getSelectionModel().getSelectedItem();

        if (q == null) {
            showAlert("Info", "No Question selected!");
            return;
        }

        if (showConfirmationDialog("Confirmation", "Do you really want to delete \n \"" + q.getText() + "\"-Question?")) {
            loadedTopic.deleteQuestion(q);
            loadedTopic.saveToFile();
        }

        //update listview
        questionsListView.getItems().clear();
        questionsListView.getItems().addAll(loadedTopic.getAllQuestions());

    }

    public void closeButton() {
        Main.changeScene("startScreen.fxml");
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
