import java.util.ArrayList;

public class EditorMenu {


    public static void start() {
        String caption = "\n\r\tThis is the Editor. Here you can\n\r" +
                "\n\r\t 1. Create a New Topic" +
                "\n\r\t 2. Edit Existing Topic" +
                "\n\r\t 3. Return to Main Menu\n";

        System.out.println(caption);

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
        String s2 = "Would you like to add a Question? \n\r\t 1. Yes \t 2. No\n\r";
        String s3 = "Would you like to add another Question? \n\r\t 1. Yes \t 2. No\n\r";
        String s4 = "Would you like to save the created topic? \n\r\t 1. Yes \t 2. No\n\r";

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
                "\n\r\t 1. Multiple Choice" +
                "\n\r\t 2. Text\n";
        String s4 = "How many answers do you want to offer?: ";
        String s5 = "Whats the correct answer?" +
                "\n\r\t (1,2,.. for Multiple Choice, text otherwise)";
        String s6 = "Please insert possible answer: ";

        System.out.println(s2);
        String text = Utility.getStringInput();

        System.out.println(s3);
        String type = Utility.getStringInput();

        System.out.println(s4);
        int  n = Utility.getIntInput(1, 4);

        String[] answers =  new String[4];
        for (int i=0; i<n; i++) {
            System.out.println(s4);
            String a = Utility.getStringInputMinMax(1, 30);
            answers[i] = a;
        }

        System.out.println(s6);
        int crrAnswer = Utility.getIntInput(1, n);

        Question q = new Question(text, type, answers, crrAnswer);
        return q;
    }

    public static void editTopic() {
        System.out.println("\nChoose topic to edit:\n");
        ArrayList<String> topics =  Topic.getAvailabeTopics();

        int i = 1;
        for (String t : topics) {
            System.out.println(i + ". " + t);
            i++;
        }
    }

}
