package gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import quiz.Profile;

import java.util.ArrayList;
import java.util.HashMap;

public class ProfileManagmentController {

    @FXML
    private ListView<Profile> loadProfileLV;
    @FXML
    private TableView<HistoryEntry> historyTable;
    @FXML
    private TableColumn<HistoryEntry, String> topicCol;
    @FXML
    private TableColumn<HistoryEntry, String> playedCol;
    @FXML
    private TableColumn<HistoryEntry, String> posCol;

    private ArrayList<Profile> profiles;


    public void initialize() {
        // populate profile listview
        loadProfiles();

        // set up table
        topicCol.setCellValueFactory(new PropertyValueFactory<>("topic"));
        playedCol.setCellValueFactory(new PropertyValueFactory<>("completed"));
        posCol.setCellValueFactory(new PropertyValueFactory<>("correct"));
    }

    public void deleteProfile() {
        int selectedItem = loadProfileLV.getSelectionModel().getSelectedIndex();
        if (selectedItem < 0) {
            GuiUtil.showAlert("Missing Information", "Please choose Profile");
            return;
        }

        Profile selectedProfile = profiles.get(selectedItem);
        selectedProfile.delete();

        loadProfiles();
    }

    public void editProfile() {

        Profile selectedProfile = loadProfileLV.getSelectionModel().getSelectedItem();
        if (selectedProfile == null) {
            GuiUtil.showAlert("Info", "No Profile selected");
            return;
        }

        String name;
        // get user input
        while ( true ){
            name = GuiUtil.showInputTextDialog("Change Name", "Please enter new Name:");
            if (name == null ) {  //user canceled
                break;
            }
            else if (Profile.exists(name)) { // name alredy exists
                GuiUtil.showAlert("Error", "Name's already taken, please try again: ");
            }
            else { // all fine, create profile, start game
                Profile.findProfile(selectedProfile.getName()).changeName(name);
                loadProfiles();
                break;
            }
        }
    }

    public void loadProfiles() {
        loadProfileLV.getItems().clear();

        profiles = Profile.getAllProfiles();

        loadProfileLV.getItems().addAll(profiles);
    }

    public void addProfile() {
        String name;

        // get user input
        while ( true ){
            name = GuiUtil.showInputTextDialog("New Profile", "Please enter your Name:");
            if (name == null ) {  //user canceled
                break;
            }
            else if (Profile.exists(name)) { // name alredy exists
                GuiUtil.showAlert("Error", "Name's already taken, please try again: ");
            }
            else { // all fine, create profile, start game
                Profile p = new Profile(name);
                p.create();
                loadProfiles();
                break;
            }
        }
    }

    public void onLoadBtn() {
        ObservableList<HistoryEntry> historyList = FXCollections.observableArrayList();
        Profile selectedProfile = loadProfileLV.getSelectionModel().getSelectedItem();

        if (selectedProfile == null) {
            GuiUtil.showAlert("Missing Information", "Please choose Profile");
            return;
        }

        var history = selectedProfile.getHistory();

        for (String topicName : history.keySet()) {
            historyList.add( new HistoryEntry(topicName, round(history.get(topicName)[0]), round(history.get(topicName)[1])) );
        }

        historyTable.setItems(historyList);

    }

    public void close() {
        Main.changeScene("startScreen.fxml");
    }

    private float round(float value) {
        return ((int) ((value + 0.05f) * 10)) / 10f;
    }


    // helper class in order to populate tableview (tableview needs objects)
    public static class HistoryEntry {
        private String topic;
        private float completed;
        private float correct;

        public HistoryEntry(String topic, float completed, float correct) {
            this.topic = topic;
            this.completed = completed;
            this.correct = correct;
        }

        // getter used by tableview, dont let the IDE fool you ;)
        public String getTopic() {
            return this.topic;
        }
        public String getCompleted() {
            return String.valueOf(this.completed);
        }
        public String getCorrect() {
            return String.valueOf(this.correct);
        }

    }

}
