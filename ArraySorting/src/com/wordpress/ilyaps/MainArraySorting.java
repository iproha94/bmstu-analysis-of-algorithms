package com.wordpress.ilyaps;

public class MainArraySorting {

    public static void main(String[] args) {

//        System.out.printf("arr.size timTime\tquickTime\n");
//        for (int i = 1000; i < 30001; i += 1000) {
//            int[] arr = SortArray.getRandomArray(i);
//
//            SortArray sat = new SortArrayTimsort(arr);
//            double timTime = sat.sortTime(100000 / i);
//
//            SortArray saq = new SortArrayQuickRandomTail(arr);
//            double quickTime =  saq.sortTime(100000 / i);
//
//            System.out.printf("%d\t%7.5f\t%7.5f\n", i, timTime, quickTime);
//        }
        int[] arr = SortArray.getRandomArray(10);

        SortArray saq2 = new SortArrayQuick(arr);
        saq2.sort();

        SortArray saq1 = new SortArrayQuickRandomTail(arr);
        saq1.sort();
    }
}
