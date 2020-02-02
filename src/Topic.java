import com.google.gson.Gson;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;

public class Topic {

    private String name;
    private ArrayList<Question> questions = new ArrayList<Question>();

    private static Gson gson = new Gson();


    public Topic(String name) { this.name = name; }

    public void addQuestion(Question q) {

        questions.add(q);
        System.out.println("\n\tQuestion added to " + this.name + "-Topic\n");
    }

    public void deleteQuestion(String questionText) {

        questions.removeIf(q -> q.getText() == questionText);
        System.out.println("\n\tQuestion removed!\n");
    }

    public void getAllQuestionTexts(String questionText) {

        questions.removeIf(q -> q.getText() == questionText);
        System.out.println("\n\tQuestion removed!\n");
    }

    public static ArrayList<String> getAllTopicNames() {

        ArrayList<String> topics = new ArrayList<String>();

        //get all files in Resources-folder and convert to objects
        File res_folder = new File("Resources");
        for (File f : res_folder.listFiles()) {
            if ( f.getName().toLowerCase().endsWith((".top")) ) {
                Topic t = loadFromFile(f);
                topics.add(t.name);
            }
        }

        return topics;
    }

    public static Topic loadFromFile(File f) {

        // read Topic object from json file
        try {
            String jsonString = Files.readString(f.toPath());
            Topic t = gson.fromJson(jsonString, Topic.class);
            return t;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void saveToFile() {
        //create JSON-String of a Topic Instance

        String jsonString = gson.toJson(this);

        //write jsonString to File
        // filename = topic's name.top
        try {
            FileWriter myWriter = new FileWriter("Resources/" + this.name + ".top");
            myWriter.write(jsonString);
            myWriter.close();
            System.out.println("Successfully wrote to file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static void deleteTopic(String name) {
        File file = new File("Resources/" + name + ".top");

        System.out.println("\n\tDo you really want to delete " + name + "-Topic? \n" +
                "\n\tThis can't be undone! \n\t 1. Yes \t 2. No\n");

        int input = Utility.getIntInput(1, 2);
        if (input==1) {
            file.delete();
            System.out.println("\n\t" + name + "-Topic deleted!\n");
        }
    }
}
