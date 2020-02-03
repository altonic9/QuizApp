import java.io.File;
import java.util.ArrayList;

public class EditorMenu {


    public static void start() {

        Utility.printHeader("Editor");

        String[] nav = {"Create New Topic", "Edit Existing Topic", "Return to Main Menu"};
        int menuPoint = Utility.printNavigation("This is the Editor. Here you can..", nav);

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

        Utility.printHeader("Editor: Topic creation");

        System.out.println("Please insert a Question:");
        String text = Utility.getStringInput();

        int menuPoint = Utility.printNavigation("What Type of answer do you expect?", new String[] {"Multiple Choice", "Text"});
        String type = (menuPoint == 1) ? "MC" : "text";

        System.out.println("How many answers do you want to offer?");
        int  n = Utility.getIntInput(1, 4);

        String[] answers =  new String[n];
        for (int i=0; i<n; i++) {
            System.out.println("Please insert possible answer:");
            String a = Utility.getStringInput();
            answers[i] = a;
        }

        System.out.println("Whats the correct answer? \n\t (1, 2, .. for Multiple Choice, text otherwise)");
        int crrAnswer = Utility.getIntInput(1, n) - 1;

        Question q = new Question(text, type, answers, crrAnswer);
        return q;
    }

    public static void editTopic() {

        ArrayList<Topic> topics =  Topic.getAllTopics();

        System.out.println("\nChoose Topic by number: \n");
        int i = 1;
        for (Topic t : topics) {
            System.out.println("\t" + i + ". " + t.getName());
            i++;
        }
        System.out.println();

        int y = Utility.getIntInput(1, i);
        Topic chosenTopic = topics.get(y-1);

        System.out.println("\nYou chose: " + chosenTopic.getName());

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

        Question q = createQuestion();
        t.addQuestion(q);

        //save topic to file
        if (Utility.getConfirmation("Would you like to save changes?"))
            t.saveToFile();

        editTopic();
    }

    private static void deleteQuestion(Topic t) {

        // create a string array containing all question names
        ArrayList<Question> questions = t.getAllQuestions();
        ArrayList<String> questionNames = new ArrayList<String>();
        for (Question q : questions) { questionNames.add(q.getText()); }

        int y = Utility.printNavigation("Chose Question you want to delete:", questionNames.toArray(new String[0]));
        //arraylist is ordered Collection so this works:
        t.deleteQuestion(questions.get(y-1));

        //save topic to file
        if (Utility.getConfirmation("Would you like to save changes?"))
            t.saveToFile();

        //back to edit topic menu
        editTopic();
    }

    private static void deleteTopic(Topic t) {

        Topic.deleteTopic(t);
        start();
    }

}
