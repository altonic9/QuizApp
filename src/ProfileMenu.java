import java.util.ArrayList;
import java.util.HashMap;

public class ProfileMenu {


    public static void start() {
        Utility.clearScreen();
        Utility.printHeader("Profile Managment");

        String[] nav = {"Show Profile", "Edit existing Profile", "Delete Profile", "Return to Main Menu"};
        int menuPoint = Utility.printNavigation("What would you like to do?", nav, true);

        switch (menuPoint) {
            case 1:
                showProfile();
                break;
            case 2:
                editUserName();
                break;
            case 3:
                deleteProfile();
                break;
            case 4:
                MainMenu.start();
                break;
        }
    }

    public static void showProfile() {
        Utility.clearScreen();

        ArrayList<String> names = Profile.getAllProfileNames();
        // show profile selection and get user input
        int input = Utility.printNavigation("Choose your Profile: ", names.toArray(new String[0]), false);

        // find profile by name
        Profile p = Profile.findProfile(names.get(input -1));

        HashMap<String, float[]> statistics = p.getHistory();

        Utility.clearScreen();
        for (String topic : statistics.keySet()) {
            float[] result = statistics.get(topic);
            System.out.println("\n\tTopic \t\t total \t positives");
            System.out.println(String.format("\n\t%s \t\t %02.0f%% \t %02.0f%% \n", topic, result[0], result[1]));
        }
    }

    public static void editUserName(){

        System.out.println("\nWhats your old Profile's name?\n");
        String oldName = Utility.getStringInput();

        while (!Profile.exists(oldName)){
            System.out.println("\nProfile doesn't exist, please try again:\n");
            oldName = Utility.getStringInput();
        }

        System.out.println("\nChoose new Profile's name:\n");
        String newName = Utility.getStringInput();

        while (Profile.exists(newName)){
            System.out.println("\nName is already taken, please try again:\n");
            newName = Utility.getStringInput();
        }

        Profile.findProfile(oldName).changeName(newName);

        start();
    }

    public static void deleteProfile(){

        System.out.println("\nWhats your Profile's Name?\n");
        String name = Utility.getStringInput();
        Profile.findProfile(name).delete();
        System.out.println("\nProfile deleted!\n");

        start();

    }
}
