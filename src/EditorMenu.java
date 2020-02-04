import java.io.File;
import java.util.ArrayList;

public class EditorMenu {


    public static void start() {
        Utility.clearScreen();
        Utility.printHeader("Editor");

        String[] nav = {"Create New Topic", "Edit Existing Topic", "Edit existing User", "Delete User", "Return to Main Menu"};
        int menuPoint = Utility.printNavigation("This is the Editor. Here you can..", nav);

        switch (menuPoint) {
            case 1:
                createTopic();
                break;
            case 2:
                editTopic();
                break;
            case 3:
                editUserName();
                break;
            case 4:
                deleteUser();
                break;
            case 5:
                MainMenu.start();
                break;
        }
    }

    public static void createTopic() {
        Utility.clearScreen();
        System.out.println("Please insert Topic's name: ");
        String name = Utility.getStringInput();

        Topic t = new Topic(name);

        //create Questions
        boolean createQ = Utility.getConfirmation("Would you like to add a Question?");
        while(createQ) {
            Question q = createQuestion();
            t.addQuestion(q);
            createQ = Utility.getConfirmation("Would you like to add another Question?");
        }

        //save topic to file
        if (Utility.getConfirmation("Would you like to save created topic?"))
            t.saveToFile();

        start();
    }

    public static Question createQuestion() {
        Utility.clearScreen();
        Utility.printHeader("Editor: Topic creation");

        System.out.println("Please insert a Question:");
        String text = Utility.getStringInput();

        int menuPoint = Utility.printNavigation("What Type of answer do you expect?", new String[] {"Multiple Choice", "Text"});
        String type = (menuPoint == 1) ? "mc" : "txt";

        if ( type.equals("mc")) {
            System.out.println("How many answers do you want to offer?");
            int  n = Utility.getIntInput(1, 4);

            String[] answers =  new String[n];
            for (int i=0; i<n; i++) {
                System.out.println("Please insert possible answer:");
                String a = Utility.getStringInput();
                answers[i] = a;
            }

            // get correct answer
            int crrAnswer = Utility.printNavigation("Whats the correct answer?", answers);

            Question q = new Question(text, type, answers, crrAnswer);
            return q;
        }
        else {
            System.out.println("\nPlease type in correct aswer now:");
            String answer = Utility.getStringInput();

            Question q = new Question(text, type, answer);
            return q;
        }
    }

    public static void editTopic() {
        Utility.clearScreen();

        // create a string array containing all topic names
        ArrayList<Topic> topics =  Topic.getAllTopics();
        ArrayList<String> topicNames = new ArrayList<String>();
        for (Topic t : topics) { topicNames.add(t.getName()); }

        int y = Utility.printNavigation("Choose Topic you want to edit:", topicNames.toArray(new String[0]));
        Topic chosenTopic = topics.get(y-1);

        Utility.clearScreen();
        System.out.println("\nYou chose: " + chosenTopic.getName() + "\n");

        String[] nav = {"Add Question", "Delete Question", "Delete Topic"};
        int input = Utility.printNavigation("What would you like to do?", nav);

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
        }

    }

    private static void addQuestionToTopic(Topic t) {
        Utility.clearScreen();

        Question q = createQuestion();
        t.addQuestion(q);

        //save topic to file
        if (Utility.getConfirmation("Would you like to save changes?"))
            t.saveToFile();

        editTopic();
    }

    private static void deleteQuestion(Topic t) {
        Utility.clearScreen();

        // create a string array containing all question names
        ArrayList<Question> questions = t.getAllQuestions();
        ArrayList<String> questionNames = new ArrayList<String>();
        for (Question q : questions) { questionNames.add(q.getText()); }

        int y = Utility.printNavigation("Chose Question you want to delete:", questionNames.toArray(new String[0]));
        //arraylist is ordered Collection so this works:
        t.deleteQuestion(questions.get(y-1));

        Utility.clearScreen();

        System.out.println("\n\tQuestion removed!\n");
        //save topic to file
        if (Utility.getConfirmation("Would you like to save changes?"))
            t.saveToFile();

        //back to edit topic menu
        editTopic();
    }

    private static void deleteTopic(Topic t) {
        Utility.clearScreen();

        String name = t.getName();
        if (Utility.getConfirmation("Do you really want to delete \"" + name + "\"-Topic? This can't be undone!")) {
            t.delete();
            System.out.println("\n\t\"" + name + "\"-Topic deleted!\n");
        }
        start();
    }

    public static void editUserName(){
        String s1 = "Whats your old Username?";
        String s2 = "Whats your new Username?";
        String s3 = "Name is not available, please insert existing Username.";
        String s4 = "Name is not available, please insert new Username.";

        System.out.println(s1);
        String nameOld = Utility.getStringInput();

        while (User.checkUserName(nameOld) == true){
            System.out.println(s3);
            nameOld = Utility.getStringInput();
        }

        System.out.println(s2);
        String nameNew = Utility.getStringInput();

        while (User.checkUserName(nameNew) == false){
            System.out.println(s4);
            nameNew = Utility.getStringInput();
        }

        User.editUserName(nameOld, nameNew);
    }

    public static void deleteUser(){
        String s1 = "Whats you Username?";
        System.out.println(s1);
        String name = Utility.getStringInput();
        User.deleteUser(User.getUserObjectWithName(name));

    }
}
