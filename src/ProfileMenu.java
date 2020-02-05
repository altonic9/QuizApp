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

    public static void showProfile() {}

    public static void editUserName(){

        System.out.println("Whats your old Profile's name?");
        String oldName = Utility.getStringInput();

        while (!Profile.exists(oldName)){
            System.out.println("Profile doesn't exist, please try again: ");
            oldName = Utility.getStringInput();
        }

        System.out.println("Choose new Profile's name: ");
        String newName = Utility.getStringInput();

        while (Profile.exists(newName)){
            System.out.println("Name is already taken, please try again: ");
            newName = Utility.getStringInput();
        }

        Profile.findProfile(oldName).changeName(newName);
    }

    public static void deleteProfile(){

        System.out.println("Whats your Profile's Name?");
        String name = Utility.getStringInput();
        Profile.findProfile(name).delete();
        System.out.println("Profile deleted!");

    }
}
