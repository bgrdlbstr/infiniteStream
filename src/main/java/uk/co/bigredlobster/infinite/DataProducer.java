package uk.co.bigredlobster.infinite;

import java.util.Random;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class DataProducer {

    private final ScheduledExecutorService executorService;
    private final int tick;
    private final BlockingDeque<Integer> queue;

    public DataProducer(final int tick, final BlockingDeque<Integer> queue) {
        this.tick = tick;
        this.queue = queue;
        executorService = new ScheduledThreadPoolExecutor(1);
    }

    public void produce() {
        Random r = new Random();
        executorService.scheduleAtFixedRate(
                () -> {
                    final int i = r.nextInt(Integer.MAX_VALUE);
                    queue.add(i);
//                    System.out.println("Produced... i=" + i);
                },
                tick,
                tick,
                TimeUnit.MILLISECONDS
        );

        System.out.println("Producer is up");
    }
}
