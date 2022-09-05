package testsuite.threads;

import testsuite.apis.ThesisApi;
import testsuite.dtos.TokenDto;
import testsuite.repositories.entities.UserConnectionToken;
import testsuite.workflows.ReadingWorkflow;
import testsuite.workflows.WritingWorkflow;

public class WorkflowExecutor extends Thread {
    private final TokenDto jwt;
    private final ThesisApi api;

    public WorkflowExecutor(UserConnectionToken userConnectionToken, TokenDto jwt, ThesisApi api) {
        this.jwt = jwt;
        this.api = api;
        System.err.println("got a token: " + (userConnectionToken.getId() + 1) + "/100");
    }

    public void run() {

        ReadingWorkflow read = new ReadingWorkflow(api);
        WritingWorkflow write = new WritingWorkflow(api, jwt);

        try {
            read.execute();
            write.execute();
        } catch (Exception e) {
            //TODO exception handling
            throw new RuntimeException(e);
        }
    }
}
