package testsuite.repository;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicLong;

public class TokenQueue {
    private final BlockingQueue<UserConnectionToken> tokenQueue = new ArrayBlockingQueue<>(120);
    private static final AtomicLong idCounter = new AtomicLong();

    public boolean atCapacity() {
        var head = tokenQueue.peek();
        if (head==null){
            return idCounter.get()>15;
        }

        return head.getId() > 15;
    }

    public void add() {
        if (idCounter.get() >= 100) {

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
