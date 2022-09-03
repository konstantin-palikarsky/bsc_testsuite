package testsuite;

import testsuite.apis.MonolithApi;
import testsuite.apis.ThesisApi;
import testsuite.workflows.AuthWorkflow;
import testsuite.workflows.Workflow;

public class Main {
    public static void main(String[] args) {
        ThesisApi api = new MonolithApi();

        Workflow workflow = new AuthWorkflow(api);

        try {
            workflow.execute();
        } catch (Exception e) {
            System.err.println("Error in reading workflow");
        }

    }


}

