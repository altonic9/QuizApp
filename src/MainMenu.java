public class MainMenu {

    public static void start() {
        String caption = "\n\r\t==========================" +
                "\n\r\t||\tWelcome to QUIZAPP  ||\n" +
                "\t==========================";
        String nav = "\n\r\t What would you like to do?\n" +
                "\n\r\t 1. Create New Profile" +
                "\n\r\t 2. Continue from existing Profile" +
                "\n\r\t 3. Start Editor\n";

        System.out.println(caption);
        System.out.println(nav);

        int menuPoint = Utility.getIntInput(1, 3);

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
