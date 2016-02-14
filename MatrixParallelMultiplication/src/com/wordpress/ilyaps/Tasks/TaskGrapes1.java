package com.wordpress.ilyaps.Tasks;

/**
 * Created by ilyap on 16.12.2015.
 */

import java.util.concurrent.Callable;

public class TaskGrapes1 implements Callable<Void> {
    private int[] factor;
    private int[][] matr;
    private int i;
    private int b;

    public TaskGrapes1(int[] factor, int[][] matr, int i, int b) {
        this.factor = factor;
        this.i = i;
        this.b = b;
        this.matr = matr;
    }

    @Override
    public Void call() {
        factor[i] = 0;
        for (int j = 1; j < b; j+=2){
            factor[i] += matr[i][j - 1] * matr[i][j];
        }
        return null;
    }
}
