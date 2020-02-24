package quiz;

import java.util.UUID;


public class Utility {

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
