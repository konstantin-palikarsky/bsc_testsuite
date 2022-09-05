package testsuite;


import testsuite.repositories.RequestStatisticsRepository;
import testsuite.repositories.TokenQueue;
import testsuite.threads.UserTokenGenerator;
import testsuite.threads.UserTokenListener;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Testsuite implements Runnable {
    private final ScheduledExecutorService userPool = Executors.newScheduledThreadPool(CAPACITY);
    private final ScheduledExecutorService tokenGenerator = Executors.newSingleThreadScheduledExecutor();
    private UserTokenListener tokenListener;
    private final RequestStatisticsRepository stats = new RequestStatisticsRepository();

    private static final int CAPACITY = 20;
    private static final int TOKEN_GENERATION_RATE_SECONDS = 1;
    private static final int WORKFLOW_EXECUTION_RATE_SECONDS = 10;
    private static final int MAX_USER_EXECUTION_LENGTH_SECONDS = 60;

    @Override
    public void run() {
        TokenQueue tokens = new TokenQueue(CAPACITY);

        tokenGenerator.scheduleAtFixedRate(new UserTokenGenerator(tokens), 0,
                TOKEN_GENERATION_RATE_SECONDS, TimeUnit.SECONDS);
        tokenListener = new UserTokenListener(tokens, userPool, WORKFLOW_EXECUTION_RATE_SECONDS, stats);
        tokenListener.start();

        //Termination logic
        while (!userPool.isShutdown()) {
            if (tokens.atCapacity()) {
                try {
                    TimeUnit.SECONDS.sleep(MAX_USER_EXECUTION_LENGTH_SECONDS);
                } catch (InterruptedException e) {
                    shutdown();
                }
                shutdown();
            }
        }

    }

    public void shutdown() {
        System.err.println("Shutting down at: " + LocalDateTime.now());
        try {
            stats.getRequestStatisticsList();
        } catch (IOException e) {
            System.err.println("couldnt get request stats");
        }
        userPool.shutdown();
        tokenGenerator.shutdown();
        tokenListener.interrupt();
    }

    public static void main(String[] args) {
        var testsuite = new Testsuite();
        testsuite.run();
    }

}

