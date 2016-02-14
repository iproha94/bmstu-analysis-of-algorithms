package com.wordpress.ilyaps;

/**
 * Created by ilyap on 28.10.2015.
 */
public class SortArrayInsert extends SortArray {
    public SortArrayInsert() {
    }

    public SortArrayInsert(int[] array) {
        super(array);
    }

    public SortArrayInsert(int size) {
        super(size);
    }

    @Override
    public void sort() {
        for (int j = 1; j < size; ++j) {
            int key = array[j];
            int i = j - 1;
            while (i >= 0 && array[i] > key){
                array[i + 1] = array[i];
                --i;
            }
            array[i + 1] = key;
        }
    }
}
