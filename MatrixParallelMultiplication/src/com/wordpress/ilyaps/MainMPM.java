package com.wordpress.ilyaps;

public class MainMPM {
    public static void compareTimeMultiplicationMatrix() {
        int countReplay = 2;
        long startTime;
        double time;

        MatrixParallelMultiplication mm = new MatrixParallelMultiplication();
        mm.setTimePause(1);


        System.out.println("Time [ms]");
        System.out.println("Standart \t Grapes \t ");

        int a = 3000;
        int b = 3000;
        int c = 3000;

        for (int i = 1; i <= 16; ++i) {
            mm.setCountTreads(i);

            mm.setRandomMatrixA(a, b);
            mm.setRandomMatrixB(b, c);

            startTime = System.nanoTime();
            for (int j = 0; j < countReplay; ++j) {
                mm.multiplyMatrixStandard();
            }
            time = (double)(System.nanoTime() - startTime) / 1000000;
            System.out.printf("%f\t", time / countReplay);

            startTime = System.nanoTime();
            for (int j = 0; j < countReplay; ++j) {
                mm.multiplyMatrixGrapes();
            }
            time = (double)(System.nanoTime() - startTime) / 1000000;
            System.out.printf("%f\n", time / countReplay);
        }
    }

    public static void checkWork() {
        MatrixParallelMultiplication mm = new MatrixParallelMultiplication();

        mm.setCountTreads(8);
        int a = 2;
        int b = 3;
        int c = 4;
        mm.setRandomMatrixA(a, b);
        mm.setRandomMatrixB(b, c);

        mm.printMatrixA();
        mm.printMatrixB();

        mm.multiplyMatrixStandard();
        mm.printMatrixC();
        mm.multiplyMatrixGrapes();
        mm.printMatrixC();
    }

    public static void main(String[] args) {
        compareTimeMultiplicationMatrix();
        //checkWork();
    }
}
