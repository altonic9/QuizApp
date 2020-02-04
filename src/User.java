import com.google.gson.Gson;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Scanner;

public class User {

    private static Gson gson = new Gson();
    private String name;
    private int progress;

    public static void main(String[] args) {

    }

    public User(String name, int progress) { this.name = name; this.progress = progress;}


    public void insertUser(){
        //create JSON-String of a Topic Instance

        String jsonString = gson.toJson(this);

        //write jsonString to File
        // filename = user name.user
        try {
            FileWriter myWriter = new FileWriter("Resources/user/" + this.name + ".user");
            myWriter.write(jsonString);
            myWriter.close();
            System.out.println("Successfully wrote to file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static ArrayList<User> getAllUser(){
        ArrayList<User> users = new ArrayList<User>();
        File res_folder = new File("Resources/user/");
        if (res_folder.listFiles() != null){
            for (File f : res_folder.listFiles()) {
                if (f.getName().toLowerCase().endsWith((".user"))) {
                    users.add(readUserFromFile(f));
                }
            }
        }
        return users;
    }

    public static User readUserFromFile(File f) {
        String jsonstr = "";
        try {
            jsonstr = Files.readString(f.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }

        User a = gson.fromJson(jsonstr, User.class);
        return a;
    }

    public static ArrayList getAllUserNames(){
        ArrayList<String> userNames = new ArrayList<String>();

        for (int i = 0; i < getAllUser().size(); i++){
            userNames.add(getAllUser().get(i).name);
        }

        return userNames;
    }

    public static boolean checkUserName(String userName){
        if (getAllUserNames().contains(userName)){
            return false;
        }else
            return true;
    }

    public static void deleteUser(User u) {
        Path path = Paths.get("Resources/user/" + u.name + ".user");
        try {
            Files.delete(path);
        } catch (NoSuchFileException x) {
            System.err.format("%s: no such" + " file or directory%n", path);
        } catch (DirectoryNotEmptyException x) {
            System.err.format("%s not empty%n", path);
        } catch (IOException x) {
            System.err.println(x);
        }
    }

    public static void editUserName(String oldName, String newName){

        ArrayList<User> allUser = getAllUser();
        User uEdit = new User("",0);
        for (int i = 0; i < allUser.size(); i++){
            if (allUser.get(i).name.contains(oldName)){
                uEdit = allUser.get(i);
                deleteUser(allUser.get(i));
            }
        }

        Path source = Paths.get("Resources/user/" + uEdit.name + ".user");
        uEdit.name = newName;
        uEdit.insertUser();

        if (Files.exists(source)){
            try {
                Files.move(source, source.resolveSibling(newName + ".user"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
