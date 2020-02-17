package gui;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import quiz.Profile;
import quiz.Question;
import quiz.Topic;
import quiz.Utility;

import java.util.ArrayList;
import java.util.Optional;

public class ProfileManagmentController {

    private ArrayList<Profile> profiles;
    private Profile selectedProfile;

    @FXML
    private VBox rootVbox;

    @FXML
    private ListView loadProfileLV;

    @FXML
    private ListView loadProfileInfoLV;

    @FXML
    private Button loadBTN;

    @FXML
    private Button deleteProfileBTN;

    @FXML
    private Button editProfileBTN;

    public void initialize() {
        loadProfiles();
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


        int selectedItem = loadProfileLV.getSelectionModel().getSelectedIndex();
        selectedProfile = profiles.get(selectedItem);
        System.out.println("SSSS");

        Label label1 = new Label("Name:");
        TextField textField = new TextField ();
        HBox hb = new HBox();
        hb.getChildren().addAll(label1, textField);
        hb.setSpacing(30);

        //Profile.findProfile(selectedProfile.getName()).changeName(newName);
    }

    @FXML
    void loadProfiles() {

        loadProfileLV.getItems().clear();

        profiles = Profile.getAllProfiles();

        for (Profile p : profiles) {
            loadProfileLV.getItems().add(p.getName());
        }
    }


    private HBox getHboxWithTextField(String label, String id) {
        // little method to create a styled HBox with label and textfield in it
        // adds id to textfield

        HBox hbox = new HBox();
        hbox.setAlignment(Pos.CENTER);
        hbox.setSpacing(30);
        Label l = new Label(label);

        TextField input = new TextField();
        input.setId(id);
        hbox.getChildren().addAll(l, input);

        return hbox;
    }
}
