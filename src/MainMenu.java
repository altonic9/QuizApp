public class MainMenu {

    public static void start() {
        Utility.clearScreen();
        Utility.printHeader("QuizApp");

        String[] nav = {"Start Game!", "Manage Profiles", "Start Editor", "Exit Game"};
        int menuPoint = Utility.printNavigation("What would you like to do?", nav, true);

        switch (menuPoint) {
            case 1:
                GameMenu.start();
                break;
            case 2:
                ProfileMenu.start();
                break;
            case 3:
                EditorMenu.start();
                break;
            case 4:
                Utility.clearScreen();
                System.exit(0);

        }
    }
}
