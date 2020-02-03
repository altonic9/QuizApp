import java.util.InputMismatchException;
import java.util.Scanner;

public class Utility {

    private static Scanner scanner = new Scanner(System.in);
    private static String clearLine = "";

    //initialize static variable
    static {
        for (int i=0; i<100; i++){ clearLine += "\n"; }
    }

    public static int getIntInput(int min, int max) {
        System.out.print("Waehle einen Menu Punkt: ");
        while (true) {
            try {
                int x = scanner.nextInt();
                scanner.nextLine(); //eat next line, otherwise next input will fail
                if (x < min || x > max) {
                    throw new InputMismatchException();
                }
                return x;
            } catch (InputMismatchException e) {
                System.out.print("Versuche es erneut: (" + min + "-" + max + ") ");
                scanner.nextLine();
            }
        }
    }

    public static String getStringInput() {
        return scanner.nextLine();
    }

    public static String getStringInputMinMax(int min, int max) {
        //funktioniert nicht!!
        while (true) {
            String text = scanner.nextLine();
            if (text.length() > min && text.length() < max) {
                return text;
            }
        }
    }

    public static boolean getConfirmation(String text) {
        System.out.println("\n" + text);
        System.out.println("\n\t 1. Yes \t 2. No\n");
        int y = getIntInput(1, 2);

        return (y == 1);
    }

    public static void printHeader(String text) {

        String line = "\n\t==============================\n";

        int space_count = (30 - text.length()) / 2 - 1;
        String space = "";
        for (int i=0; i<space_count; i++) { space += " "; }

        // add a space to left side if text length is not even
        String header = line + "\t|" + space + text + space;
        if (text.length()%2 == 0) {
            header += "|" + line;
        }
        else {
            header += " |" + line;
        }

        System.out.println(header);
    }

    public static int printNavigation(String caption, String[] points){
        //prints a bulletpoint list and waits for user input

        System.out.println("\t" + caption + "\n");

        int i = 1;
        for (String p : points) {
            System.out.println("\t" + i + ". " + p);
            i++;
        }
        System.out.println();
        return getIntInput(1, points.length);
    }

    public static void clearScreen() {
        System.out.println(clearLine);
    }
}
