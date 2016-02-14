package com.wordpress.ilyaps;

import java.util.Random;

/**
 * Created by ilyap on 28.10.2015.
 */
public class SortArrayQuickRandomTail extends SortArrayQuick {
    Random rand = new Random();
    public int num;
    public int max;


    public SortArrayQuickRandomTail() {
    }

    public SortArrayQuickRandomTail(int[] array) {
        super(array);
    }

    public SortArrayQuickRandomTail(int size) {
        super(size);
    }

    private int randPartition(int p, int r) {
        int i = rand.nextInt(r - p + 1) + p;
        swap(i, r);
        return partition(p, r);
    }

    private void tailRecursiveQuicksort(int p, int r) {
        num++;
        while (p < r) {
            int q = randPartition(p, r);
            tailRecursiveQuicksort(p, q - 1);
            p = q + 1;
        }
    }


    public void sort() {
        num = 0;
        tailRecursiveQuicksort(0, size - 1);
        System.out.println("QuickRandomTail num 0f call= " + num );
    }
}
