package com.wordpress.ilyaps;

import java.util.concurrent.Callable;

/**
 * Created by ilyap on 14.12.2015.
 */
final class TimePauseTask implements Callable<Void> {
    /**
     * @param timeLength время в миллисекундах, сколько будет выполняться задача
     */
    private final int timeLength;

    public TimePauseTask(int timeLength) {
        this.timeLength = timeLength;
    }

    @Override
    public Void call() {
        long startTime = System.nanoTime();
        while (((double)(System.nanoTime() - startTime) / 1000000) < timeLength) {
        }
        return null;
    }
}
