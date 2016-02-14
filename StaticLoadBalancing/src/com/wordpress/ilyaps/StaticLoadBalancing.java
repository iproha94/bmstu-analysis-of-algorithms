package com.wordpress.ilyaps;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by ilyap on 15.12.2015.
 */
public class StaticLoadBalancing {
    //время в милисекундах
    private int maxTimeOfTask;
    private int minTimeOfTask;

    private int countOfTasks;
    private int countOfThreads;

    private List<Integer> tasks;
    private List<List<Integer>> taskThreadsBalancing;
    private List<List<Integer>> taskThreadsRandomize;

    public void setMaxTimeOfTask(int maxTimeOfTask) {
        this.maxTimeOfTask = maxTimeOfTask;
    }

    public void setMinTimeOfTask(int minTimeOfTask) {
        this.minTimeOfTask = minTimeOfTask;
    }

    public void setCountOfThreads(int countOfThreads) {
        this.countOfThreads = countOfThreads;
    }

    static List<Integer> getRandomSetOfTasks(int countTasks, int maxTime, int minTime) {
        List<Integer> list = new ArrayList<>(countTasks);
        Random rand = new Random();

        for (int i = 0; i < countTasks; ++i) {
            int time = rand.nextInt(maxTime - minTime) + minTime;
            list.add(time);
        }
        return list;
    }

    public void setRandomTasks(int countOfTasks) {
        this.countOfTasks = countOfTasks;
        this.tasks = getRandomSetOfTasks(countOfTasks, maxTimeOfTask, minTimeOfTask);
    }

    void createBalancingTreads() {
        List<Integer> tempTask = new ArrayList<>(tasks.size());
        tempTask.addAll(tasks);
        tempTask.sort((a, b) -> b - a);

        taskThreadsBalancing = new ArrayList<>();

        for (int i = 0; i < countOfThreads; ++i) {
            taskThreadsBalancing.add(new ArrayList<>());
        }

        for (int i = 0; i < tempTask.size(); ++i) {
            int smallestThread = smallestThread(taskThreadsBalancing);
            taskThreadsBalancing.get(smallestThread).add(tempTask.get(i));
        }
    }

    void createRandomizeTreads() {
        taskThreadsRandomize = new ArrayList<>();

        for (int i = 0; i < countOfThreads; ++i) {
            taskThreadsRandomize.add(new ArrayList<>());
        }

        for (int i = 0; i < tasks.size(); ++i) {
            int smallestThread = smallestThread(taskThreadsRandomize);
            taskThreadsRandomize.get(smallestThread).add(tasks.get(i));
        }
    }

    private static int smallestThread(List<List<Integer>> taskThreads) {
        if (taskThreads.isEmpty()) {
            throw new RuntimeException();
        }

        int sum = getThreadSum(taskThreads.get(0));
        int num = 0;
        for (int i = 1; i < taskThreads.size(); ++i) {
            int tempsum = getThreadSum(taskThreads.get(i));
            if(tempsum < sum) {
                sum = tempsum;
                num = i;
            }
        }
        return num;
    }

    private static int getThreadSum(List<Integer> list) {
        return list.stream().reduce((s1, s2) -> s1 + s2).orElse(0);
    }

    static void runPractice(List<List<Integer>> threads) {
        List<Future<Void>> listFutures = new ArrayList<>();
        List<ExecutorService> listExecutorServices = new ArrayList<>();

        for (List<Integer> thread : threads) {
            ExecutorService executorService = Executors.newSingleThreadExecutor();
            listExecutorServices.add(executorService);

            for (int i = 0; i < thread.size(); i++) {
                Future<Void> future = executorService.submit(new TimePauseTask(thread.get(i)));
                listFutures.add(future);
            }
        }

        for (Future<Void> future : listFutures) {
            while(!future.isDone()) {}
        }

        listExecutorServices.forEach(ExecutorService::shutdown);
    }

    void runPracticeBalancing() {
        runPractice(taskThreadsBalancing);
    }

    static double  getTheoryTimeExecution(List<List<Integer>> threads) {
        if (threads.isEmpty()) {
            throw new RuntimeException();
        }

        int time = getThreadSum(threads.get(0));
        for (List<Integer> thread : threads) {
            int tempsum = getThreadSum(thread);
            if(tempsum > time) {
                time = tempsum;
            }
        }
        return (double)time / 1000;
    }

    double getTheoryMaxTimeExecutionBalancing() {
        return getTheoryTimeExecution(taskThreadsBalancing);
    }

    double getTheoryMaxTimeExecutionRandomize() {
        return getTheoryTimeExecution(taskThreadsRandomize);
    }

    void runPracticeRandomize() {
        runPractice(taskThreadsRandomize);
    }
}
