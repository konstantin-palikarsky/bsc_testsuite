package testsuite;

import testsuite.apis.MonolithApi;
import testsuite.apis.ThesisApi;
import testsuite.dtos.TokenDto;
import testsuite.workflows.Workflow;
import testsuite.workflows.WritingWorkflow;

public class Main {
    public static void main(String[] args) {
        ThesisApi api = new MonolithApi();

        TokenDto auth = new TokenDto();
        auth.setToken("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VybmFtZSI6Iu-_vSzvv71GTXnvv70iLCJleHAiOjE2NjIyMTExMTd9.-9GZbisEvkBCc1K3gawe_O6dTWK3evcexP6gwiRlcTI");

        Workflow workflow = new WritingWorkflow(api, auth);

        try {
            workflow.execute();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }


}

