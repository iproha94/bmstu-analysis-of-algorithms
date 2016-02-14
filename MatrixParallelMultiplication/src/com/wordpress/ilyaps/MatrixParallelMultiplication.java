package com.wordpress.ilyaps;

import com.wordpress.ilyaps.Tasks.TaskGrapes1;
import com.wordpress.ilyaps.Tasks.TaskGrapes2;
import com.wordpress.ilyaps.Tasks.TaskStandart;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by ilyap on 16.12.2015.
 */
public class MatrixParallelMultiplication {
    private int timePause = 100;
    private int maxNumberRandom = 100;
    private int countTreads;

    private int[][] matrA;
    private int[][] matrB;
    private int[][] matrC;
    private int[][] matrBT;

    private ExecutorService executorService;
    private List<Future<Void>> listFutures = new ArrayList<>(1000);

    public void setCountTreads(int countTreads) {
        this.countTreads = countTreads;
    }

    public void setTimePause(int timePause) {
        this.timePause = timePause;
    }

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
        executorService = Executors.newFixedThreadPool(countTreads);

        if (matrA[0].length != matrB.length) {
            throw new RuntimeException();
        }
        matrC = new int[matrA.length][matrBT.length];

        for (int i = 0; i < matrA.length; i++) {
            Callable<Void> task = new TaskStandart(i, matrA, matrBT, matrC);
            listFutures.add(executorService.submit(task) );
        }

        waitAndClearetasks();

        executorService.shutdown();
    }

    private void waitAndClearetasks() {
        for (Future<Void> future : listFutures) {
            while(!future.isDone()) {
                try {
                    synchronized (this) {
                        this.wait(timePause);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        listFutures.clear();
    }

    public void multiplyMatrixGrapes() {
        executorService = Executors.newFixedThreadPool(countTreads);

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
            Callable<Void> task = new TaskGrapes1(rowFactor, matrA, i,  b);
            listFutures.add( executorService.submit(task));
        }

        for (int i = 0; i < c; ++i) {
            Callable<Void> task = new TaskGrapes1(columnFactor, matrBT, i,  b);
            listFutures.add( executorService.submit(task) );
        }

        waitAndClearetasks();

        for (int i = 0; i < a; ++i) {
            Callable<Void> task = new TaskGrapes2(columnFactor, rowFactor, matrA, matrBT,  matrC, i, c, b);
            listFutures.add( executorService.submit(task) );
        }

        waitAndClearetasks();

        executorService.shutdown();

        if (b % 2 == 1) {
            for (int i = 0; i < a; ++i) {
                for (int j = 0; j < c; ++j) {
                    matrC[i][j] += matrA[i][b - 1] * matrBT[j][b - 1];
                }
            }
        }
    }
}
