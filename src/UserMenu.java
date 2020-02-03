public class UserMenu {

    public static void main(String[] args) {

    }

    public static void start() {
        String caption = "\n\r\t==========================" +
                "\n\r\t||\t     Editor         ||\n" +
                "\t==========================";

        String nav = "\n\r\t 1. Create a New User" +
                "\n\r\t 2. Edit Existing User" +
                "\n\r\t 3. Return to Main Menu\n";

        System.out.println(caption);
        System.out.println(nav);

        int menuPoint = Utility.getIntInput(1, 3);

        switch (menuPoint) {
            case 1:
                createUser();
                break;
            case 2:
                //editUser
                break;
            case 3:
                MainMenu.start();
                break;
        }
    }

    public static void createUser(){
        String s1 = "Please insert Username: ";
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

    public static void editUser(){
        String s1 = "Which of the following User are you?";

        for (int i = 0; i < User.getAllUserNames().size(); i++){
            
        }
    }
}
