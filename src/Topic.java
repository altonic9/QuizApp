import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;

public class Topic {

    public String name;
    public ArrayList<Question> questions = new ArrayList<Question>();

    public Topic(String name) {
        this.name = name;
    }

    public void addQuestion(Question q) {
        questions.add(q);
    }

    public static ArrayList<String> getAvailabeTopics() {
        ArrayList<String> topics = readTopicsFromFiles();
        return topics;
    }

    private static ArrayList<String> readTopicsFromFiles() {
        // read first line in every txt-file in resource folder
        ArrayList<String> topics = new ArrayList<String>();

        //get all files in Resources
        File res_folder = new File("Resources");
        for (File f : res_folder.listFiles()) {
            if (f.getName().toLowerCase().endsWith((".txt"))) {
                topics.add(readTitleFromFile(f));
            }
        }

        return topics;
    }

    private static String readTitleFromFile(File f) {
        // read first line in given file
        try (Scanner sc = new Scanner(f, StandardCharsets.UTF_8.name())) {
            return sc.nextLine();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
}
