package com.wordpress.ilyaps;

public class MainSLB {

    static void compareTimeWithAndWithoutBalance() {
        StaticLoadBalancing slb = new StaticLoadBalancing();
        slb.setMaxTimeOfTask(500);
        slb.setMinTimeOfTask(100);
        long startTime;
        double time;

        System.out.println("Time [ms]");
        System.out.println("Randomize \t Balancing \t theory Randomize \t theory Balancing");
        for (int i = 1; i < 17; ++i) {
            slb.setCountOfThreads(i);
            slb.setRandomTasks(i * 10);
            slb.createBalancingTreads();
            slb.createRandomizeTreads();

            startTime = System.nanoTime();
            slb.runPracticeRandomize();
            time = (double)(System.nanoTime() - startTime) / 1000000000;
            System.out.printf("%f\t", time);

            startTime = System.nanoTime();
            slb.runPracticeBalancing();
            time = (double)(System.nanoTime() - startTime) / 1000000000;
            System.out.printf("%f\t", time);

            System.out.printf("%f\t", slb.getTheoryMaxTimeExecutionRandomize());
            System.out.printf("%f\n", slb.getTheoryMaxTimeExecutionBalancing());

        }
    }

    public static void main(String[] args) {
        compareTimeWithAndWithoutBalance();

    }
}
