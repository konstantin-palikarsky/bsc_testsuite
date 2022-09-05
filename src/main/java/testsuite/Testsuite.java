package testsuite;


import testsuite.repository.TokenQueue;
import testsuite.threads.UserTokenGenerator;
import testsuite.threads.UserTokenListener;

import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Testsuite implements Runnable {
    private final ScheduledExecutorService userPool = Executors.newScheduledThreadPool(50);
    private final ScheduledExecutorService tokenGenerator = Executors.newSingleThreadScheduledExecutor();


    @Override
    public void run() {
        TokenQueue tokens = new TokenQueue();

        tokenGenerator.scheduleAtFixedRate(new UserTokenGenerator(tokens), 0, 1, TimeUnit.SECONDS);
        var tokenListener = new UserTokenListener(tokens, userPool);
        tokenListener.start();

        var startTime = LocalDateTime.now();
        while(!userPool.isShutdown()){
            if (tokens.atCapacity()){
                System.err.println("Shutting down at: "+LocalDateTime.now());
                userPool.shutdownNow();
                tokenGenerator.shutdownNow();
                tokenListener.interrupt();
            }
        }

    }

    public static void main(String[] args) {
        var testsuite = new Testsuite();
        testsuite.run();
    }

}

