import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;

public class User {

    public ArrayList<String> userNames = new ArrayList<>();

    public static void main(String[] args) {

    }

    public User(String userName){
        this.userNames.add(userName);
    }

    public static ArrayList<String> getUsers(){
        ArrayList<String> users = readUser();
        return users;
    }

    public static void insertUser(){

    }

    public static ArrayList<String> readUser(){
        ArrayList<String> users = new ArrayList<String>();

        File res_folder = new File("User");
        for (File f : res_folder.listFiles()) {
            if (f.getName().toLowerCase().endsWith((".txt"))) {
                users.add(readUserFromFile(f));
            }
        }
        return users;
    }

    private static String readUserFromFile(File f) {
        // read first line in given file
        try (Scanner sc = new Scanner(f, StandardCharsets.UTF_8.name())) {
            return sc.nextLine();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    //Kommt ins main
     /*public static void newUser(){
        var scanner = new Scanner(System.in);

        System.out.println("Username eingeben: ");
        String userName = scanner.nextLine();


        if (User.readUsers.equals(userName)){
               sout("Name vergeben);
        }else{
        User u = new User(userName);
        User.insertUser();
        }
    }*/
}
