package com.vivek.threading;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Tumbling window counter for events using a scheduled executor service to reset the counter at the start of a new hour
 *
 * Dynamic Delay Calculation:
 * The delay for the next reset is dynamically calculated to ensure it aligns with the next hour boundary.
 */
public class TumblingWindowCounter {

    private final AtomicLong eventCounter = new AtomicLong(0);
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    public TumblingWindowCounter() {
        scheduleHourlyReset();
    }

    // Increment the counter for each event
    public void onEvent() {
        eventCounter.incrementAndGet();
    }

    // Get the current count
    public long getCount() {
        return eventCounter.get();
    }

    // Schedule a reset at the start of every hour
    private void scheduleHourlyReset() {
        long delay = calculateDelayUntilNextHour();

        scheduler.scheduleAtFixedRate(() -> {
            System.out.println("Hour ended. Total count: " + getCount());
            eventCounter.set(0); // Reset the counter
        }, delay, TimeUnit.HOURS.toMillis(1), TimeUnit.MILLISECONDS);
    }

    // Calculate the delay until the next hour
    private long calculateDelayUntilNextHour() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime nextHour = now.truncatedTo(ChronoUnit.HOURS).plusHours(1);
        return ChronoUnit.MILLIS.between(now, nextHour);
    }

    // Shutdown the scheduler gracefully
    public void shutdown() {
        scheduler.shutdown();
        try {
            if (!scheduler.awaitTermination(1, TimeUnit.SECONDS)) {
                scheduler.shutdownNow();
            }
        } catch (InterruptedException e) {
            scheduler.shutdownNow();
        }
    }

    public static void main(String[] args) {
        TumblingWindowCounter counter = new TumblingWindowCounter();

        // Simulate events
        for (int i = 0; i < 100; i++) {
            counter.onEvent();
        }

        System.out.println("Current count: " + counter.getCount());

        // Simulate shutdown after 2 hours for demonstration purposes
        Executors.newSingleThreadScheduledExecutor().schedule(counter::shutdown, 2, TimeUnit.HOURS);
    }

}
