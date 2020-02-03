public class MainMenu {

    public static void start() {
        Utility.printHeader("Main Menu");

        String[] nav = {"Create New Profile", "Continue from existing Profile", "Start Editor"};
        int menuPoint = Utility.printNavigation("What would you like to do?", nav);

        switch (menuPoint) {
            case 1:
                //User.create();
                break;
            case 2:
                //User.load;
                break;
            case 3:
                EditorMenu.start();
                break;
        }
    }
}
