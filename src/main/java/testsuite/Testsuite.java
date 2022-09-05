package testsuite;


import testsuite.apis.*;
import testsuite.repositories.RequestStatisticsRepository;
import testsuite.repositories.TokenQueue;
import testsuite.repositories.entities.RequestStatistics;
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
    private final RequestStatisticsRepository stats = new RequestStatisticsRepository();

    private UserTokenListener tokenListener;
    private TokenQueue tokens;

    //API to stress test
    private static final ApiType API = ApiType.MONOLITH;

    //Maximum connected users at a time
    private static final int CAPACITY = 20;

    //How often a new user connects (until CAPACITY is reached)
    private static final int TOKEN_GENERATION_RATE_SECONDS = 1;

    //How often each user executes their workflows
    private static final int WORKFLOW_EXECUTION_RATE_SECONDS = 10;

    //How long the stress test keeps running after all users are connected (in seconds)
    private static final int MAX_USER_EXECUTION_LENGTH_SECONDS = 20;

    @Override
    public void run() {
        var api = init();
        if (api == null) {
            shutdown();
            return;
        }

        tokenGenerator.scheduleAtFixedRate(new UserTokenGenerator(tokens), 0, TOKEN_GENERATION_RATE_SECONDS, TimeUnit.SECONDS);
        tokenListener = new UserTokenListener(tokens, userPool, WORKFLOW_EXECUTION_RATE_SECONDS, api);
        tokenListener.start();

        //Termination logic
        while (!userPool.isShutdown()) {
            if (tokens.atCapacity()) {
                try {
                    TimeUnit.SECONDS.sleep(MAX_USER_EXECUTION_LENGTH_SECONDS);
                } catch (InterruptedException ignored) {
                }
                shutdown();
            }
        }

    }

    public ThesisApi init() {
        tokens = new TokenQueue(CAPACITY);

        //Setting first timestamp
        stats.save(new RequestStatistics(LocalDateTime.now(), null, "TEST START TIMESTAMP"));

        switch (API) {
            case MONOLITH -> {
                return new MonolithApi(stats);
            }
            case OPENFAAS -> {
                return new FaasApi(stats);
            }
            case OPENWHISK -> {
                return new WhiskApi(stats);
            }
            default -> {
                return null;
            }
        }
    }

    public void shutdown() {
        System.err.println("Shutting down at: " + LocalDateTime.now());
        try {
            stats.getRequestStatisticsList();
        } catch (IOException e) {
            System.err.println("Couldn't get request stats");
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

