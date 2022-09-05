package testsuite.threads;

import testsuite.apis.MonolithApi;
import testsuite.apis.ThesisApi;
import testsuite.dtos.TokenDto;
import testsuite.repository.TokenQueue;
import testsuite.workflows.AuthWorkflow;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class UserTokenListener extends Thread {
    private final TokenQueue tokens;
    private final ScheduledExecutorService userPool;

    public UserTokenListener(TokenQueue tokens, ScheduledExecutorService userPool) {
        this.tokens = tokens;
        this.userPool = userPool;
    }

    public void run() {

        while (!userPool.isShutdown()) {
            var userToken = tokens.poll();
            ThesisApi api = new MonolithApi();

            AuthWorkflow authenticate = new AuthWorkflow(api);
            TokenDto jwt = null;
            try {
                jwt = authenticate.execute();
            } catch (Exception ignored) {
                //TODO exception handling
            }

            userPool.scheduleAtFixedRate(new WorkflowExecutor(userToken, jwt, api), 0, 10, TimeUnit.SECONDS);
        }

    }
}
