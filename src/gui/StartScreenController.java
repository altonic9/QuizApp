package gui;

import javafx.fxml.FXML;


public class StartScreenController {


    public void startQuiz() {
        Main.changeScene("profileSelection.fxml");
    }

    public void openProfileManagment() {
        Main.changeScene("profileManagment.fxml");
    }

    public void openEditor() {
        Main.changeScene("editor.fxml");
    }

    public void exit() {
        System.exit(0);
    }

}
