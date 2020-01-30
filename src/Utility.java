import java.util.InputMismatchException;
import java.util.Scanner;

public class Utility {
//     public static void main(String[] args) {
//        getIntInput();
//    }

    public static int getIntInput() {
        int x = 0;
        System.out.print("Waehle einen Menu Punkt: ");
        while (x == 0) {
            x = checkIntInput();
        }
        return x;
    }

    private static int checkIntInput() {
        Scanner scanner = new Scanner(System.in);
        try {
            int x = scanner.nextInt();
            if (x <= 0 || x > 9) {
                throw new InputMismatchException();
            }
            return x;
        } catch (InputMismatchException e) {
            System.out.print("Versuche es erneut: (1-9) ");
            return 0;
        }
    }
}
