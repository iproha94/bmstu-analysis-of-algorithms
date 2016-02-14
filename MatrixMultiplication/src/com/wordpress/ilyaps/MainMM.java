package com.wordpress.ilyaps;


public class MainMM {
    public static void compareTimeDifferentMatrix() {
        int koefReplay = 20000000;
        long startTime;
        double time;

        MatrixMultiplication mm = new MatrixMultiplication();

        System.out.println("Time [ms]");
        System.out.println("dimension \t Standart \t Grapes \t ");

        for (int i = 1000; i <= 2000; i += 100) {
            System.out.printf("%dx%d\t", i,i);

            mm.setRandomMatrixA(i, i);
            mm.setRandomMatrixB(i, i);

            int countReplay = koefReplay / i / i;

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

    public static void compareTimeDifferentGrapes() {
        int koefReplay = 20000000;
        long startTime;
        double time;

        MatrixMultiplication mm = new MatrixMultiplication();

        System.out.println("Time [ms]");
        System.out.println("dimension \t Grapes \t Slow grapes \t ");

        for (int i = 100; i <= 2000; i += 100) {
            System.out.printf("%dx%d\t", i,i);

            mm.setRandomMatrixA(i, i);
            mm.setRandomMatrixB(i, i);

            int countReplay = koefReplay / i / i;

            startTime = System.nanoTime();
            for (int j = 0; j < countReplay; ++j) {
                mm.multiplyMatrixGrapes();
            }
            time = (double)(System.nanoTime() - startTime) / 1000000;
            System.out.printf("%f\t", time / countReplay);

            startTime = System.nanoTime();
            for (int j = 0; j < countReplay; ++j) {
                mm.multiplyMatrixGrapesSlow();
            }
            time = (double)(System.nanoTime() - startTime) / 1000000;
            System.out.printf("%f\n", time / countReplay);
        }
    }

    public static void checkWork() {
        MatrixMultiplication mm = new MatrixMultiplication();

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
        checkWork();
        //compareTimeDifferentMatrix();
        //compareTimeDifferentGrapes();
    }
}
