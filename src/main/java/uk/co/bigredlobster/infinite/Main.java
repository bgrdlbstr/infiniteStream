package uk.co.bigredlobster.infinite;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

public class Main {
    public static void main(String[] args) {
        final BlockingDeque<Integer> blockingDeque = new LinkedBlockingDeque<>();
        final DataConsumer c = new DataConsumer(blockingDeque, 100);
        final DataProducer p = new DataProducer(5, blockingDeque);
        final DataMonitor m = new DataMonitor(blockingDeque);

        c.consume();
        p.produce();
        m.monitor();

        try {
            Thread.sleep(10_000_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
