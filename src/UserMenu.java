import java.util.ArrayList;
import java.util.SplittableRandom;

public class UserMenu {

    public static void start() {
        Utility.printHeader("User selection:");

        String[] nav = new String[]{"Start with New User", "Load User", "Return to Main Menu"};

        int menuPoint = Utility.printNavigation("What would you like to do?", nav);

        switch (menuPoint) {
            case 1:
                createUser();
                break;
            case 2:
                loadUser();
                break;
            case 3:
                MainMenu.start();
                break;
        }
    }

    public static void createUser(){
        String s1 = "Please insert Username";
        String s2 = "Username exits, please insert new Username!";

        System.out.println(s1);
        String name = Utility.getStringInput();

        while (User.checkUserName(name) == false){
            System.out.println(s2);
            name = Utility.getStringInput();
        }

        User u = new User(name);
        u.insertUser();

    }

    public static void loadUser(){
        ArrayList<String> names = User.getAllUserNames();
        
            Utility.printNavigation("Choose your User!", names.toArray(new String[0]));
    }

}
