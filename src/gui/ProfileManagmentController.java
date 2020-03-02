package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import quiz.Profile;
import java.util.ArrayList;
import java.util.HashMap;

public class ProfileManagmentController {

    private ArrayList<Profile> profiles;
    private Profile selectedProfile;
    @FXML
    private ListView loadProfileLV;
    @FXML
    private Button addProfileBTN;
    @FXML
    private Button deleteProfileBTN;
    @FXML
    private TableView<HistoryEntry> historyTable;
    @FXML
    private TableColumn<HistoryEntry, String> topicCol;
    @FXML
    private TableColumn<HistoryEntry, String> playedCol;
    @FXML
    private TableColumn<HistoryEntry, String> posCol;


    public void initialize() {
        loadProfiles();
    }

    @FXML
    void deleteProfile(ActionEvent event) {
        int selectedItem = loadProfileLV.getSelectionModel().getSelectedIndex();
        if (selectedItem < 0) {
            GuiUtil.showAlert("Missing Information", "Please choose Profile");
            return;
        }

        Profile selectedProfile = profiles.get(selectedItem);
        selectedProfile.delete();

        loadProfiles();
    }

    @FXML
    void editProfile(ActionEvent event) {
        int selectedItem = loadProfileLV.getSelectionModel().getSelectedIndex();
        if (selectedItem < 0) {
            GuiUtil.showAlert("Missing Information", "Please choose Profile");
            addProfileBTN.setVisible(true);
            deleteProfileBTN.setVisible(true);
            return;
        }
        selectedProfile = profiles.get(selectedItem);
        String name;

        // get user input
        while ( true ){
            name = GuiUtil.showInputTextDialog("Edit Profile", "Please enter your Name:");
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

    @FXML
    void loadProfiles() {
        loadProfileLV.getItems().clear();

        profiles = Profile.getAllProfiles();

        for (Profile p : profiles) {
            loadProfileLV.getItems().add(p.getName());
        }
    }

    @FXML
    void addProfile() {
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

    @FXML
    void close() {
        Main.changeScene("startScreen.fxml");
    }


    // helper class in order to populate tableview
    public static class HistoryEntry {
        public String  topic;
        public float completed;
        public float correct;

        public HistoryEntry(String topic, float completed, float correct) {
            this.topic = topic;
            this.completed = completed;
            this.correct = correct;
        }

    }

}
