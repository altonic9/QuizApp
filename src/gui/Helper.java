package gui;

// This class is used to hand over information between scenes
// Bad practice, I know..

import quiz.Profile;
import quiz.Question;

public class Helper {

    // editor scene --> question creation scene
    public static String topicUUID;
    // for question edit
    public static Question questionToEdit;

    //gamestart: profile selection --> topic seletion
    public static Profile gameProfile;

}
