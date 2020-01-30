import com.google.gson.Gson;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Scanner;

public class Topic {

    private String name;
    private ArrayList<Question> questions = new ArrayList<Question>();

    private static Gson gson = new Gson();


    public Topic(String name) { this.name = name; }

    public void addQuestion(Question q) {

        questions.add(q);
    }

    public static ArrayList<String> getAvailabeTopics() {

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

    private static Topic loadFromFile(File f) {

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

    public void saveToFile()
    {
        //create JSON-String of a topic Instance

        String jsonString = gson.toJson(this);

        //write jsonString to File
        // filename = topic's name
        try {
            FileWriter myWriter = new FileWriter("Resources/" + this.name + ".top");
            myWriter.write(jsonString);
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
