import com.google.gson.Gson;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;

public class Profile {

    private static Gson gson = new Gson();
    private String name;
    private ArrayList<History> history;

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
            jsonstr = Files.readString(f.toPath());
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
           Path path = Paths.get("resources/user/" + this.name + ".p");
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

        try {
            Profile newP = (Profile) this.clone();
            newP.setName( name );
            newP.create(); //writes it to file

            this.delete();
        }
        catch (CloneNotSupportedException x) {
            System.err.println(x);
        }
    }

}