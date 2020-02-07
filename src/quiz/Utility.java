package quiz;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.UUID;

public class Utility {

    private static Scanner scanner = new Scanner(System.in);
    private static String clearLine = "";

    // initialize static variable
    static {
        for (int i=0; i<100; i++){ clearLine += "\n"; }
    }

    public static int getIntInput(int min, int max) {
        // funktioniert nicht richtig bei falscher eingabe
        System.out.print("How do you want to go on: ");
        while (true) {
            try {
                int x = scanner.nextInt();
                scanner.nextLine(); // eat next line, otherwise next input will fail
                if (x < min || x > max) {
                    throw new InputMismatchException();
                }
                return x;
            } catch (InputMismatchException e) {
                System.out.print("Try again: (" + min + "-" + max + ") ");
                // scanner.nextLine(); Mir ist nicht ganz klar wieso das raus muss, deshalb lass ich es mal noch temporär hier.
            }
        }
    }

    public static String getStringInput() {
        return scanner.nextLine();
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

    public static int printNavigation(String caption, String[] points, boolean lastSpace){
        //prints a bulletpoint list and waits for user input

        System.out.println("\n\t" + caption + "\n");

        int i = 1;
        for (String p : points) {
            System.out.println("\t" + i + ". " + p);
            i++;
            if (i==points.length && lastSpace ) // print last bulletpoint with a space before
                System.out.println();
        }
        System.out.println();
        return getIntInput(1, points.length);
    }

    public static void clearScreen() {
        System.out.println(clearLine);
    }

    public static String generateUUID() {

        UUID uuid = UUID.randomUUID();

        return uuid.toString();
    }

    public static int levensthein(String str1, String str2) {
        // prepares an array for the levensthein algorithm
        int[][] distance = new int[str1.length()+1][str2.length()+1];

        for (int x=0; x <= str1.length(); x++) {
            distance[x][0] = x;
        }

        for (int y=0; y <= str2.length(); y++) {
            distance[0][y] = y;
        }

        return calculateLevensthein(str1, str2, distance);
    }

    private static int calculateLevensthein(String str1, String str2, int[][] distance) {
        // levensthein algorithm
        int d;
        for (int i=0; i < str1.length(); i++) {
            for (int j=0; j < str2.length(); j++) {
                d = 1;
                if (str1.substring(i, i+1).equals(str2.substring(j, j+1))) {
                    d = 0;
                }
                distance[i+1][j+1] = Math.min(Math.min(
                        distance[i][j+1]+1,
                        distance[i+1][j]+1),
                        distance[i][j] + d
                );
            }
        }
        return distance[str1.length()][str2.length()];
    }
}
