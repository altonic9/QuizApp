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
    @FXML
    private Button editProfileBTN;
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
    @FXML
    private TextField profileNameTF;

    public void initialize() {
        loadProfiles();
        changeNameBTN.setDisable(true);
        addProfileWithNameBTN.setDisable(true);
        profileNameTF.setVisible(false);
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
        loadProfileLV.setVisible(false);
        profileNameTF.setVisible(true);
        addProfileBTN.setDisable(true);
        deleteProfileBTN.setDisable(true);
        changeNameBTN.setDisable(false);

        int selectedItem = loadProfileLV.getSelectionModel().getSelectedIndex();
        if (selectedItem < 0) {
            GuiUtil.showAlert("Missing Information", "Please choose Profile");
            addProfileBTN.setVisible(true);
            addProfileWithNameBTN.setVisible(true);
            deleteProfileBTN.setVisible(true);
            return;
        }
        selectedProfile = profiles.get(selectedItem);
        profileNameTF.setText("Change Name from: " + selectedProfile.getName());
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
        changeNameBTN.setDisable(false);
        enterPnL.setText("Enter your Profilename:");
        int selectedItem = loadProfileLV.getSelectionModel().getSelectedIndex();
        selectedProfile = profiles.get(selectedItem);

        while (Profile.exists(editName.getText())){
            GuiUtil.showAlert("Missing Information", "Name is not availible!");
            return;
        }
        if (event.getSource() == changeNameBTN && editName.getText().isEmpty()){
            GuiUtil.showAlert("Missing Information", "Please Enter a Name!");
            return;
        }
        Profile.findProfile(selectedProfile.getName()).changeName(editName.getText());

        editName.clear();

        loadProfiles();

        addProfileBTN.setDisable(false);
        deleteProfileBTN.setDisable(false);
        changeNameBTN.setDisable(true);
        loadProfileLV.setVisible(true);
        profileNameTF.setVisible(false);
    }

    @FXML
    void addProfile(ActionEvent event) {
        deleteProfileBTN.setDisable(true);
        editProfileBTN.setDisable(true);
        addProfileWithNameBTN.setDisable(false);
    }

    @FXML
    void addProfileWithName(ActionEvent event) {
        enterPnL.setText("Enter your Profilename:");

        while (Profile.exists(editName.getText())){
            GuiUtil.showAlert("Missing Information", "Name is not availible!");
            return;
        }

        if (event.getSource() == addProfileWithNameBTN && editName.getText().isEmpty()){
            GuiUtil.showAlert("Missing Information", "Please Enter a Name!");
            return;
        }
        Profile p = new Profile(editName.getText());
        p.create();

        editName.setText("");
        loadProfiles();

        deleteProfileBTN.setDisable(false);
        editProfileBTN.setDisable(false);
        addProfileWithNameBTN.setDisable(true);
        loadProfileLV.setVisible(true);
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
