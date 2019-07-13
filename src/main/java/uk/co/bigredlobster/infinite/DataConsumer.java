package uk.co.bigredlobster.infinite;

import java.util.List;
import java.util.PriorityQueue;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class DataConsumer {
    private final BlockingDeque<Integer> blockingDeque;
    private final int maxSize;
    private final PriorityQueue<Integer> priorityQueue;

    public DataConsumer(final BlockingDeque<Integer> blockingDeque, final int maxSize) {
        this.blockingDeque = blockingDeque;
        priorityQueue = new PriorityQueue<>(maxSize);
        this.maxSize = maxSize;

        final ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(1);
        executorService.scheduleAtFixedRate(
                this::printHighest,
                5,
                5,
                TimeUnit.SECONDS
        );


        System.out.println("Consumer is up");
    }

    public void consume() {
        final ExecutorService threadPoolExecutor = Executors.newSingleThreadExecutor();
        threadPoolExecutor.submit(this::consumeData);
    }

    private void consumeData() {
        //noinspection InfiniteLoopStatement
        while (true) {
            try {
                final Integer data = blockingDeque.take();

                if (priorityQueue.size() >= maxSize) {
                    //noinspection ConstantConditions
                    if (priorityQueue.peek() < data) {
                        priorityQueue.add(data);
                        priorityQueue.poll();
                    }
                } else {
                    priorityQueue.add(data);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException("Aaaargh");
            }
        }
    }

    private void printHighest() {
        final List<Integer> collect = priorityQueue.stream().mapToInt(x -> x).sorted().boxed().collect(Collectors.toList());
        System.out.println("sorted = " + collect);
    }
}
