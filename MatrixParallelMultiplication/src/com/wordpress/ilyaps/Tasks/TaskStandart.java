package com.wordpress.ilyaps.Tasks;

import java.util.concurrent.Callable;

/**
 * Created by ilyap on 16.12.2015.
 */
public final class TaskStandart implements Callable<Void> {
    private int i;
    private int[][] matrA;
    private int[][] matrBT;
    private int[][] matrC;

    public TaskStandart(int i, int[][] matrA, int[][] matrBT, int[][] matrC) {
        this.i = i;
        this.matrA = matrA;
        this.matrBT = matrBT;
        this.matrC = matrC;
    }

    @Override
    public Void call() {
        for (int j = 0; j < matrBT.length; j++) {
            matrC[i][j] = 0;
            for (int k = 0; k < matrA[0].length; k++) {
                matrC[i][j] += matrA[i][k] * matrBT[j][k];
            }
        }
        return null;
    }
}