package testsuite;

import testsuite.apis.MonolithApi;
import testsuite.apis.ThesisApi;
import testsuite.dtos.TokenDto;
import testsuite.workflows.AuthWorkflow;
import testsuite.workflows.Workflow;
import testsuite.workflows.WritingWorkflow;

public class Main {
    public static void main(String[] args) {
        ThesisApi api = new MonolithApi();

        TokenDto auth = new TokenDto();
        auth.setToken("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VybmFtZSI6Ilx0zac477-977-977-9IiwiZXhwIjoxNjYyMjE0ODQzfQ.5kMkGSI2rbajLDUqG1tHVH-H9MY8kUjaxb29b4boGro");
        Workflow workflow = new WritingWorkflow(api, auth);
        //Workflow workflow= new AuthWorkflow(api);

        try {
            workflow.execute();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }


}

