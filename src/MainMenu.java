public class MainMenu {

    public static void start() {
        Utility.printHeader("Main Menu");

        String[] nav = {"Start Game!", "Start Editor", "Play Game (provisorisch)", "Exit Game"};
        int menuPoint = Utility.printNavigation("What would you like to do?", nav);

        switch (menuPoint) {
            case 1:
                UserMenu.start();
                break;
            case 2:
                EditorMenu.start();
                break;
            case 3:
                Game.start();
                break;
            case 4:
                System.exit(0);
        }
    }
}
