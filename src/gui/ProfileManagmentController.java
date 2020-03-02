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
    private Label profilInfo;
    @FXML
    private Button addProfileBTN;
    @FXML
    private ListView  topicName;
    @FXML
    private Button deleteProfileBTN;
    @FXML
    private ListView  total;
    @FXML
    private ListView positives;

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
    void addProfile(ActionEvent event) {
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
    void loadProfiles(ActionEvent event) {
        int selectedItem = loadProfileLV.getSelectionModel().getSelectedIndex();
        if (selectedItem < 0) {
            GuiUtil.showAlert("Missing Information", "Please choose Profile");
            return;
        }
        profilInfo.setVisible(true);
        topicName.getItems().clear();
        total.getItems().clear();
        positives.getItems().clear();

        selectedProfile = profiles.get(selectedItem);

        HashMap<String, float[]> statistics = selectedProfile.getHistory();

        if (statistics.isEmpty()){
            topicName.getItems().add("No Statistic");
            total.getItems().add("No Statistic");
            positives.getItems().add("No Statistics");
        }else {
            for (String topic : statistics.keySet()) {
                float[] result = statistics.get(topic);
                topicName.getItems().add(topic);
                total.getItems().add(String.valueOf(result[0]));
                positives.getItems().add(String.valueOf(result[1]));
            }
        }
    }

    @FXML
    void close(ActionEvent event) { Main.changeScene("startScreen.fxml"); }

}
