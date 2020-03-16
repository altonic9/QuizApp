package quiz;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;


public class Utility {

    private static List<Character> allowedChars;

    static {
        allowedChars = Arrays.asList('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '.', ',', '-', '+');
    }

    public static String generateUUID() {

        UUID uuid = UUID.randomUUID();

        return uuid.toString();
    }

    public static boolean isNumeric(String s) {
        // convert String to char[] array
        char[] chars = s.toCharArray();

        // iterate through char array and check if char is part of allowed chars
        for (char ch : chars) {
            if ( !allowedChars.contains(ch) ) {
                return false;
            }
        }

        return true;
    }

    public static int levenstheinDistance(String str1, String str2) {
        str1 = str1.toLowerCase();
        str2 = str2.toLowerCase();

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
