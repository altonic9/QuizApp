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
    private Label profileInfoL;

    @FXML
    private Label enterPnL;

    @FXML
    private Button changeNameBTN;

    @FXML
    private TextField editName;

    @FXML
    private ListView loadProfileLV;

    @FXML
    private Label profilInfo;

    @FXML
    private Button addProfileWithNameBTN;

    public void initialize() {
        loadProfiles();
        editName.setVisible(false);
        changeNameBTN.setVisible(false);
        enterPnL.setVisible(false);
        addProfileWithNameBTN.setVisible(false);
        profileInfoL.setVisible(false);
        profilInfo.setVisible(false);

    }

    @FXML
    void deleteProfile(ActionEvent event) {
        int selectedItem = loadProfileLV.getSelectionModel().getSelectedIndex();

        Profile selectedProfile = profiles.get(selectedItem);
        selectedProfile.delete();

        loadProfiles();
    }

    @FXML
    void editProfile(ActionEvent event) {
        editName.setVisible(true);
        changeNameBTN.setVisible(true);
        enterPnL.setVisible(true);

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
    void changeName(ActionEvent event) {
        enterPnL.setText("Enter your Profilename:");
        int selectedItem = loadProfileLV.getSelectionModel().getSelectedIndex();
        selectedProfile = profiles.get(selectedItem);

        while (Profile.exists(editName.getText())){
            enterPnL.setText("Name is not availible!");
            return;
        }

        Profile.findProfile(selectedProfile.getName()).changeName(editName.getText());

        loadProfiles();

        editName.setVisible(false);
        changeNameBTN.setVisible(false);
        enterPnL.setVisible(false);
    }

    @FXML
    void addProfile(ActionEvent event) {
        editName.setVisible(true);
        enterPnL.setVisible(true);
        addProfileWithNameBTN.setVisible(true);
    }

    @FXML
    void addProfileWithName(ActionEvent event) {
        enterPnL.setText("Enter your Profilename:");

        while (Profile.exists(editName.getText())){
            enterPnL.setText("Name is not availible!");
            return;
        }

        Profile p = new Profile(editName.getText());
        p.create();

        editName.setText("");
        loadProfiles();

        editName.setVisible(false);
        enterPnL.setVisible(false);
        addProfileWithNameBTN.setVisible(false);
    }

    @FXML
    void loadProfiles(ActionEvent event) {
        profileInfoL.setVisible(true);
        profilInfo.setVisible(true);

        int selectedItem = loadProfileLV.getSelectionModel().getSelectedIndex();
        selectedProfile = profiles.get(selectedItem);

        HashMap<String, float[]> statistics = selectedProfile.getHistory();


        for (String topic : statistics.keySet()) {
            float[] result = statistics.get(topic);

            profileInfoL.setText(String.format(topic, result[0], result[1]));
        }

    }

}
