import java.io.File;
import java.util.ArrayList;

public class EditorMenu {


    public static void start() {
        String caption = "\n\t==========================" +
                "\n\t||\t     Editor         ||\n" +
                "\t==========================";

        String nav = "\n\t 1. Create a New Topic" +
                "\n\t 2. Edit Existing Topic" +
                "\n\t 3. Return to Main Menu\n";

        System.out.println(caption);
        System.out.println(nav);

        int menuPoint = Utility.getIntInput(1, 3);

        switch (menuPoint) {
            case 1:
                createTopic();
                break;
            case 2:
                editTopic();
                break;
            case 3:
                MainMenu.start();
                break;
        }
    }

    public static void createTopic() {
        String s1 = "Please insert Topic's name: ";
        String s2 = "Would you like to add a Question? \n\t 1. Yes \t 2. No\n";
        String s3 = "Would you like to add another Question? \n\t 1. Yes \t 2. No\n";
        String s4 = "Would you like to save the created topic? \n\t 1. Yes \t 2. No\n";

        System.out.println(s1);
        String name = Utility.getStringInput();

        Topic t = new Topic(name);

        System.out.println(s2);
        int y = Utility.getIntInput(1, 2);

        //create Questions
        while(y == 1) {
            Question q = createQuestion();
            t.addQuestion(q);
            System.out.println(s3);
            y = Utility.getIntInput(1, 2);
        }

        //save topic to file
        System.out.println(s4);
        y = Utility.getIntInput(1, 2);
        if (y==1)
            t.saveToFile();

        start();

    }

    public static Question createQuestion() {
        String s2 = "Please insert a Question: ";
        String s3 = "What Type of answer do you expect?: " +
                "\n\t 1. Multiple Choice" +
                "\n\t 2. Text\n";
        String s4 = "How many answers do you want to offer?: ";
        String s5 = "Whats the correct answer?" +
                "\n\t (1,2,.. for Multiple Choice, text otherwise)";
        String s6 = "Please insert possible answer: ";

        System.out.println(s2);
        String text = Utility.getStringInput();

        System.out.println(s3);
        String type = Utility.getStringInput();

        System.out.println(s4);
        int  n = Utility.getIntInput(1, 4);

        String[] answers =  new String[4];
        for (int i=0; i<n; i++) {
            System.out.println(s6);
            String a = Utility.getStringInput();
            answers[i] = a;
        }

        System.out.println(s5);
        int crrAnswer = Utility.getIntInput(1, n) - 1;

        Question q = new Question(text, type, answers, crrAnswer);
        return q;
    }

    public static void editTopic() {
        ArrayList<String> topicNames =  Topic.getAllTopicNames();

        System.out.println("\nChoose Topic by number: \n");
        int i = 1;
        for (String t : topicNames) {
            System.out.println("\t" + i + ". " + t);
            i++;
        }
        System.out.println();

        int y = Utility.getIntInput(1, i);
        String chosenTopic = topicNames.get(y-1);

        System.out.println("\nYou chose: " + chosenTopic);
        String nav = "\n\t What would you like to do?\n" +
                "\n\t 1. Add Question" +
                "\n\t 2. Delete Question" +
                "\n\t 3. Delete Topic\n" +
                "\n\t 4. Save Changes";
        System.out.println(nav);

        int input = Utility.getIntInput(1, 3);
        switch (input) {
            case 1:
                addQuestionToTopic(chosenTopic);
                break;
            case 2:
                deleteQuestion(chosenTopic);
                break;
            case 3:
                deleteTopic(chosenTopic);
                break;
            case 4:
                //
                break;

        }

    }

    private static void addQuestionToTopic(String topicName) {
        Question q = createQuestion();

        File file = new File("Resources/" + topicName + ".top");
        Topic t = Topic.loadFromFile(file);
        t.addQuestion(q);

        //save topic to file
        System.out.println("Would you like to save changes? \n\t 1. Yes \t 2. No\n");
        int y = Utility.getIntInput(1, 2);
        if (y==1)
            t.saveToFile();

        editTopic();
    }

    private static void deleteQuestion(String topicName) {
        File file = new File("Resources/" + topicName + ".top");
        Topic t = Topic.loadFromFile(file);
        t.deleteQuestion(text);
    }

    private static void deleteTopic(String topicName) {

        Topic.deleteTopic(topicName);
        start();
    }

}
