package com.wordpress.ilyaps;

import java.util.*;

/**
 * Created by ilyap on 28.10.2015.
 */
public class SortArrayTimsort extends SortArray{
    private static final int MAX_FOR_GALLOP = 7;

    class Pair {
        public int start;
        public int size;

        public Pair(int s, int i) {
            this.start = s;
            this.size = i;
        }
    }

    private int minrun;
    private static List<Pair> arrayPair = new ArrayList<>();

    public SortArrayTimsort() {
    }

    public SortArrayTimsort(int[] array) {
        super(array);
        minrun = getMinrun(size);
    }

    public SortArrayTimsort(int size) {
        super(size);
        minrun = getMinrun(size);
    }

    private int getMinrun(int n) {
        int r = 0;           /* ������ 1 ���� ����� ��������� ����� ����� ���� �� 1 ��������� */
        while (n >= 64) {
            r |= n & 1;
            n >>= 1;
        }
        return n + r;
    }

    private void invers(int s, int f) {
        int m = s + (f - s) / 2;
        for (int i = s; i <= m; ++i) {
            swap(i, f + s - i);
        }
    }

    @Override
    public void sort() {
        step(0);
        Stack<Pair> st = new Stack<>();
        int i = 0;

        while (i < arrayPair.size()) {
            st.add(arrayPair.get(i++));
            while ((st.size() > 2)
                    && !((st.get(st.size() - 1).size > st.get(st.size() - 2).size + st.get(st.size() - 3).size)
                        && (st.get(st.size() - 2).size > st.get(st.size() - 3).size))) {

                if (st.get(st.size() - 1).size > st.get(st.size() - 3).size) {
                    merge(st.get(st.size() - 3).start, st.get(st.size() - 3).size,
                            st.get(st.size() - 2).start, st.get(st.size() - 2).size);
                    st.get(st.size() - 3).size += st.get(st.size() - 2).size;
                    st.remove(st.size() - 2);
                } else {
                    merge(st.get(st.size() - 2).start, st.get(st.size() - 2).size,
                            st.get(st.size() - 1).start, st.get(st.size() - 1).size);
                    st.get(st.size() - 2).size += st.get(st.size() - 1).size;
                    st.remove(st.size() - 1);
                }
            }
        }

        while (st.size() > 1) {
            merge(st.get(st.size() - 2).start, st.get(st.size() - 2).size,
                    st.get(st.size() - 1).start, st.get(st.size() - 1).size);
            st.get(st.size() - 2).size += st.get(st.size() - 1).size;
            st.remove(st.size() - 1);
        }

        arrayPair.clear();
    }


    private void step(int s) {
        boolean desc;
        int i = s;

        if (i >= size - 1) {
            arrayPair.add(new Pair(s, 1));
            return;
        } else {
            desc = array[i] > array[i + 1];
        }

        while (i < size - 1 && (array[i] > array[i + 1]) == desc) {
             ++i;
        }

        if (desc) {
            invers(s, i);
        }

        if (i - s + 1 < minrun) {
            i = s + minrun - 1;
            if (i >= size) {
                i = size - 1;
            }
            //�������� ����� ��������� �� ���� �� minrun
        }

        sortInsert(s, i);
        arrayPair.add(new Pair(s, i + 1 - s));
        if (i < size - 1) {
            step(i + 1);
        }
    }

    private void merge(int firstStartIndex, int firstSize, int secondStartIndex, int secondSize) {
        int[] newFirstPart = new int[firstSize];
        int[] newSecondPart = new int[secondSize];

        for (int i = 0; i < firstSize; ++i) {
            newFirstPart[i] = array[firstStartIndex + i];
        }
        for (int i = 0; i < secondSize; ++i) {
            newSecondPart[i] = array[secondStartIndex + i];
        }

        int pointFirst = 0;
        int pointSecond = 0;
        int pointResult = firstStartIndex;

        int flagFirst = 0;
        int flagSecond = 0;


        while (pointFirst < firstSize && pointSecond < secondSize) {
            if (newFirstPart[pointFirst] < newSecondPart[pointSecond]) {
                ++flagFirst;
                flagSecond = 0;

                if (flagFirst == MAX_FOR_GALLOP) {
                    flagFirst = 0;
                    int numGallop = 0;
                    try {
                        while (newFirstPart[pointFirst + (int) Math.pow(2, numGallop)] < newSecondPart[pointSecond]) {
                            ++numGallop;
                        }
                    } catch (Exception e) {
                        numGallop--;
                    }
                    for (int i = 0; i < (int) Math.pow(2, numGallop); ++i) {
                        array[pointResult++] = newFirstPart[pointFirst++];
                    }
                } else {

                    array[pointResult++] = newFirstPart[pointFirst++];
                }
            } else {
                ++flagSecond;
                flagFirst = 0;

                if (flagSecond == MAX_FOR_GALLOP) {
                    flagSecond = 0;
                    int numGallop = 0;
                    try {
                        while (newSecondPart[pointSecond + (int) Math.pow(2, numGallop)] < newFirstPart[pointFirst]) {
                            ++numGallop;
                        }
                    } catch (Exception e) {
                        numGallop--;
                    }
                    for (int i = 0; i < (int) Math.pow(2, numGallop); ++i) {
                        array[pointResult++] = newSecondPart[pointSecond++];
                    }
                } else {

                    array[pointResult++] = newSecondPart[pointSecond++];
                }
            }
        }


        if (pointFirst < firstSize) {
            for (int i = pointFirst; i < firstSize; ++i) {
                array[pointResult++] = newFirstPart[i];
            }
        } else if (pointSecond < secondSize) {
            for (int i = pointSecond; i < secondSize; ++i) {
                array[pointResult++] = newSecondPart[i];
            }
        }
    }



    public void sortInsert(int s, int f) {
        for (int j = s + 1; j <= f; ++j) {
            int key = array[j];
            int i = j - 1;
            while (i >= s && array[i] > key){
                array[i + 1] = array[i];
                --i;
            }
            array[i + 1] = key;
        }
    }

    public static void main(String[] args) {
//        int[] arr = {82, 54, 99, 35, 29, 42, 2, 96, 4, 77, 8, 26, 86, 54, 93, 72, 78, 2, 45, 3};
//        SortArrayTimsort sa = new SortArrayTimsort(arr);
//        sa.print();
//        sa.sort();
//        sa.print();

        for (int i = 0; i < 1000; ++i) {
            SortArrayTimsort sat = new SortArrayTimsort(i);
            SortArrayQuickRandomTail saq = new SortArrayQuickRandomTail(sat.getCloneArray());

            sat.sort();
            saq.sort();

            int[] a1 = sat.getCloneArray();
            int[] a2 = saq.getCloneArray();

            boolean f = false;

            for (int j = 0; i < a1.length; ++i) {
                if (a1[j] != a2[j]) {
                    f = true;
                }
            }


            if (f) {
                System.out.println(i + "Error");
            }

        }
    }
}
