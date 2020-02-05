import com.google.gson.Gson;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;

public class Topic {

    private String name;
    private ArrayList<Question> questions = new ArrayList<Question>();
    private String id;

    private static Gson gson = new Gson();


    public Topic(String name) {
        this.name = name;
        this.id = Utility.generateUUID();
    }

    public String getName() {
        return this.name;
    }

    public String getId() {
        return this.id;
    }

    public void addQuestion(Question q) {

        questions.add(q);
        System.out.println("\n\tQuestion added to " + this.name + "-Topic\n");
    }

    public void deleteQuestion(Question q) {

        questions.remove(q);
    }

    public ArrayList<Question> getAllQuestions() {

        return this.questions;
    }

    public static ArrayList<Topic> getAllTopics() {

        ArrayList<Topic> topics = new ArrayList<Topic>();

        //get all files in Resources-folder and convert to objects
        File res_folder = new File("resources/topics");
        for (File f : res_folder.listFiles()) {
            if ( f.getName().toLowerCase().endsWith((".top")) ) {
                Topic t = loadFromFile(f);
                topics.add(t);
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
            FileWriter myWriter = new FileWriter("resources/topics/" + this.name + ".top");
            myWriter.write(jsonString);
            myWriter.close();
            System.out.println("Successfully wrote to file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void delete() {

        File file = new File("resources/topics/" + name + ".top");
        file.delete();
    }

}
