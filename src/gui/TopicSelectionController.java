package gui;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import quiz.Profile;
import quiz.Question;
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

    private Profile chosenProfile;
    private Topic chosenTopic;

    public void initialize() {
        chosenProfile = Helper.gameProfile;
        Helper.gameProfile = null;

        loadTopicsList();
    }

    private void loadTopicsList() {
        // remove all items first
        topicCB.getItems().clear();

        // get all topics and add to listView
        topicCB.getItems().addAll(Topic.getAllTopics());
    }

    public void onAllTopicChkBox() {

        if (allTopicChkBox.isSelected()) {
            //disable topic selection box
            topicCB.setDisable(true);

            // get amount of questions and set spinner
            int maxQuestions = 0;
            for (Topic t : Topic.getAllTopics()) {
                maxQuestions += t.getAllQuestions().size();
            }

            SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, maxQuestions, maxQuestions);
            questionSpinner.setValueFactory(valueFactory);
        }
        else {
            //re-enable topic selection box
            topicCB.setDisable(false);
            //reset spinner value
            SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 1, 1);
            questionSpinner.setValueFactory(valueFactory);
        }

    }

    public void onTopicCB() {
        // get selected topic and set spinner range

        chosenTopic = topicCB.getValue();
        int maxQuestions = chosenTopic.getAllQuestions().size();

        // set spinner amount
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, maxQuestions, maxQuestions);
        questionSpinner.setValueFactory(valueFactory);
    }

    public void start() {
        // gather all game preferences and switch scene

        Helper.gameTopic = chosenTopic;
        Helper.gameProfile = chosenProfile;
        Helper.questionAmount = questionSpinner.getValue();
        Helper.randomized = randomChkBox.isSelected();
        Helper.playAllTopics = allTopicChkBox.isSelected();

        Main.changeScene("gameScreen.fxml");

    }

    public void cancel() {
        Main.changeScene("startScreen.fxml");
    }

}
