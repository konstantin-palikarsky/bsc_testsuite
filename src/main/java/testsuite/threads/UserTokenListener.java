package testsuite.threads;

import testsuite.apis.MonolithApi;
import testsuite.apis.ThesisApi;
import testsuite.dtos.TokenDto;
import testsuite.repositories.RequestStatisticsRepository;
import testsuite.repositories.TokenQueue;
import testsuite.workflows.AuthWorkflow;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class UserTokenListener extends Thread {
    private final TokenQueue tokens;
    private final ScheduledExecutorService userPool;
    private final int workflowRate;
    ThesisApi api;

    public UserTokenListener(TokenQueue tokens, ScheduledExecutorService userPool,
                             int workflowRate, ThesisApi api) {
        this.tokens = tokens;
        this.api = api;
        this.userPool = userPool;
        this.workflowRate = workflowRate;
    }

    public void run() {

        while (!userPool.isShutdown()) {
            var userToken = tokens.poll();
            if (userToken == null) {
                //TokenQueue has been interrupted
                return;
            }


            AuthWorkflow authenticate = new AuthWorkflow(api);
            TokenDto jwt = null;
            try {
                jwt = authenticate.execute();
            } catch (Exception ignored) {
                //TODO exception handling
            }

            userPool.scheduleAtFixedRate(new WorkflowExecutor(userToken, jwt, api), 0,
                    workflowRate, TimeUnit.SECONDS);
        }

    }
}
