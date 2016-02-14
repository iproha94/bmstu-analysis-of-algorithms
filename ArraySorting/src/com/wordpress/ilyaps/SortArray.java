package com.wordpress.ilyaps;

import java.util.Random;

/**
 * Created by ilyap on 28.10.2015.
 */
public abstract class SortArray {
    protected static final int MAX_NUMBER_RANDOM = 10;
    protected int[] array;
    protected int size;

    public void setArray(int[] array) {
        this.array = array.clone();
    }

    protected SortArray() {
        this.array = null;
        this.size = 0;
    }

    protected SortArray(int[] array) {
        this.array = array.clone();
        this.size = array.length;
    }

    protected SortArray(int size) {
        this.size = size;
        setRandomArray(size);
    }

    public abstract void sort();

    public double sortTime(int JOB_COUNT) {
        int[] arrSave = this.getCloneArray();
        long start = System.nanoTime();

        for (int i = 0; i < JOB_COUNT; ++i) {
            sort();
            this.setArray(arrSave);
        }

        double time = (double)(System.nanoTime() - start) / JOB_COUNT;
        return time / 1000000;
    }

    private void setRandomArray(int size) {
        this.array = new int[size];
        final Random random = new Random();

        for (int i = 0; i < size; ++i) {
            array[i] = random.nextInt(MAX_NUMBER_RANDOM);
        }
    }

    public static int[] getRandomArray(int size) {
        int[] arr = new int[size];
        final Random random = new Random();

        for (int i = 0; i < size; ++i) {
            arr[i] = random.nextInt(MAX_NUMBER_RANDOM);
        }

        return arr;
    }

    public void print() {
        System.out.println(array.toString());
        for (int el : array) {
            System.out.printf("%d, ", el);
        }
        System.out.println();
    }

    public static void printArray(int[] arr) {
        System.out.println(arr.toString());
        for (int el : arr) {
            System.out.printf("%d, ", el);
        }
        System.out.println();
    }


    public int[] getCloneArray() {
        return array.clone();
    }

    protected void swap(int i, int j) {
        int t = array[i];
        array[i] = array[j];
        array[j] = t;
    }
}
