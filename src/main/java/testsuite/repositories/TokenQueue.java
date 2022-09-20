package testsuite.repositories;

import testsuite.repositories.entities.UserConnectionToken;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicLong;

public class TokenQueue {
    private final BlockingQueue<UserConnectionToken> tokenQueue;
    private static final AtomicLong idCounter = new AtomicLong();
    private final int capacity;

    public TokenQueue(int capacity) {
        this.capacity = capacity;
        this.tokenQueue = new ArrayBlockingQueue<>(capacity + 20);
    }

    public boolean atCapacity() {
        var head = tokenQueue.peek();

        if (head == null) {
            return idCounter.get() >= capacity;
        }

        return head.id() >= capacity;
    }

    public void fill(int capacity) {
        for (int i = 0; i < capacity; i++) {

            tokenQueue.add(new UserConnectionToken(idCounter.getAndIncrement()));
        }
        System.err.println("Filled tokens up to: " + (capacity+20-tokenQueue.remainingCapacity()));
    }

    public void add() {
        if (idCounter.get() >= capacity) {

            return;
        }
        boolean saveFlag = true;
        while (saveFlag) try {
            tokenQueue.put(new UserConnectionToken(idCounter.getAndIncrement()));
            saveFlag = false;
        } catch (InterruptedException ignored) {
        }

    }

    public synchronized UserConnectionToken poll() {
        try {
            return tokenQueue.take();
        } catch (InterruptedException e) {
            System.err.println("Interrupted on user connection token retrieval");
            return null;
        }

    }
}
