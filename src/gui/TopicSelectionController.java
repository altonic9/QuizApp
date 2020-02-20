package gui;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import quiz.Profile;
import quiz.Topic;

public class TopicSelectionController {

    @FXML
    private CheckBox allTopicChkBox;
    @FXML
    private ComboBox<Topic> topicCB;
    @FXML
    private Spinner<Integer> questionSpinner;
    @FXML
    private CheckBox randomChkBox;

    Profile gameProfile = Helper.gameProfile;
    Topic chosenTopic;

    public void initialize() {
        loadTopicsList();
    }

    private void loadTopicsList() {
        // remove all items first
        topicCB.getItems().clear();

        // get all topics and add to listView
        topicCB.getItems().addAll(Topic.getAllTopics());
    }

    public void onAllTopicChkBox() {
        topicCB.setDisable(allTopicChkBox.isSelected());
    }

    public void onTopicCB() {
        chosenTopic = topicCB.getValue();
        int maxQuestions = chosenTopic.getAllQuestions().size();

        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, maxQuestions, maxQuestions);
        questionSpinner.setValueFactory(valueFactory);
    }

    public void start() {
        int amount = questionSpinner.getValue();
    }

    public void cancel() {
        Main.changeScene("startScreen.fxml");
    }

}
