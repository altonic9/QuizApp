package gui;

import javafx.fxml.FXML;
import quiz.Topic;


public class StartScreenController {


    public void startQuiz() {
        if (Topic.getAllTopics().size() == 0) {
            GuiUtil.showAlert("Information", "No topics available.\nPlease go to the Editor and create a Topic first.");
            return;
        }
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
