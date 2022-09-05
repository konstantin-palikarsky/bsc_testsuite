package testsuite;


import testsuite.repository.TokenQueue;
import testsuite.threads.UserTokenGenerator;
import testsuite.threads.UserTokenListener;

import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Testsuite implements Runnable {
    private final ScheduledExecutorService userPool = Executors.newScheduledThreadPool(CAPACITY);
    private final ScheduledExecutorService tokenGenerator = Executors.newSingleThreadScheduledExecutor();
    private UserTokenListener tokenListener;

    private static final int CAPACITY = 50;
    private static final int TOKEN_GENERATION_RATE_SECONDS = 1;
    private static final int WORKFLOW_EXECUTION_RATE_SECONDS = 10;

    @Override
    public void run() {
        TokenQueue tokens = new TokenQueue(CAPACITY);

        tokenGenerator.scheduleAtFixedRate(new UserTokenGenerator(tokens), 0,
                TOKEN_GENERATION_RATE_SECONDS, TimeUnit.SECONDS);
        tokenListener = new UserTokenListener(tokens, userPool, WORKFLOW_EXECUTION_RATE_SECONDS);
        tokenListener.start();

        //Termination logic
        while (!userPool.isShutdown()) {
            if (tokens.atCapacity()) {
                shutdown();
            }
        }

    }

    public void shutdown() {
        System.err.println("Shutting down at: " + LocalDateTime.now());
        userPool.shutdownNow();
        tokenGenerator.shutdownNow();
        tokenListener.interrupt();
    }

    public static void main(String[] args) {
        var testsuite = new Testsuite();
        testsuite.run();
    }

}

