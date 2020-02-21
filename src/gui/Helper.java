package gui;

// This class is used to hand over information between scenes
// Bad practice, I know..

import quiz.Profile;
import quiz.Question;
import quiz.Topic;

public class Helper {

    // editor scene --> question creation scene
    public static String topicUUID;
    // for question edit
    public static Question questionToEdit;

    //gamestart: profile selection --> topic seletion --> game
    public static Profile gameProfile;
    public static Topic gameTopic;
    public static int questionAmount;
    public static Boolean randomized;

}
