import java.util.InputMismatchException;
import java.util.Scanner;

public class Utility {
    private static Scanner scanner = new Scanner(System.in);

//    public static void main(String[] args) {
//        System.out.println(getStringInput());
//        System.out.println(getStringInputMinMax(2,5));
//        getIntInput(1,5);
//    }

    public static int getIntInput(int min, int max) {
        System.out.print("Waehle einen Menu Punkt: ");
        while (true) {
            try {
                int x = scanner.nextInt();
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
        while (true) {
            String text = scanner.nextLine();
            if (text.length() > min && text.length() < max) {
                return text;
            }
        }
    }
}
