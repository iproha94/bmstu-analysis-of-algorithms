package com.wordpress.ilyaps;

/**
 * Created by ilyap on 28.10.2015.
 */
public class SortArrayQuick extends SortArray {
    public int num;
    public int max;


    public SortArrayQuick() {
        super();
    }

    public SortArrayQuick(int[] array) {
        super(array);
    }

    public SortArrayQuick(int size) {
        super(size);
    }

    protected int partition(int p, int r) {
        int x = array[r];
        int i = p - 1;
        for (int j = p; j < r; ++j) {
            if (array[j] <= x) {
                i += 1;
                swap(i, j);
            }
        }
        swap(i + 1, r);
        return i + 1;
    }

    
    protected void quicksort(int p, int r) {
        num++;
        if (p < r) {
            int q = partition(p, r);
            quicksort(p, q - 1);
            quicksort(q + 1, r);
        }
    }

    public void sort() {
        num = 0;
        quicksort(0, size - 1);
        System.out.println("Quick num  0f call = " + num);

    }
}
