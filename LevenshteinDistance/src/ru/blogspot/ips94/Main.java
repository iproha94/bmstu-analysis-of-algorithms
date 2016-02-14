package ru.blogspot.ips94;

public class Main {

    public static int getDistance(String s1, String s2) {
        if (s1.length() < s2.length()) {
            String s = s1;
            s1 = s2;
            s2 = s;
        }

        int n = s1.length() + 1;
        int m = s2.length() + 1;

        int[][] matr = new int[n][m];

        for(int i = 0; i < n; i++)
            matr[i][0] = i;

        for(int i = 0; i < m; i++)
            matr[0][i] = i;

        for(int i = 1; i < n; i++) {
            for(int j = 1; j < m; j++) {
                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    matr[i][j] = matr[i - 1][j - 1];
                } else {
                    matr[i][j] = min(matr[i - 1][j - 1], matr[i][j - 1], matr[i - 1][j]) + 1;
                }

            }
        }

        return matr[n - 1][m - 1];
    }

    private static int min(int i1, int i2, int i3) {
        if (i1 < i2 && i1 < i3) {
            return i1;
        }

        if (i2 < i1 && i2 < i3) {
            return i2;
        }

        return i3;
    }

    public static void main(String[] args) {
	// write your code here
        System.out.println(getDistance("вальпургиева", "варпульгирева"));
    }
}
