import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;

public class QuizApp {

    public static void main(String[] args) {

        ArrayList<String> topics = readTopicsFromFiles();
        for ( String t : topics)
            System.out.println(t);

        createTopic();
    }

    private static ArrayList<String> readTopicsFromFiles(){
        // read first line in every txt-file in resource folder
        ArrayList<String> topics = new ArrayList<String>();

        //get all files in Resources
        File res_folder = new File("Resources");
        for( File f : res_folder.listFiles() )
        {
            if (f.getName().toLowerCase().endsWith((".txt")))
            {
                topics.add(readTitleFromFile(f));
            }
        }

        return topics;
    }

    private static String readTitleFromFile(File f){
        // read first line in given file
        try (Scanner sc = new Scanner(f, StandardCharsets.UTF_8.name())) {
            return sc.nextLine();
        }
        catch (IOException e) {
            e.printStackTrace();
            return "";
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
    
//    // from JSON to object
//    yourObject o = gson.fromJson(JSONString,yourObject.class);
}

