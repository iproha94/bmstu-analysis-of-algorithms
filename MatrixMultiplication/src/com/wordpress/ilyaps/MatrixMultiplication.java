package com.wordpress.ilyaps;

import java.util.Random;

/**
 * Created by ilyap on 16.12.2015.
 */
public class MatrixMultiplication {
    private int maxNumberRandom = 100;

    private int[][] matrA;
    private int[][] matrB;
    private int[][] matrC;
    private int[][] matrBT;

    public static int[][] getRandomMatrix(int n, int m, int maxRandom) {
        int[][] matr = new int[n][m];
        final Random random = new Random();

        for (int[] row : matr) {
            for (int i = 0; i < row.length; ++i) {
                row[i] = random.nextInt(maxRandom);
            }
        }

        return matr;
    }

    public void setRandomMatrixA(int n, int m) {
        matrA = getRandomMatrix(n, m, maxNumberRandom);
    }

    public void setRandomMatrixB(int n, int m) {
        matrB = getRandomMatrix(n, m, maxNumberRandom);
        matrBT = matrixTranspose(matrB);
    }

    public static void printMatrix(int[][] matr, int maxNumber) {
        System.out.println(matr);

        for (int[] row : matr) {
            for (int item : row) {
                System.out.printf("%" + Math.getExponent(maxNumber) + "d", item);
            }
            System.out.println();
        }
    }

    public void printMatrixA() {
        printMatrix(matrA, maxNumberRandom);
    }

    public void printMatrixB() {
        printMatrix(matrB, maxNumberRandom);
    }

    public void printMatrixC() {
        printMatrix(matrC, maxNumberRandom);
    }


    public static int[][] matrixTranspose(int[][] matr) {
        int[][] matr2 = new int[matr[0].length][matr.length];

        for (int i = 0; i <  matr.length; ++i) {
            for (int j = 0; j <  matr[0].length; ++j) {
                matr2[j][i] = matr[i][j];
            }
        }

        return matr2;
    }

    public void multiplyMatrixStandard() {
        if (matrA[0].length != matrB.length) {
            throw new RuntimeException();
        }
        matrC = new int[matrA.length][matrBT.length];

        for (int i = 0; i < matrA.length; i++) {
            for (int j = 0; j < matrBT.length; j++) {
                matrC[i][j] = 0;
                for (int k = 0; k < matrA[0].length; k++) {
                    matrC[i][j] += matrA[i][k] * matrBT[j][k];
                }
            }
        }
    }

    public void multiplyMatrixGrapes() {
        if (matrA[0].length != matrB.length) {
            throw new RuntimeException();
        }

        int a = matrA.length;
        int b = matrA[0].length;
        int c = matrBT.length;

        matrC = new int[a][c];

        int[] rowFactor = new int[a];
        int[] columnFactor = new int[c];

        for (int i = 0; i < a; ++i) {
            rowFactor[i] = 0;
            for (int j = 1; j < b; j+=2){
                rowFactor[i] += matrA[i][j - 1] * matrA[i][j];
            }
        }

        for (int i = 0; i < c; ++i) {
            columnFactor[i] = 0;
            for (int j = 1; j < b; j+=2){
                columnFactor[i] += matrBT[i][j - 1] * matrBT[i][j];
            }
        }


        for (int i = 0; i < a; ++i) {
            for (int j = 0; j < c; ++j) {
                matrC[i][j] = -rowFactor[i] - columnFactor[j];
                for (int k = 1; k < b; k += 2) {
                    matrC[i][j] += (matrA[i][k - 1] + matrBT[j][k]) *
                            (matrA[i][k] + matrBT[j][k - 1]);
                }
            }
        }

        if (b % 2 == 1) {
            for (int i = 0; i < a; ++i) {
                for (int j = 0; j < c; ++j) {
                    matrC[i][j] += matrA[i][b - 1] * matrBT[j][b - 1];
                }
            }
        }
    }

    public void multiplyMatrixGrapesSlow() {
        if (matrA[0].length != matrB.length) {
            throw new RuntimeException();
        }

        int a = matrA.length;
        int b = matrA[0].length;
        int c = matrBT.length;

        matrC = new int[a][c];

        int[] rowFactor = new int[a];
        int[] columnFactor = new int[c];

        for (int i = 0; i < a; ++i) {
            rowFactor[i] = 0;
            for (int j = 1; j < b; j+=2){
                rowFactor[i] += matrA[i][j - 1] * matrA[i][j];
            }
        }

        for (int i = 0; i < c; ++i) {
            columnFactor[i] = 0;
            for (int j = 1; j < b; j+=2){
                columnFactor[i] += matrBT[i][j - 1] * matrBT[i][j];
            }
        }


        for (int i = 0; i < a; ++i) {
            for (int j = 0; j < c; ++j) {
                matrC[i][j] = -rowFactor[i] - columnFactor[j];
                for (int k = 0; k < b / 2; k++) {
                    matrC[i][j] = matrC[i][j] +
                            (matrA[i][2 * k] + matrBT[j][2 * k + 1]) *
                            (matrA[i][2 * k + 1] + matrBT[j][2 * k]);
                }
            }
        }

        if (b % 2 == 1) {
            for (int i = 0; i < a; ++i) {
                for (int j = 0; j < c; ++j) {
                    matrC[i][j] += matrA[i][b - 1] * matrBT[j][b - 1];
                }
            }
        }
    }
}
