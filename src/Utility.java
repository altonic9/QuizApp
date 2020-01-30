import java.util.InputMismatchException;
import java.util.Scanner;

public class Utility {
//     public static void main(String[] args) {
//        getIntInput(1,3);
//    }

    public static int getIntInput(int min, int max) {
        int x = 0;
        System.out.print("Waehle einen Menu Punkt: ");
        while (x == 0) {
            x = checkIntInput(min, max);
        }
        return x;
    }

    private static int checkIntInput(int min, int max) {
        Scanner scanner = new Scanner(System.in);
        try {
            int x = scanner.nextInt();
            if (x < min || x > max) {
                throw new InputMismatchException();
            }
            return x;
        } catch (InputMismatchException e) {
            System.out.print("Versuche es erneut: (" + min + "-" + max + ") ");
            return 0;
        }
    }
}
