package gui;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
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
                name = GuiUtility.showInputTextDialog("New Profile", "Please enter your Name:");
                if (name == null ) {  //user canceled
                    break;
                }
                else if (Profile.exists(name)) { // name alredy exists
                    GuiUtility.showAlert("Error", "Name's already taken, please try again: ");
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
            ComboBox<Profile> cb = new ComboBox<>();
            Button loadBtn = new Button();

            // prepare objects
            vbox.setId("profilesVbox");
            vbox.setAlignment(Pos.CENTER);
            vbox.setSpacing(10);

            cb.setId("profileCB");
            cb.getItems().addAll(Profile.getAllProfiles());
            cb.setPrefWidth(150);

            loadBtn.setText("load");
            loadBtn.setPrefWidth(60);
            loadBtn.setOnAction((event) -> { onLoadButton(); });

            // add objects to scene
            vbox.getChildren().addAll(cb, loadBtn);
            menuHbox.getChildren().add(vbox);

            profilesShown = true;
        }

    }

    private void onLoadButton() {
        // get selected profile and go to game scene
        ComboBox<Profile> cb = (ComboBox<Profile>) menuHbox.lookup("#profileCB");
        Profile p = cb.getSelectionModel().getSelectedItem();
        if (p == null) {
            GuiUtility.showAlert("Input Error", "Please select Profile");
            return;
        }
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

}
