package uk.co.bigredlobster.infinite;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class DataMonitor {
    private final BlockingDeque<Integer> blockingDeque;

    public DataMonitor(final BlockingDeque<Integer> blockingDeque) {
        this.blockingDeque = blockingDeque;
        System.out.println("Monitor is up");
    }

    public void monitor() {
        final ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(1);
        executorService.scheduleAtFixedRate(
                this::printQueueSize,
                10,
                10,
                TimeUnit.SECONDS
        );
    }

    private void printQueueSize() {
        System.out.println("blockingDeque.size() = " + blockingDeque.size());
    }
}
