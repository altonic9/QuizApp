package gui;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import quiz.Profile;

import java.util.Optional;


public class ProfileSelectionController {

    @FXML
    private HBox menuHbox;
    private boolean profilesShown = false;

    public void onCreateUser() {
            String name;

            // get user input
            while ( true ){
                name = showInputTextDialog("New User", "Please enter your Name");
                if (name == null ) {  //user canceled
                    break;
                }
                else if (Profile.exists(name)) { // name alredy exists
                    showAlert("Error", "Name's already taken, please try again: ");
                }
                else { // all fine, create profile, start game
                    Profile p = new Profile(name);
                    p.create();
                    startGame(p);
                    break;
                }
            }
    }

    public void onLoadProfile() {

        if (profilesShown) {
            menuHbox.getChildren().remove(menuHbox.lookup("#profilesVbox"));
            profilesShown = false;
        }
        else {
            // create needed scene objects
            VBox vbox = new VBox();
            ListView<Profile> lv = new ListView<>();
            Button loadBtn = new Button();

            // prepare objects
            vbox.setId("profilesVbox");
            vbox.setAlignment(Pos.CENTER);
            vbox.setSpacing(10);

            lv.setId("profileLV");
            lv.getItems().addAll(Profile.getAllProfiles());
            lv.setPrefHeight(300);

            loadBtn.setText("load");
            loadBtn.setPrefWidth(60);
            loadBtn.setOnAction((event) -> { onLoadButton(); });

            // add objects to scene
            vbox.getChildren().addAll(lv, loadBtn);
            menuHbox.getChildren().add(vbox);

            profilesShown = true;
        }

    }

    private void onLoadButton() {
        // get selected profile and go to game scene
        ListView<Profile> lv = (ListView<Profile>) menuHbox.lookup("#profileLV");
        Profile p = lv.getSelectionModel().getSelectedItem();
        startGame(p);
    }

    private void startGame(Profile p) {

        Helper.gameProfile = p;

        Main.changeScene("topicSelection.fxml");
    }

    public void openProfileManagment() {
        Main.changeScene("profileManagment.fxml");
    }

    public void openEditor() {
        Main.changeScene("editor.fxml");
    }

    public void exit() {
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

}
