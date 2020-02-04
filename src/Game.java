import jdk.jshell.execution.Util;

import java.lang.reflect.Array;
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
        // answer := user answer
        int answer = Utility.printNavigation(q.getText(), q.getAnswers());

        Utility.clearScreen();
        Utility.printHeader(currentTopic.getName());

        //show if correct
        if ( answer-1 == q.getCrrAnswer())
            System.out.println(w);
        else
            System.out.println(f);

        //wait some seconds
        try {
            TimeUnit.SECONDS.sleep(2);
        }
        catch(InterruptedException e) {

        }

    }


}
