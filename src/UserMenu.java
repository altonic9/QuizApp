import java.util.ArrayList;
import java.util.SplittableRandom;

public class UserMenu {

    public static void main(String[] args) {

    }

    public static void start() {
        Utility.printHeader("User selection:");

        String[] nav = new String[]{"Create a New User",
                "Load User",
                "Edit existing User",
                "Delete User",
                "Return to Main Menu"};

        int menuPoint = Utility.printNavigation("What would you like to do?", nav);

        switch (menuPoint) {
            case 1:
                createUser();
                break;
            case 2:
                loadUser();
                break;
            case 3:
                editUserName();
                break;
            case 4:
                deleteUser();
                break;
            case 5:
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

    public static void editUserName(){
        String s1 = "Whats your old Username?";
        String s2 = "Whats your new Username?";
        String s3 = "Name is not available, please insert existing Username.";
        String s4 = "Name is not available, please insert new Username.";

        System.out.println(s1);
        String nameOld = Utility.getStringInput();

        while (User.checkUserName(nameOld) == true){
            System.out.println(s3);
            nameOld = Utility.getStringInput();
        }

        System.out.println(s2);
        String nameNew = Utility.getStringInput();

        while (User.checkUserName(nameNew) == false){
            System.out.println(s4);
            nameNew = Utility.getStringInput();
        }

        User.editUserName(nameOld, nameNew);
    }

    public static void deleteUser(){
        String s1 = "Whats you Username?";
        System.out.println(s1);
        String name = Utility.getStringInput();
        User.deleteUser(User.getUserObjectWithName(name));

    }
}
