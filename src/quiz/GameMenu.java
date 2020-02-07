package quiz;

import java.util.ArrayList;

public class GameMenu {

    public static void start() {
        Utility.clearScreen();
        Utility.printHeader("Game Menu:");

        String[] nav = new String[]{"Start with new Profile", "Load Profile", "Return to Main Menu"};

        int menuPoint = Utility.printNavigation("What would you like to do?", nav, true);

        switch (menuPoint) {
            case 1:
                createProfile();
                break;
            case 2:
                loadProfile();
                break;
            case 3:
                MainMenu.start();
                break;
        }
    }

    public static void createProfile(){
        Utility.clearScreen();

        System.out.println("Please enter your Name");
        String name = Utility.getStringInput();

        while ( Profile.exists(name) ){
            System.out.println("Name's already taken, please try again: ");
            name = Utility.getStringInput();
        }

        Profile p = new Profile(name);
        p.create();

    }

    public static void loadProfile(){
        Utility.clearScreen();

        ArrayList<String> names = Profile.getAllProfileNames();
        // show profile selection and get user input
        int input = Utility.printNavigation("Choose your Profile: ", names.toArray(new String[0]), false);

        // find profile by name
        Profile p = Profile.findProfile(names.get(input -1));
        Game.start(p);
    }

}

