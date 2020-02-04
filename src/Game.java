import jdk.jshell.execution.Util;

import java.lang.reflect.Array;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Game {

    private static Topic currentTopic;
    private static String w = "\n\n\n\t\t\t   KORREKT \n\n\n\n";
    private static String f = "\n\n\n\t\t\t   FALSCH  \n\n\n\n";

    public static void start() {
        Utility.clearScreen();
        Utility.printHeader("Game");

        // create a string array containing all topic names
        ArrayList<Topic> topics =  Topic.getAllTopics();
        ArrayList<String> topicNames = new ArrayList<String>();
        for (Topic t : topics) { topicNames.add(t.getName()); }

        int menuPoint = Utility.printNavigation("Chose your topic...", topicNames.toArray(new String[0]));
        currentTopic = topics.get(menuPoint-1);

        play();

    }

    public static void play() {

        ArrayList<Question> questions = currentTopic.getAllQuestions();
        int max = questions.size();

        int i = 1;
        for (Question q : questions) {
            Utility.clearScreen();
            Utility.printHeader(currentTopic.getName());
            System.out.println("\tFrage (" + i + "/" + max + ")\n");
            ask(q);
            i++;
        }
    }

    public static void ask(Question q) {
        Utility.clearScreen();
        Utility.printHeader(currentTopic.getName());

        // answer := user answer
        Boolean crr;
        if (q.getType().equals("mc")) {
            //multiple choice

            // prints questions and possible answers & gets user input
            int answer = Utility.printNavigation(q.getText(), q.getAnswers());
            crr = q.isCrrAnswer(answer -1);
        }
        else {
            // text question
            System.out.println("\t" + q.getText());
            System.out.println("\nPlease type in your aswer now:");
            String answer = Utility.getStringInput();
            crr = q.isCrrAnswer(answer);
        }

        //show result
        if ( crr )
            System.out.println(w);
        else
            System.out.println(f);

        //wait some seconds
        try { TimeUnit.SECONDS.sleep(2); } catch(InterruptedException e) {}
    }


}
