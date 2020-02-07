package quiz;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Profile {

    private static Gson gson = new Gson();
    private String name;
    private HashMap<String, HashMap<String, Boolean>> history = new HashMap<>();
    // e.g. {top1_uuid : {q1_uuid: true, q2: false, ...},
    //       top2_uuid : {q1_uuid: true, q2: true, ...}, ....
    //       }

    public Profile(String name) { this.name = name;}

    public void setName( String name) { this.name = name; }

    public void create(){
        //creates Profile file

        String jsonString = gson.toJson(this);

        //write jsonString to File
        // filename = user name.p
        try {
            FileWriter myWriter = new FileWriter("resources/profiles/" + this.name + ".p");
            myWriter.write(jsonString);
            myWriter.close();
            System.out.println("Successfully wrote to file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static ArrayList<Profile> getAllProfiles(){
        ArrayList<Profile> profiles = new ArrayList<Profile>();
        File res_folder = new File("resources/profiles/");
        if (res_folder.listFiles() != null){
            for (File f : res_folder.listFiles()) {
                if (f.getName().toLowerCase().endsWith((".p"))) {
                    profiles.add(readProfileFromFile(f));
                }
            }
        }
        return profiles;
    }

    public static Profile readProfileFromFile(File f) {
        String jsonstr = "";
        try {
            // jsonstr = Files.readString(f.toPath()); //java 11, doenst work with java 8
            jsonstr = new String(Files.readAllBytes(f.toPath()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Profile a = gson.fromJson(jsonstr, Profile.class);
        return a;
    }

    public static ArrayList getAllProfileNames(){
        ArrayList<Profile> users = getAllProfiles();
        ArrayList<String> names = new ArrayList<String>();

        for (Profile u : users){
            names.add(u.name);
        }

        return names;
    }

    public static boolean exists(String userName) {
        return getAllProfileNames().contains(userName);
    }

    public void delete() {
       try {
           Path path = Paths.get("resources/profiles/" + this.name + ".p");
           try {
               Files.delete(path);
           } catch (NoSuchFileException x) {
               System.err.format("%s: no such" + " file or directory%n", path);
           } catch (DirectoryNotEmptyException x) {
               System.err.format("%s not empty%n", path);
           } catch (IOException x) {
               System.err.println(x);
           }
       }catch (NullPointerException x){
           System.out.println("User does not exits!");
           ProfileMenu.deleteProfile();
       }

    }

    public static Profile findProfile(String name){
        ArrayList<Profile> profiles = getAllProfiles();
        for (Profile p : profiles){
            if ( p.name.equals(name) )
                return p;
        }
        return null;
    }

    public void changeName(String name) {

        String oldName = this.name;
        this.setName(name);
        this.create(); //writes it to file

        findProfile(oldName).delete();


    }

    public void addToHistory(String topicUUID, String questionUUID, boolean pass) {

        if (history.containsKey(topicUUID)) {
            history.get(topicUUID).put(questionUUID, pass);
        }
        else {
            // create the sub-hashmap that contains questions outcome
            HashMap<String, Boolean> questionMap = new HashMap<String, Boolean>();
            questionMap.put(questionUUID, pass);
            history.put(topicUUID, questionMap);
        }
        this.create();
    }

    public HashMap<String, float[]> getHistory() {

        HashMap<String, float[]> result = new HashMap <String, float[]>();

        // returns completed %, correct % to each topic
        // since topics can change by editor, ignore statistics to non-existend questions
        
        // loop through all topics in history
        for (String topicID : history.keySet()) {
            
            Set<String> questionIdsInHistory = history.get(topicID).keySet();
            Set<String> questionIdsInTopic = new HashSet<>();
            
            Topic t = Topic.getById(topicID);
            if (t != null) {
                for (Question q : t.getAllQuestions()) { questionIdsInTopic.add(q.getId()); }

                //intersection of sets - stored in questionIdsInTopic
                questionIdsInTopic.retainAll(questionIdsInHistory);
                float[] totalAndPositives= crunchNumbers(questionIdsInTopic, history.get(topicID), t.getAllQuestions().size());

                result.put(t.getName(), totalAndPositives);
            }
            else {
                // topic doesnt exist anymore
                history.remove(topicID);
            }

        }

        return result;

    }

    private static float[] crunchNumbers(Set<String> relevantQuestions, HashMap<String, Boolean> questionsInHistory, int all) {
        int pos = 0;
        for (String questionId : relevantQuestions) {
            if (questionsInHistory.get(questionId)) { pos++; }
        }

        float total = (float)relevantQuestions.size()/all * 100;
        float positiv = (float)pos / all * 100;

        return new float[] {total, positiv};
    }
}
