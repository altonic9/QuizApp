import com.google.gson.Gson;

import java.util.ArrayList;

public class Editor {

    public static void start() {
        String caption = "\n\r\tThis is the Editor. Here you can" +
                "\n\r\t 1. Create New Topic" +
                "\n\r\t 2. Edit Existing Topic\n";

        System.out.println(caption);

        int menuPoint = Utility.getIntInput();

        switch (menuPoint) {
            case 1:
                createTopic();
                break;
            case 2:
                editTopic();
                break;
        }
    }

    private static void createTopic()
    {
        Topic topic = new Topic("Harry Potter");

        String[] a = new String[] {"Potter", "Harry", "Tom"};
        Question q = new Question("mc", "Wie heißt Harry Potter mit Vornamen?", a, 1);

        topic.addQuestion(q);

        Gson gson = new Gson();
        String j = gson.toJson(topic);
        System.out.println();
        System.out.println(j);
    }

    private static void editTopic()
    {
        System.out.println("\nChoose topic to edit:\n2");
        ArrayList<String> topics =  Topic.getAvailabeTopics();

        int i = 1;
        for (String t : topics) {
            System.out.println(i + ". " + t);
            i++;
        }


    }

}
