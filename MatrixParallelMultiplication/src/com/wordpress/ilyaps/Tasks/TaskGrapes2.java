package com.wordpress.ilyaps.Tasks;

import java.util.concurrent.Callable;

/**
 * Created by ilyap on 16.12.2015.
 */
public class TaskGrapes2 implements Callable<Void> {
    private int[] columnFactor;
    private int[] rowFactor;

    private int[][] matrA;
    private int[][] matrB;
    private int[][] matrC;
    private int i;
    private int c;
    private int b;

    public TaskGrapes2(int[] columnFactor,int[] rowFactor, int[][] matrA,int[][] matrB,int[][] matrC, int i, int c, int b) {
        this.columnFactor = columnFactor;
        this.rowFactor = rowFactor;
        this.i = i;
        this.c = c;
        this.b = b;
        this.matrA = matrA;
        this.matrB = matrB;
        this.matrC = matrC;
    }

    @Override
    public Void call() {
        for (int j = 0; j < c; ++j) {

            matrC[i][j] = -rowFactor[i] - columnFactor[j];
            for (int k = 1; k < b; k += 2) {
                matrC[i][j] += (matrA[i][k - 1] + matrB[j][k]) * (matrA[i][k] + matrB[j][k - 1]);
            }

        }
        return null;
    }
}